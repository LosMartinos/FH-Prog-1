package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "")
public class WatchlistMovieEntity extends MovieEntity {
    public WatchlistMovieEntity(String apiID) {
        super(apiID);
    }
}
