package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository() {
        this.dao = Database.getDatabase().getWatchlistDao();
    }

    List<WatchlistMovieEntity> getWatchlist() throws SQLException {
        return dao.queryForAll();
    }

    int addToWatchlist(WatchlistMovieEntity movie) {
        try {
            dao.create(movie);
        } catch (SQLException e) {
            return -1;
        } return 0;
    }

    int removeFromWatchlist(String apiID) {
        try {
            dao.delete(dao.queryForEq("apiID", apiID));
        } catch (SQLException e) {
            return -1;
        } return 0;
    }
}
