package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieAPIException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.*;

public class HomeViewController {

    // Filters
    @FXML
    public ComboBox<String> genreComboBox;

    @FXML
    public ComboBox<String> ratingComboBox;

    @FXML
    public ComboBox<String> releaseYearComboBox;

    @FXML
    public Button searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public Button sortBtn;

    // Movie list and API
    @FXML
    public JFXListView<Movie> movieListView;

    private List<Movie> allMovies;
    private SortedState sortedState;
    private ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private MovieAPI movieAPI;
    private MovieRepository mr;
    private ClickEventHandler<Movie> onAddToWatchlistClicked = null;
    private WatchlistRepository wlp;

    public void initialize() {
        try
        {
            wlp = new WatchlistRepository();
        }
        catch (DatabaseException e){
            alertException(e.getMessage());
        }

        onAddToWatchlistClicked = (clickedItem) -> wlp.addToWatchlist(new WatchlistMovieEntity(clickedItem.getId()));
        movieAPI = new MovieAPI();
        try
        {
            this.mr = new MovieRepository();

        }
        catch (DatabaseException e)
        {
            alertException(e.getMessage());

        }
        try
        {
            allMovies = Movie.initializeMovies(movieAPI);
            try
            {
                mr.removeAll();
                mr.addAllMovies(allMovies);
            }catch (DatabaseException | NullPointerException e)
            {
                alertException(e.getMessage());
            }
        }
        catch (MovieAPIException e){
            alertException(e.getMessage());
            try{
                allMovies = MovieEntity.toMovies(mr.getAllMovies());
            }
            catch (DatabaseException | NullPointerException en){
                alertException(en.getMessage());
            }
        }

        try{
            movieList.addAll(allMovies);
        }catch (NullPointerException e){
            alertException(e.getMessage());
        }
        sortedState = SortedState.NONE;
        movieListView.setItems(movieList);
        movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked, "Watchlist"));

        initializeFilters();
    }

    public void initializeFilters() {
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().add("All genres");
        genreComboBox.getItems().addAll(MovieAPI.movieGenres);

        releaseYearComboBox.setPromptText("Filter by Release Year");
        Set<String> setOfReleaseYears = new HashSet<>();
        try{
            for (Movie movie : allMovies) {
                setOfReleaseYears.add(String.valueOf(movie.getReleaseYear()));
            }
            List<String> releaseYears = new ArrayList<>(setOfReleaseYears);
            Collections.sort(releaseYears);
            releaseYearComboBox.getItems().addAll(releaseYears);
        }catch (NullPointerException e){
            alertException(e.getMessage());
        }finally{
            releaseYearComboBox.getItems().add("All years");
        }

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
            movieList.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else {
            movieList.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
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

    @FXML
    public void searchBtnClicked(ActionEvent event) throws MovieAPIException {
        List<String> filters = filterCheck();
        movieList.clear();
        try {
            movieList.addAll(movieAPI.getMoviesWithFiltersApplied(filters.get(0), filters.get(1), filters.get(2), filters.get(3)));
        }catch (MovieAPIException e){
            alertException(e.getMessage());
            try{
                movieList.addAll(MovieEntity.toMovies(mr.getAllMovies()));
            }
            catch(DatabaseException | NullPointerException en){
                alertException(en.getMessage());
            }
        }
        sortedState = SortedState.NONE;
    }

    @FXML
    public void sortBtnClicked(ActionEvent event) {
        sortMovies();
    }

    /*
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
    */
    public void alertException(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
