/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import java.util.List;


public class Movie {

    private int movieId;
    private String name;
    private String description;
    private String poster;
    private String trailer;
    private Date releaseDate;
    private String country;
    private String director;
    private int ageRestricted;
    private String actors;
    private int duration;
    private int status;
    private List<Genre> genres;
    public Movie() {
    }

    public Movie(int movieId) {
        this.movieId = movieId;
    }

    public Movie(int movieId, String name, String description, String poster, String trailer, Date releaseDate, String country, String director, int ageRestricted, String actors, int duration, int status, List<Genre> genres) {
        this.movieId = movieId;
        this.name = name;
        this.description = description;
        this.poster = poster;
        this.trailer = trailer;
        this.releaseDate = releaseDate;
        this.country = country;
        this.director = director;
        this.ageRestricted = ageRestricted;
        this.actors = actors;
        this.duration = duration;
        this.status = status;
        this.genres = genres;
    }



    public int getMovieId() {
        return movieId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPoster() {
        return poster;
    }

    public String getTrailer() {
        return trailer;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getCountry() {
        return country;
    }

    public String getDirector() {
        return director;
    }

    public int getAgeRestricted() {
        return ageRestricted;
    }

    public String getActors() {
        return actors;
    }

    public int getDuration() {
        return duration;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setAgeRestricted(int ageRestricted) {
        this.ageRestricted = ageRestricted;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "actors='" + actors + '\'' +
                ", movieId=" + movieId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", poster='" + poster + '\'' +
                ", trailer='" + trailer + '\'' +
                ", releaseDate=" + releaseDate +
                ", country='" + country + '\'' +
                ", director='" + director + '\'' +
                ", ageRestricted=" + ageRestricted +
                ", duration=" + duration +
                ", status=" + status +
                ", genres=" + genres +
                '}';
    }
}
