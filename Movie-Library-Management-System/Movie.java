// Movie.java
// Represents a single movie record in the Movie Collection Management System

public class Movie implements Comparable<Movie> {

    // ====== Attributes ======
    private String movieID;
    private String title;
    private String director;
    private String genre;
    private int releaseYear;
    private int rating; // 1–5 stars

    // ====== Constructor ======
    public Movie(String movieID, String title, String director, String genre, int releaseYear, int rating) {
        this.movieID = movieID;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    // ====== Getters & Setters ======
    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // ====== Comparable Implementation ======
    @Override
    public int compareTo(Movie other) {
        return this.title.compareToIgnoreCase(other.title); // Default: sort by title
    }

    // ====== toString() ======
    @Override
    public String toString() {
        return movieID + ", " + title + ", " + director + ", " + genre + ", " + releaseYear + ", " + rating;
    }
}
