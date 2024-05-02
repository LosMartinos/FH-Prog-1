package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
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

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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

    private MovieAPI movieAPI;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movieAPI = new MovieAPI();
        allMovies = Movie.initializeMovies(movieAPI);
        observableMovies.addAll(allMovies);
        sortedState = SortedState.NONE;
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        initializeFilters();
    }

    public void initializeFilters() {
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().add("All genres");
        genreComboBox.getItems().addAll(MovieAPI.movieGenres);

        releaseYearComboBox.setPromptText("Filter by Release Year");
        Set<String> setOfReleaseYears = new HashSet<>();
        for (Movie movie : allMovies) {
            setOfReleaseYears.add(String.valueOf(movie.getReleaseYear()));
        }
        List<String> releaseYears = new ArrayList<>(setOfReleaseYears);
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
        observableMovies.clear();
        observableMovies.addAll(movieAPI.getMoviesWithFiltersApplied(filters.get(0), filters.get(1), filters.get(2), filters.get(3)));
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

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream() //umwandlung zu eeinem stream
                .mapToInt(movie -> movie.getTitle().length()) // mapping zu int
                .max() // nimm das max im stream, first found gewinnt im standoff
                .orElse(0); // wenn fehler is gib 0
    }

    public int countMoviesFrom(List<Movie> movies, String director) { // normalerweise ein long weil im stream große zahlen sein können
        return (int) movies.stream()
                .filter(movie -> movie.getDirectors().contains(director)) // get directors
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList()); // Collectors.tolist macht eine liste aus den movies
    }

    public String getMostPopularActor(List<Movie> movies) {
        Map<String, Long> actorCounts = movies.stream()  // chatgpt gönnt
                .flatMap(movie -> movie.getMainCast().stream()) // flat map is map für listen/arrays/gruppen
                .collect(Collectors.groupingBy(s -> s, Collectors.counting())); // lambda um den actorstring unverändert zu übergeben

        return actorCounts.entrySet().stream() // ???xd
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}

