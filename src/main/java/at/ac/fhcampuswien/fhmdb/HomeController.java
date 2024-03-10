package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    public JFXButton resetBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(EnumSet.allOf(Movie.MOVIEGENRES.class));

        resetBtn.setOnAction(event -> {
            resetFilters();
        });

        searchBtn.setOnAction(event -> {
            filterObservableMovies((Movie.MOVIEGENRES) genreComboBox.getValue(), searchField.getText().toLowerCase());
            movieListView.setItems(observableMovies);
        });
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                sortMoviesAscending(observableMovies);
                sortBtn.setText("Sort (desc)");
            } else {
                sortMoviesDescending(observableMovies);
                sortBtn.setText("Sort (asc)");
            }
        });
    }
    private void resetFilters() {
        observableMovies.clear();
        observableMovies.addAll(allMovies);
        movieListView.setItems(observableMovies);
        genreComboBox.setValue(null);
    }

    public void filterObservableMovies(Movie.MOVIEGENRES genre, String search) {
        // Check if both genre and search are not empty
        search = search.toLowerCase();
        if (genre != null && !search.isEmpty()) {
            observableMovies.clear();
            // Filter by both genre and search
            for (Movie movie : allMovies) {
                if (movie.getGenres().contains(genre) && (movie.getTitle().toLowerCase().contains(search) || movie.getDescription().toLowerCase().contains(search))) {
                    observableMovies.add(movie);
                }
            }
            return;
        }

        // Check if genre is not empty
        if (genre != null) {
            observableMovies.clear();
            // Filter only by genre
            for (Movie movie : allMovies) {
                if (movie.getGenres().contains(genre)) {
                    observableMovies.add(movie);
                }
            }
            return;
        }

        // Check if search is not empty
        if (!search.isEmpty()) {
            observableMovies.clear();
            // Filter only by search
            for (Movie movie : allMovies) {
                if (movie.getTitle().toLowerCase().contains(search) || movie.getDescription().toLowerCase().contains(search)) {
                    observableMovies.add(movie);
                }
            }
        }

        //No filtering required
    }

    public void sortMoviesAscending(List<Movie> movies){
        Collections.sort(movies);
    }
    public void sortMoviesDescending(List<Movie> movies){
        sortMoviesAscending(movies);
        Collections.reverse(movies);
    }

    public void setObservableMovies(List<Movie> movies) {
        observableMovies.addAll(movies);
    }

}