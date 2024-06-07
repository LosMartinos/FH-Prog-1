package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class MovielistState {
    private SortedState sortedState;

    public MovielistState() {
        sortedState = SortedState.NONE;
    }

    public ObservableList<Movie> sortMoviesIfNotNone(ObservableList<Movie> observableMovies) {
        if (sortedState == SortedState.NONE) {
            return observableMovies;
        } else return sortMovies(sortedState, observableMovies);
    }

    public ObservableList<Movie> sortMovies(ObservableList<Movie> observableMovies){
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            return sortMovies(SortedState.ASCENDING, observableMovies);
        } else return sortMovies(SortedState.DESCENDING, observableMovies);
    }

    private ObservableList<Movie> sortMovies(SortedState sortDirection, ObservableList<Movie> observableMovies) {
        if (sortDirection == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
        return observableMovies;
    }
}
