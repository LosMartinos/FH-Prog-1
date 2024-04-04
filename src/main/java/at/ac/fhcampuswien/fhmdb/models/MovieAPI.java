package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;

public class MovieAPI {
    public static final String[] movieGenres = {
            "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY",
            "COMEDY", "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY",
            "FANTASY", "HISTORY", "HORROR", "MUSICAL", "MYSTERY",
            "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR", "WESTERN"
    };

    public static String createUrlForMovieAPI(String query, String genre, String released, String rating) {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host("prog2.fh-campuswien.ac.at")
                .addPathSegment("movies");

        if (query != null) urlBuilder.addQueryParameter("query", query);
        if (genre != null) urlBuilder.addQueryParameter("genre", genre);
        if (released != null) urlBuilder.addQueryParameter("releaseYear", released);
        if (rating != null) urlBuilder.addQueryParameter("ratingFrom", rating);

        return urlBuilder.build().toString();
    }

    public static String okhttpGetRequestForMovieAPI(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "FHMDb Client")
                .build();

        try {
            return new OkHttpClient().newCall(request).execute().body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Movie> jsonStringToMovieObjects(String json) {
        return new Gson().fromJson(json, new TypeToken<List<Movie>>(){}.getType());
    }
}
