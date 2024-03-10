package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Comparable {
    private String title;
    private String description;

    // TODO add more properties here
    private List<MOVIEGENRES> genres;

    @Override
    public int compareTo(Object o) {
        return this.getTitle().compareTo(((Movie) o).getTitle());
    }

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
        movies.add(new Movie("Balls", "Yeah, no idea what this is about... But it won an oscar", List.of(MOVIEGENRES.FANTASY, MOVIEGENRES.HORROR)));
        movies.add(new Movie("The Lobster", "A dystopian comedy-drama where single people must find a romantic partner within 45 days or be turned into animals.", List.of(MOVIEGENRES.COMEDY)));
        movies.add(new Movie("Arcane", "A visually stunning animated series set in the gritty city of Piltover and the oppressed underground of Zaun, where two sisters find themselves on opposite sides of a brewing conflict that threatens to tear their world apart.", List.of(MOVIEGENRES.ACTION, MOVIEGENRES.DRAMA, MOVIEGENRES.ANIMATION)));
        movies.add(new Movie("Suits", "A legal drama series centered around a brilliant college dropout who lands a job as a law associate to a top Manhattan lawyer, despite lacking a law degree. With his photographic memory and quick wit, he navigates the cutthroat world of corporate law while hiding his secret.", List.of(MOVIEGENRES.DRAMA)));
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
