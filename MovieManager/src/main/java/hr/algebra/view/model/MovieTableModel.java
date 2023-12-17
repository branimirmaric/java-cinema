/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import hr.algebra.model.Movie;

/**
 *
 * @author branimir.maric
 */
public class MovieTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
        "Id", "Title", "Published date", "Description", "Orginal title", "Duration", "Release year", "Genre", "Picture path"
    };

    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return movies.get(rowIndex).getIdMovie();
            case 1:
                return movies.get(rowIndex).getTitle();
            case 2:
                return movies.get(rowIndex).getPublishedDate().format(Movie.DATE_FORMATTER);
            case 3:
                return movies.get(rowIndex).getDescription();
            case 4:
                return movies.get(rowIndex).getOrginalTitle();
            case 5:
                return movies.get(rowIndex).getDuration();
            case 6:
                return movies.get(rowIndex).getReleaseYear();
            case 7:
                return movies.get(rowIndex).getGenre();
            case 8:
                return movies.get(rowIndex).getPicturePath();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }

        return super.getColumnClass(columnIndex);
    }

}
