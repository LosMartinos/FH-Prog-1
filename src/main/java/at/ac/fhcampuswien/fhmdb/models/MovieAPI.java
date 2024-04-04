package at.ac.fhcampuswien.fhmdb.models;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MovieAPI {
    private static final String BASE_URL = "https://prog2.fh-campuswien.ac.at/movies";
    private static final String USER_AGENT = "FHMDb Client";

    private OkHttpClient client;
    private Gson gson;

    public MovieAPI() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public List<Movie> getAllMovies() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("User-Agent", USER_AGENT)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseData = response.body().string();

            Type listType = new TypeToken<List<Movie>>(){}.getType();

            // Parse the JSON array into a list of Movie objects
            return gson.fromJson(responseData, listType);
        }
    }
}
