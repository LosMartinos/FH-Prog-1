package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.List;

public class WatchlistViewController {

    @FXML
    public JFXListView<Movie> watchlistView;
    private WatchlistRepository wp;

    private final ClickEventHandler<Movie> onRemoveFromWatchlistClicked = (clickedItem) -> wp.removeFromWatchlist(clickedItem.getId());

    private final ObservableList<Movie> watchlist = FXCollections.observableArrayList();
    private List<WatchlistMovieEntity> movieEntityList;

    public void initialize() {
        try{
            this.wp = new WatchlistRepository();
            this.movieEntityList = wp.getWatchlist();
            for(WatchlistMovieEntity mv : movieEntityList) {
                watchlist.add(new MovieRepository().getMovieByApiId(mv.getApiID()));
            }
        }catch(DatabaseException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

        watchlistView.setItems(watchlist);
        watchlistView.setCellFactory(watchlistView -> new MovieCell(onRemoveFromWatchlistClicked, "Remove"));
    }
}
