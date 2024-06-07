package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.Observable;
import at.ac.fhcampuswien.fhmdb.Observer;
import com.j256.ormlite.dao.Dao;

import java.util.*;

public class WatchlistRepository implements Observable {
    Dao<WatchlistMovieEntity, Long> dao;
    private static WatchlistRepository instance;
    private HashMap<Observer, String> observers;

    private WatchlistRepository() throws DataBaseException {
        try {
            this.dao = DatabaseManager.getInstance().getWatchlistDao();
        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
        this.observers = new HashMap<>();
    }

    public static WatchlistRepository getInstance() throws DataBaseException {
        if (instance == null) {
            instance = new WatchlistRepository();
        }
        return instance;
    }

    public List<WatchlistMovieEntity> getWatchlist() throws DataBaseException {
        try {
            return dao.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataBaseException("Error while reading watchlist");
        }
    }
    public int addToWatchlist(WatchlistMovieEntity movie) throws DataBaseException {
        try {
            // only add movie if it does not exist yet
            long count = dao.queryBuilder().where().eq("apiId", movie.getApiId()).countOf();
            if (count == 0) {
                notifyObservers("Added to watchlist", "addToWatchlist");
                return dao.create(movie);
            } else {
                notifyObservers("Movie is already in the watchlist", "addToWatchlist");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataBaseException("Error while adding to watchlist");
        }
    }

    public int removeFromWatchlist(String apiId) throws DataBaseException {
        try {
            notifyObservers("Removed from Watchlist", "removeFromWatchlist");
            return dao.delete(dao.queryBuilder().where().eq("apiId", apiId).query());
        } catch (Exception e) {
            throw new DataBaseException("Error while removing from watchlist");
        }
    }

    public void addObserver(Observer observer, String event) {
        observers.put(observer, event);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message, String event) {
        for (Observer observer : observers.keySet()) {
            if (Objects.equals(observers.get(observer), event)) {
                observer.update(message);
            }
        }
    }
}
