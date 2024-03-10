package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
                    assertEquals(movies.get(0).getTitle(), "The Lobster", "SortDescending Failed");
                });
    }
}