/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author branimir.maric
 */
public class Movie {

    public static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.RFC_1123_DATE_TIME; 

    private int idMovie;
    private String title;
    private LocalDateTime publishedDate;
    private String description;
    private String orginalTitle;
    private int duration;
    private int releaseYear;
    private String genre;
    private String picturePath;

    public Movie() {
    }

    public Movie(int idMovie, String title, LocalDateTime publishedDate, String description, String orginalTitle, int duration, int releaseYear, String genre, String picturePath) {
        this(title, publishedDate, description, orginalTitle, duration, releaseYear, genre, picturePath);
        this.idMovie = idMovie;
    }

    public Movie(String title, LocalDateTime publishedDate, String description, String orginalTitle, int duration, int releaseYear, String genre, String picturePath) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.orginalTitle = orginalTitle;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.picturePath = picturePath;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrginalTitle() {
        return orginalTitle;
    }

    public void setOrginalTitle(String orginalTitle) {
        this.orginalTitle = orginalTitle;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return idMovie + " - " + title;
    }

}
