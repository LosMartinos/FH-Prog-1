package at.ac.fhcampuswien.fhmdb.models;

import javafx.animation.Animation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Comparable{
    private String id;
    private String title;
    private List<String> genres;
    private int releaseYear;
    private String description;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> directors;
    private List<String> writers;
    private List<String> mainCast;
    private double rating;

    public Movie(String id, String title, List<String> genres, int releaseYear, String description,
                 String imgUrl, int lengthInMinutes, List<String> directors, List<String> writers,
                 List<String> mainCast, double rating) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public int compareTo(Object o) {
        return this.getTitle().compareTo(((Movie) o).getTitle());
    }

    public static List<Movie> initializeMovies() {
        List<Movie> movies = new ArrayList<>();
        List<String> atr = new ArrayList<>();


        try {
            MovieAPI movieAPI = new MovieAPI();

            movies.addAll(movieAPI.getAllMovies());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
