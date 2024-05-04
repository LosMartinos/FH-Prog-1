package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genres = new Label();

    private final Button detailsButton = new Button("Show Details");
    protected final Button watchlistButton = new Button();

    private final VBox movieDataLayout = new VBox(title, detail, genres);
    private final HBox buttonsLayout = new HBox(detailsButton, watchlistButton);

    public MovieCell(ClickEventHandler<Movie> addToWatchlistClicked, String watchlistBtnText) {
        super();

        watchlistButton.setOnMouseClicked(mouseEvent -> {
            addToWatchlistClicked.onClick(getItem());
        });

        watchlistButton.setText(watchlistBtnText);

        movieDataLayout.setSpacing(10);
        movieDataLayout.setAlignment(Pos.CENTER_LEFT);

        buttonsLayout.setSpacing(10);
        buttonsLayout.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(buttonsLayout, Priority.ALWAYS);

        detailsButton.getStyleClass().add("background-yellow");
        watchlistButton.getStyleClass().add("background-yellow");

        title.getStyleClass().add("text-yellow");
        title.setFont(new Font(20));

        detail.getStyleClass().add("text-white");
        detail.setMaxWidth(500);
        detail.setWrapText(true);


        genres.getStyleClass().add("text-white");
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );
            genres.setText(String.join(", ", movie.getGenres()));

            HBox cellLayout = new HBox(movieDataLayout, buttonsLayout);
            cellLayout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));
            cellLayout.setPadding(new Insets(10));
            setGraphic(cellLayout);
        }
    }
}

