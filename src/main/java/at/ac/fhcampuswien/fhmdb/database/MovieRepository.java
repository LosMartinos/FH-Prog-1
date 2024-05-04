package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    Dao<MovieEntity, Long> dao;

    public MovieRepository() throws DatabaseException {
        this.dao = Database.getDatabase().getMovieDao();
    }

    public List<MovieEntity> getAllMovies() throws DatabaseException {
        try{
            return dao.queryForAll();
        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public void removeAll() throws DatabaseException {
        try {
            dao.delete(getAllMovies());
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

    }

    public MovieEntity getMovieById(long id) throws SQLException {
        return dao.queryForId(id);
    }

    public Movie getMovieByApiId(String id) {
        try {
            return MovieEntity.toMovies(dao.queryForEq("apiID", id)).get(0);
        } catch (SQLException e) {
            System.out.println("couldn't get movie by api id");
        } return null;
    }

    public int addAllMovies(List<Movie> movies) {
        try {
            dao.create(MovieEntity.fromMovies(movies));
        } catch (SQLException e) {
            return -1;
        } return 0;
    }
}
