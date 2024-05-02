package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {

    public static final String DB_URL = "jdbc:h2:file: ./db/moviedb";
    public static final String DB_USERNAME = "user";
    public static final String DB_PASSWORD = "password";

    private static ConnectionSource connectionSource;
    private static Database instance;
    private Dao<MovieEntity, Long> movieDao;
    private Dao<WatchlistMovieEntity, Long> watchlistDao;

    private Database() {
        try {
            createConnectionSource();
            movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
            watchlistDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTable(WatchlistMovieEntity.class);
            createTable(MovieEntity.class);
        } catch (SQLException e) {
            System.out.println("Failed to create Database");
        }
    }

    private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    private static void createTable(Class<?> entityClass) throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, entityClass);
    }

    public static Database getDatabase() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return watchlistDao;
    }

    public Dao<MovieEntity, Long> getMovieDao() {
        return movieDao;
    }
}
