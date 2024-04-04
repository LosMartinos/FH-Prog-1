package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.*;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    public JFXComboBox releaseYearComboBox;

    @FXML
    public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies;

    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    protected SortedState sortedState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allMovies = Movie.initializeMovies();
        observableMovies.addAll(allMovies);
        sortedState = SortedState.NONE;

        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().add("All genres");
        genreComboBox.getItems().addAll(MovieAPI.movieGenres);

        releaseYearComboBox.setPromptText("Filter by Release Year");
        List<String> releaseYears = new ArrayList<>();
        for(Movie movie : allMovies) {
            releaseYears.add(String.valueOf(movie.getReleaseYear()));
        }
        Collections.sort(releaseYears);
        releaseYearComboBox.getItems().add("All years");
        releaseYearComboBox.getItems().addAll(releaseYears);

        ratingComboBox.setPromptText("Filter by Rating");
        ratingComboBox.getItems().add("All ratings");
        ratingComboBox.getItems().addAll(">1", ">2", ">3", ">4", ">5", ">6", ">7", ">8", ">9");
    }

    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            sortMovies(SortedState.ASCENDING);
        } else if (sortedState == SortedState.ASCENDING) {
            sortMovies(SortedState.DESCENDING);
        }
    }

    public void sortMovies(SortedState sortDirection) {
        if (sortDirection == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }

    public void searchBtnClicked(ActionEvent actionEvent) {
        List<String> filters = filterCheck();
        String url = MovieAPI.createUrlForMovieAPI(filters.get(0), filters.get(1), filters.get(2), filters.get(3));
        String movieData = MovieAPI.okhttpGetRequestForMovieAPI(url);
        List<Movie> movies = MovieAPI.jsonStringToMovieObjects(movieData);
        observableMovies.clear();
        observableMovies.addAll(movies);
        sortedState = SortedState.NONE;
    }

    private List<String> filterCheck() {
        String query = searchField.getText().toLowerCase();
        String genre = (String) genreComboBox.getValue();
        String releaseYear = (String) releaseYearComboBox.getValue();
        String rating = (String) ratingComboBox.getValue();
        if (rating != null) rating = rating.replace(">", "");

        List<String> filters = new ArrayList<>(Arrays.asList(genre, releaseYear, rating));
        for (int i = 0; i < filters.size(); i++) {
            String filter = filters.get(i);
            filters.set(i, filter != null && filter.contains("All") ? null : filter);
        }
        filters.add(0, query);

        return filters;
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }
}