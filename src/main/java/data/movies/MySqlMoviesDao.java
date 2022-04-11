package data.movies;


import com.mysql.cj.jdbc.Driver;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMoviesDao implements MoviesDao {

    private Connection connection;

    public MySqlMoviesDao() {
        //We will configure our connections here

        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + Config.DB_HOST + ":3306/docrob?allowPublicKeyRetrieval=true&useSSL=false",
                    Config.DB_USER,
                    Config.DB_PW);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Movie> all() throws SQLException {
        // TODO: Get ALL the movies
        ArrayList<Movie> movies = new ArrayList<>();

        PreparedStatement st = connection.prepareStatement("SELECT * FROM movies");

        ResultSet rs = st.executeQuery();

        while(rs.next()) {
            Movie movie = new Movie();
            movie.setId(rs.getInt("id"));
            movie.setTitle(rs.getString("title"));
            movie.setRating(rs.getDouble("rating"));
            movie.setPoster(rs.getString("poster"));
            movie.setYear(rs.getInt("year"));
            movie.setGenre(rs.getString("genre"));
            movie.setDirector(rs.getString("director"));
            movie.setPlot(rs.getString("plot"));
            movie.setActors(rs.getString("actors"));

            movies.add(movie);
        }

        rs.close();
        st.close();

        return movies;
    }

    @Override
    public Movie findOne(int id) {
        Movie findMovie = null;
        try {
            Statement statement = connection.createStatement();

            ResultSet rs = null;
            rs = statement.executeQuery("SELECT * FROM movies WHERE id = " + id);
            rs.next();

            findMovie = new Movie(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getDouble("rating"),
                    rs.getString("poster"),
                    rs.getInt("year"),
                    rs.getString("genre"),
                    rs.getString("director"),
                    rs.getString("plot"),
                    rs.getString("actors")

            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findMovie;
    }

    @Override
    public void insert(Movie movie) {
        try {
            // make a preparedstatement
            PreparedStatement ps = connection.prepareStatement("insert into movies " +
                    " (title, rating, poster, year, genre, director, plot, actors) " +
                    " values (?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            // set all of the user info in the preparedstatement
            ps.setString(1, movie.getTitle());
            ps.setDouble(2, movie.getRating());
            ps.setString(3, movie.getPoster());
            ps.setInt(4, movie.getYear());
            ps.setString(5, movie.getGenre());
            ps.setString(6, movie.getDirector());
            ps.setString(7, movie.getPlot());
            ps.setString(8, movie.getActors());
            // execute the query
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int newId = keys.getInt(0);
            // do something with the id from the new record if necessary
            System.out.println("id from the newly inserted movie is " + newId);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAll(Movie[] movies) throws SQLException {
        //iterares over the given movies
        for(Movie movie : movies) {
            // call insert for each of the movies
            insert(movie);
        }

    }

    @Override
    public void update(Movie movie) throws SQLException {
        // assumption: movie only has the fields that we need to update
        String query = "update movies set";
        Movie changeMovie = findOne(movie.getId());

        if (movie.getTitle() != null) {
            changeMovie.setTitle(movie.getTitle());
        }
        if (movie.getYear() != null) {
            changeMovie.setYear(movie.getYear());
        }
        if (movie.getPoster() != null) {
            changeMovie.setPoster(movie.getPoster());
        }
        if (movie.getRating() != null) {
            changeMovie.setRating(movie.getRating());
        }
        if (movie.getGenre() != null) {
            changeMovie.setGenre(movie.getGenre());
        }
        if (movie.getDirector() != null) {
            changeMovie.setDirector(movie.getDirector());
        }
        if (movie.getActors() != null) {
            changeMovie.setActors(movie.getActors());
        }

        StringBuilder sql = new StringBuilder("UPDATE movies SET title = ?, poster = ?, year = ?, genre = ?, director = ?, plot = ?, WHERE id = ? ");


        PreparedStatement statement = connection.prepareStatement((sql.toString()));
        statement.setString(1, changeMovie.getTitle());
        statement.setString(2, changeMovie.getRating());
        statement.setString(3, changeMovie.getPoster());
        statement.setString(4, changeMovie.getYear());
        statement.setString(5, changeMovie.getGenre());
        statement.setString(6, changeMovie.getDirector());
        statement.setString(7, changeMovie.getActors());
        statement.setString(8, changeMovie.getPlot());
        statement.setString(9, changeMovie.getId());




//
//        // get rif og trailing comma
//        query = query.substring(0, query.length() - 1);
//        query += "where id = ? ";
//
//        //creatre prepareed statemtne from the query string
//        PreparedStatement ps = connection.prepareStatement(query);
//
//        int currentIndex = 1;
//        if (movie.getTitle() != null) {
//            ps.setString(currentIndex, movie.getTitle());
//            currentIndex++;
//        }
//        if (movie.getRating() != null) {
//            ps.setDouble(currentIndex, movie.getRating());
//            currentIndex++;
//        }
//        ps.setDouble(currentIndex);
//        ps.executeUpdate();
    }




    @Override
    public void delete(int id) throws SQLException {
        //make a prepared statement and assemble the delete query
        PreparedStatement st = connection.prepareStatement("DELETE FROM movies WHERE id = ?");

        //set parameters for the statement
        st.setInt(1,id);

        //execute the statement
        st.executeUpdate();
    }

    public void cleanUp() {
        System.out.println("Calling clean up.....");

        try {
            connection.close();
        } catch (SQLException) {
            e.printStackTrace();
        }
    }
}

