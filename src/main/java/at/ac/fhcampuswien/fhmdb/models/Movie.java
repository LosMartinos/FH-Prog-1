package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;

    // TODO add more properties here
    private List<MOVIEGENRES> genres;
    public enum MOVIEGENRES {
        ACTION, ADVENTURE, ANIMATION, BIOGRAPHY, COMEDY,
        CRIME, DRAMA, DOCUMENTARY, FAMILY, FANTASY, HISTORY, HORROR,
        MUSICAL, MYSTERY, ROMANCE, SCIENCE_FICTION, SPORT, THRILLER, WAR, WESTERN
    }

    public Movie(String title, String description, List<MOVIEGENRES> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<MOVIEGENRES> getGenres() { return genres; }
    public String getGenresAsString() { return listToString(this.genres); }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here
        movies.add(new Movie("Ted", "This is a cool movie!", List.of(MOVIEGENRES.ACTION, MOVIEGENRES.COMEDY)));
        movies.add(new Movie("Sharknado 5", "This is a hilarious movie!", List.of(MOVIEGENRES.ACTION, MOVIEGENRES.COMEDY)));
        movies.add(new Movie("Balls", "Yeah, no idea what this is about...", List.of(MOVIEGENRES.FANTASY, MOVIEGENRES.HORROR)));

        return movies;
    }

    public String listToString(List<MOVIEGENRES> list) {
        StringBuilder stringBuilder = new StringBuilder();

        // Iterate through the list and append each element to the StringBuilder
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i));

            // Append a space after each element except for the last one
            if (i < list.size() - 1) {
                stringBuilder.append("  ");
            }
        }
        return stringBuilder.toString();
    }
}
