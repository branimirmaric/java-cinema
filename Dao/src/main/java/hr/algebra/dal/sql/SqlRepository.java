/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Movie;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class SqlRepository implements Repository {
    
    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "Description";
    private static final String ORGINAL_TITLE = "OrginalTitle";
    private static final String DURATION = "Duration";
    private static final String RELEASE_YEAR = "ReleaseYear";
    private static final String GENRE = "Genre";
    private static final String PICTURE_PATH = "PicturePath";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";

    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); 
                CallableStatement stmt = connection.prepareCall(CREATE_MOVIE);) {

            stmt.setString(TITLE, movie.getTitle());
            stmt.setString(PUBLISHED_DATE, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setString(ORGINAL_TITLE, movie.getOrginalTitle());
            stmt.setInt(DURATION, movie.getDuration());
            stmt.setInt(RELEASE_YEAR, movie.getReleaseYear());
            stmt.setString(GENRE, movie.getGenre());
            stmt.setString(PICTURE_PATH, movie.getPicturePath());

            stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);

            stmt.executeUpdate();

            return stmt.getInt(ID_MOVIE);
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); 
                CallableStatement stmt = connection.prepareCall(CREATE_MOVIE);) {
            for (Movie movie : movies) {                
                stmt.setString(TITLE, movie.getTitle());
                stmt.setString(PUBLISHED_DATE, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
                stmt.setString(DESCRIPTION, movie.getDescription());
                stmt.setString(ORGINAL_TITLE, movie.getOrginalTitle());
                stmt.setInt(DURATION, movie.getDuration());
                stmt.setInt(RELEASE_YEAR, movie.getReleaseYear());
                stmt.setString(GENRE, movie.getGenre());
                stmt.setString(PICTURE_PATH, movie.getPicturePath());

                stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);
                
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void updateMovie(int id, Movie data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); 
                CallableStatement stmt = connection.prepareCall(UPDATE_MOVIE);) {

            stmt.setString(TITLE, data.getTitle());
            stmt.setString(PUBLISHED_DATE, data.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setString(DESCRIPTION, data.getDescription());
            stmt.setString(ORGINAL_TITLE, data.getOrginalTitle());
            stmt.setInt(DURATION, data.getDuration());
            stmt.setInt(RELEASE_YEAR, data.getReleaseYear());
            stmt.setString(GENRE, data.getGenre());
            stmt.setString(PICTURE_PATH, data.getPicturePath());

            stmt.setInt(ID_MOVIE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); 
                CallableStatement stmt = connection.prepareCall(DELETE_MOVIE);) {

            stmt.setInt(ID_MOVIE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); 
                CallableStatement stmt = connection.prepareCall(SELECT_MOVIE);) {

            stmt.setInt(ID_MOVIE, id);

            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    // staticki ctor
                    return Optional.of(
                            new Movie(
                                    rs.getInt(ID_MOVIE),
                                    rs.getString(TITLE),
                                    LocalDateTime.parse(
                                            rs.getString(PUBLISHED_DATE),
                                            Movie.DATE_FORMATTER),
                                    rs.getString(DESCRIPTION),
                                    rs.getString(ORGINAL_TITLE),
                                    rs.getInt(DURATION),
                                    rs.getInt(RELEASE_YEAR),
                                    rs.getString(GENRE),
                                    rs.getString(PICTURE_PATH)
                            )
                    );
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection connection = dataSource.getConnection(); 
                CallableStatement stmt = connection.prepareCall(SELECT_MOVIES); 
                ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                movies.add(
                        new Movie(
                                rs.getInt(ID_MOVIE),
                                rs.getString(TITLE),
                                LocalDateTime.parse(
                                        rs.getString(PUBLISHED_DATE),
                                        Movie.DATE_FORMATTER),
                                rs.getString(DESCRIPTION),
                                rs.getString(ORGINAL_TITLE),
                                rs.getInt(DURATION),
                                rs.getInt(RELEASE_YEAR),
                                rs.getString(GENRE),
                                rs.getString(PICTURE_PATH)
                        )
                );
            }
        }
        return movies;
    }
}
