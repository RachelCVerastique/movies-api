package main;

import com.google.gson.Gson;
import data.movies.MoviesDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name="FindOneServlet", urlPatterns="/movies/findOne/*")


public class FindOneServlet extends HttpServlet {
    private MoviesDao dao = MoviesDaoFactory.getMoviesDao(MoviesDaoFactory.DAOType.MYSQL);
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        try {

            int id = new Gson().fromJson(request.getReader(), int.class);
            out.println(new Gson().toJson(dao.findOne(id)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
