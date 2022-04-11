
package data.movies;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InMemoryMoviesDao implements MoviesDao {

    private HashMap<Integer, Movie> moviesMap = new HashMap<>();

    @Override
    public List<Movie> all() throws SQLException {
        return moviesMap != null ? new ArrayList(moviesMap.values()) : null;
    }

    @Override
    public Movie findOne(int id) {
        return moviesMap != null ? moviesMap.get(id) : null;
    }

    @Override
    public void insert(Movie movie) {
        int newId = moviesMap.keySet().size() + 1;
        movie.setId(newId);
        moviesMap.put(newId, movie);
    }
// recieves an array of movies and turns in to a hashmap
    @Override
    public void insertAll(Movie[] movies) throws SQLException {
        moviesMap = getMoviesMap(Arrays.asList(movies));
    }

    @Override
    public void update(Movie movie) throws SQLException {
        if (moviesMap != null) {
            moviesMap.replace(movie.getId(), movie);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        if (moviesMap != null) {
            moviesMap.remove(id);
        }
    }



    private HashMap<Integer, Movie> getMoviesMap(List<Movie> movies) {
        HashMap<Integer, Movie> movieHashMap = new HashMap<>();
        int counter = 1;
        for (Movie movie : movies) {
            movieHashMap.put(counter, movie);
            movie.setId(counter);
            counter++;
        }
        return movieHashMap;
    }

}

