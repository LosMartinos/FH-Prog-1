package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DatabaseTable(tableName = "movies")
public class MovieEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField()
    private String apiID;

    @DatabaseField()
    private String title;

    @DatabaseField()
    private String description;

    @DatabaseField()
    private String genres;

    @DatabaseField()
    private int releaseYear;

    @DatabaseField()
    private String imgUrl;

    @DatabaseField()
    private int lengthInMinutes;

    @DatabaseField()
    private double rating;

    public MovieEntity() {}

    public MovieEntity(String apiID, String title, String description, String genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        this.apiID = apiID;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    protected MovieEntity(String apiID) {
        this.apiID = apiID;
    }

    private static String genresToString(List<String> genres) {
        return String.join(", ", genres);
    }

    private static List<String> genresFromString(String genres) {
        return Arrays.asList(genres.split(", "));
    }

    public static List<MovieEntity> fromMovies(List<Movie> movies) {
        List<MovieEntity> movieEntities = new ArrayList<>();
        for (Movie m : movies) {
            movieEntities.add(new MovieEntity(m.getId(), m.getTitle(), m.getDescription(), genresToString(m.getGenres()), m.getReleaseYear(), m.getImgUrl(), m.getLengthInMinutes(), m.getRating()));
        }
        return movieEntities;
    }

    public static List<Movie> toMovies(List<MovieEntity> movieEntities) {
        List<Movie> movies = new ArrayList<>();
        for (MovieEntity m : movieEntities) {
            movies.add(new Movie(m.apiID, m.title, m.description, genresFromString(m.genres), m.releaseYear, m.imgUrl, m.lengthInMinutes, m.rating));
        }
        return movies;
    }
}
