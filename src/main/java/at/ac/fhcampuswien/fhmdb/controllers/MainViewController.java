package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.models.Screen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainViewController {

    @FXML
    public Button homeBtn;

    @FXML
    public Button watchlistBtn;

    @FXML
    public BorderPane mainPane;

    public void initialize() {
        loadHomeView();
    }

    public void setContentView(String pathToView) {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource(pathToView));

        try {
            mainPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Error while loading -->" + pathToView);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadHomeView() {
        setContentView(Screen.HOME.path);
    }

    public void loadWatchlistView() {
        setContentView(Screen.WATCHLIST.path);
    }
}
