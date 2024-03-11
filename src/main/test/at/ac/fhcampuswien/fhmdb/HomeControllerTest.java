package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
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
                    assertEquals(homeController.getObservableMovies().get(0).getTitle(),"The Hateful Eight", "Ignore Capitalization failed");
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
}