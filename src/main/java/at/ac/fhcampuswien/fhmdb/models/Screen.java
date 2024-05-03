package at.ac.fhcampuswien.fhmdb.models;

public enum Screen {

    HOME("home-view.fxml"),
    WATCHLIST("watchlist-view.fxml");

    public final String path;

    Screen(String path) {
        this.path = path;
    }
}
