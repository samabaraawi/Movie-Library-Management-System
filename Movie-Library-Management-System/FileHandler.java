// FileHandler.java
// Responsible for reading and writing movie data from/to text files

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    // ====== Read Movies from File ======
    public static ArrayList<Movie> readMoviesFromFile(String fileName) {
        ArrayList<Movie> movies = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Skip empty lines or header line
                if (line.trim().isEmpty() || line.startsWith("MovieID")) continue;

                String[] parts = line.split(",");

                if (parts.length == 6) {
                    String id = parts[0].trim();
                    String title = parts[1].trim();
                    String director = parts[2].trim();
                    String genre = parts[3].trim();
                    int year = Integer.parseInt(parts[4].trim());
                    int rating = Integer.parseInt(parts[5].trim());

                    movies.add(new Movie(id, title, director, genre, year, rating));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("❌ File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }

        return movies;
    }

    // ====== Save Movies to File ======
    public static void saveMoviesToFile(String fileName, ArrayList<Movie> movies) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            // Write header line
            writer.println("MovieID, Title, Director, Genre, Release Year, Rating");

            // Write all movies
            for (Movie m : movies) {
                writer.println(m.toString());
            }

            System.out.println("✅ Data successfully saved to " + fileName);

        } catch (IOException e) {
            System.out.println("❌ Error writing to file: " + e.getMessage());
        }
    }
}
