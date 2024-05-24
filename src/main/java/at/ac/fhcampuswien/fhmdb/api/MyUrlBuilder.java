package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import okhttp3.HttpUrl;

public class MyUrlBuilder {

    private HttpUrl.Builder urlBuilder;


    public MyUrlBuilder(String BASE_URL) {
        this.urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
    }


    public MyUrlBuilder query(String query) {
        if (query != null && !query.isEmpty()) {
            this.urlBuilder.addQueryParameter("query", query);
        }
        return this;
    }

    public MyUrlBuilder genre(Genre genre) {
        if (genre != null) {
            this.urlBuilder.addQueryParameter("genre", String.valueOf(genre));
        }
        return this;
    }

    public MyUrlBuilder releaseYear(String releaseYear) {
        if (releaseYear != null) {
            this.urlBuilder.addQueryParameter("releaseYear", releaseYear);
        }
        return this;
    }

    public MyUrlBuilder rating(String ratingFrom) {
        if (ratingFrom != null) {
            this.urlBuilder.addQueryParameter("ratingFrom", ratingFrom);
        }
        return this;
    }

    public String build() {
        return urlBuilder.build().toString();
    }
}
