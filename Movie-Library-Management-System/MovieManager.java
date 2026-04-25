// MovieManager.java
// Handles all movie-related operations such as add, delete, search, sort, and statistics

import java.util.*;
import java.time.Year;

public class MovieManager {

    // ====== Data Structure ======
    private ArrayList<Movie> movies;

    // ====== Constructor ======
    public MovieManager() {
        movies = new ArrayList<>();
    }

    // ====== Basic Operations ======

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public boolean deleteMovie(String movieID) {
        return movies.removeIf(m -> m.getMovieID().equalsIgnoreCase(movieID));
    }

    public Movie searchByID(String movieID) {
        for (Movie m : movies) {
            if (m.getMovieID().equalsIgnoreCase(movieID)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Movie> searchByTitle(String keyword) {
        ArrayList<Movie> result = new ArrayList<>();
        for (Movie m : movies) {
            if (m.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(m);
            }
        }
        return result;
    }

    public ArrayList<Movie> getAllMovies() {
        return movies;
    }

    // ====== Sorting ======

    public void sortByTitle() {
        Collections.sort(movies, Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER));
    }

    public void sortByDirector() {
        Collections.sort(movies, Comparator.comparing(Movie::getDirector, String.CASE_INSENSITIVE_ORDER));
    }

    public void sortByYear() {
        Collections.sort(movies, Comparator.comparingInt(Movie::getReleaseYear));
    }

    // ====== Editing ======
    public boolean editMovie(String movieID, String newTitle, String newDirector, String newGenre, int newYear, int newRating) {
        Movie m = searchByID(movieID);
        if (m != null) {
            m.setTitle(newTitle);
            m.setDirector(newDirector);
            m.setGenre(newGenre);
            m.setReleaseYear(newYear);
            m.setRating(newRating);
            return true;
        }
        return false;
    }

    // ====== Statistics ======
    public Map<String, Long> countByGenre() {
        return movies.stream().collect(
                java.util.stream.Collectors.groupingBy(Movie::getGenre, java.util.stream.Collectors.counting())
        );
    }

    public Map<String, Long> countByDirector() {
        return movies.stream().collect(
                java.util.stream.Collectors.groupingBy(Movie::getDirector, java.util.stream.Collectors.counting())
        );
    }

    public Map<Integer, Long> countByYear() {
        return movies.stream().collect(
                java.util.stream.Collectors.groupingBy(Movie::getReleaseYear, java.util.stream.Collectors.counting())
        );
    }

    public int getYearWithMaxMovies() {
        return countByYear().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    public int getYearWithMinMovies() {
        return countByYear().entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    public String getDirectorWithMaxMovies() {
        return countByDirector().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public String getDirectorWithMinMovies() {
        return countByDirector().entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public ArrayList<Movie> getMoviesByDirector(String directorName) {
        ArrayList<Movie> result = new ArrayList<>();
        for (Movie m : movies) {
            if (m.getDirector().equalsIgnoreCase(directorName)) {
                result.add(m);
            }
        }
        return result;
    }

    // ====== Active Director Check ======
    public boolean isDirectorActive(String directorName) {
        int currentYear = Year.now().getValue();
        for (Movie m : movies) {
            if (m.getDirector().equalsIgnoreCase(directorName)
                    && (currentYear - m.getReleaseYear()) <= 5) {
                return true;
            }
        }
        return false;
    }
}
