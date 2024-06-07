package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.controllers.MovieListController;
import at.ac.fhcampuswien.fhmdb.controllers.WatchlistController;
import javafx.util.Callback;

public class MyFactory implements Callback<Class<?>, Object> {
    @Override
    public Object call(Class<?> aClass) {

        if (aClass == MovieListController.class) {
            return MovieListController.getInstance();
        }

        /*if (aClass == WatchlistController.class) {
            return MovieListController.getInstance();
        }*/

        // Fallback for other classes (if any)
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

