package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    Dao<MovieEntity, Long> dao;

    public MovieRepository() {
        this.dao = Database.getDatabase().getMovieDao();
    }

    public List<MovieEntity> getAllMovies() throws SQLException {
        return dao.queryForAll();
    }

    public int removeAll() {
        try {
            dao.delete(getAllMovies());
        } catch (SQLException e) {
            return -1;
        } return 0;
    }

    public MovieEntity getMovieById(long id) throws SQLException {
        return dao.queryForId(id);
    }

    public int addAllMovies(List<Movie> movies) {
        try {
            dao.create(MovieEntity.fromMovies(movies));
        } catch (SQLException e) {
            return -1;
        } return 0;
    }
}
