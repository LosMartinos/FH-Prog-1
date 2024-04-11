package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
	//Preparing Test Data
	static List<Movie> testMovies = new ArrayList<>();

	@BeforeAll //Fills testMovies list with Test Data once to prevent duplicate Test Data
	static void setUpTestData() {
		fillTestMoviesWithData();
	}

	static void fillTestMoviesWithData() {
		try (FileReader reader = new FileReader("src/main/testResources/Testdata.json")) {
			Type type = new TypeToken<ArrayList<Movie>>() {
			}.getType();
			testMovies = new Gson().fromJson(reader, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Tests

	@Test
	void getMostOftenOccurringActor() {
		String expectedActor = "Viking Superstar";
		HomeController homeController = new HomeController();
		assertAll("most popular actor assertions",
				() -> {
					String methodResult = homeController.getMostPopularActor(testMovies);
					assertEquals(methodResult, expectedActor, "Getting the most popular Actor Failed");
				}
		);
	}

	@Test
	void getLongestMovieTitleAsInteger(){
		int expectedResult = "Humuhumunukunukuapua'a Moments".length();
		HomeController homeController = new HomeController();
		assertAll("longest movie title assertions",
                () -> {
					int methodResult = homeController.getLongestMovieTitle(testMovies);
					assertEquals(methodResult, expectedResult, "Getting the longest Movie title Failed");
				}
		);
	}

	@Test
	void countMoviesFromADirector(){
		int expectedResult1 = 1;
		int expectedResult2 = 2;
		String director1 = "Jens Pollak";
		String director2 = "Smörgåsbord";
		HomeController homeController = new HomeController();
		assertAll("counting movie assertions",
                () -> {
					int methodResult = homeController.countMoviesFrom(testMovies, director1);
					assertEquals(methodResult, expectedResult1, "Counting Movies of" + director1 + " Failed");
				},
				() -> {
					int methodResult = homeController.countMoviesFrom(testMovies, director2);
					assertEquals(methodResult, expectedResult2, "Counting Movies of" + director2 + " Failed");
				}
		);
	}

	@Test
	void getAllMoviesBetweenYears(){
		String expectedResult1 = "Viking: Beginning of an Era";
		String expectedResult2 = "Viking: The Vikinging";
		HomeController homeController = new HomeController();
		assertAll("movie between years assertions",
                () -> {
					List<Movie> methodResult = homeController.getMoviesBetweenYears(testMovies,700,900);
					assertEquals(methodResult.get(0).getTitle(), expectedResult1, "Getting movie 1 between years Failed");
				},
				() -> {
					List<Movie> methodResult = homeController.getMoviesBetweenYears(testMovies,700,900);
					assertEquals(methodResult.get(1).getTitle(), expectedResult2, "Getting movie 2 between years Failed");
				}
		);
	}

	/*

    @Test
    void sortingMovieListAlphabetically() {
        List<Movie> movies = Movie.initializeMovies();
        HomeController homeController = new HomeController();
        assertAll("sorting assertions",
                () -> {
                    homeController.sortMoviesAscending(movies);
                    assertEquals(movies.get(0).getTitle(), "Arcane", "SortAscending Failed");
                },

                () -> {
                    homeController.sortMoviesDescending(movies);
                    assertEquals(movies.get(0).getTitle(), "Tokyo Zombie", "SortDescending Failed");
                });
    }

    @Test
    void searchBarQuery(){
        String movieTitle = "The Hateful Eight"; //Capital Letters
        HomeController homeController = new HomeController();
        assertAll("simple query assertion",
                () -> {
                    homeController.filterObservableMovies(null,movieTitle);
                    assertEquals(homeController.getObservableMovies().get(0).getTitle(),"The Hateful Eight", "Search check failed");
                }
        );
    }

    @Test
    void searchBarIgnoreCapitalization() {
        String movieTitle1 = "THE HATEFUL EIGHT"; //Capital Letters
        String movieTitle2 = "tokyo zombie"; //Lowercase Letters
        HomeController homeController = new HomeController();
        assertAll("capitalization assertions",
                () -> {
                    homeController.filterObservableMovies(null,movieTitle1);
                    assertEquals(homeController.getObservableMovies().get(0).getTitle(),"The Hateful Eight", "Ignore Capitalization failed");
                },

                () -> {
                    homeController.filterObservableMovies(null,movieTitle2);
                    assertEquals(homeController.getObservableMovies().get(0).getTitle(),"Tokyo Zombie", "Ignore Capitalization failed");
                });
    }

    @Test
    void searchBarGenreFilter() {
        Movie.MOVIEGENRES movieGenre1 = Movie.MOVIEGENRES.SPORT;
        Movie.MOVIEGENRES movieGenre2 = Movie.MOVIEGENRES.SCIENCE_FICTION;
        Movie.MOVIEGENRES movieGenre3 = Movie.MOVIEGENRES.ACTION;

        HomeController homeController = new HomeController();
        assertAll("genre filter assertions",
                () -> {
                    homeController.filterObservableMovies(movieGenre1,"");
                    assertEquals(homeController.getObservableMovies().get(0).getTitle(),"Rocky", "Genre Filter failed");
                },

                () -> {
                    homeController.filterObservableMovies(movieGenre2,"");
                    assertEquals(homeController.getObservableMovies().get(0).getTitle(),"Cowboy Bebop", "Genre Filter failed");
                },

                () -> {
                    homeController.filterObservableMovies(movieGenre3,"");
                    assertEquals(homeController.getObservableMovies().get(0).getTitle(),"Ted", "Genre Filter failed");
                    assertEquals(homeController.getObservableMovies().get(1).getTitle(),"Cowboy Bebop", "Genre Filter failed");
                    assertEquals(homeController.getObservableMovies().get(2).getTitle(),"Tokyo Zombie", "Genre Filter failed");
                });
    }

    @Test
    void searchBarGenreFilterAndMovieNameCombined() {
        Movie.MOVIEGENRES movieGenre1 = Movie.MOVIEGENRES.SPORT;
        String movieName1 = "Rocky";
        Movie.MOVIEGENRES movieGenre2 = Movie.MOVIEGENRES.ACTION;
        String movieName2 = "SHMONGUSS' REVENGE";
        Movie.MOVIEGENRES movieGenre3 = Movie.MOVIEGENRES.BIOGRAPHY;
        String movieName3 = "Tokyo Zombie";
        HomeController homeController = new HomeController();
        assertAll("genre filter assertions",
                () -> {
                    homeController.filterObservableMovies(movieGenre1,movieName1);
                    assertEquals(homeController.getObservableMovies().get(0).getTitle(),"Rocky", "Genre Filter failed");
                },

                () -> {
                    homeController.filterObservableMovies(movieGenre2,movieName2);
                    assertTrue(homeController.getObservableMovies().isEmpty());
                },

                () -> {
                    homeController.filterObservableMovies(movieGenre3, movieName3);
                    assertTrue(homeController.getObservableMovies().isEmpty());
                });
    }
	 */
}