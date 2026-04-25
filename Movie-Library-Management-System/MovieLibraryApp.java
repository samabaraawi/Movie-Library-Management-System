// MovieLibraryApp.java
// Main GUI application for the Movie Collection Management System

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class MovieLibraryApp extends Application {

    private MovieManager manager = new MovieManager();
    private TableView<Movie> tableView = new TableView<>();
    private ObservableList<Movie> observableMovies;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("🎬 Movie Collection Management System");

        // ====== Table Columns ======
        TableColumn<Movie, String> idCol = new TableColumn<>("Movie ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("movieID"));

        TableColumn<Movie, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Movie, String> dirCol = new TableColumn<>("Director");
        dirCol.setCellValueFactory(new PropertyValueFactory<>("director"));

        TableColumn<Movie, String> genreCol = new TableColumn<>("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Movie, Integer> yearCol = new TableColumn<>("Release Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));

        TableColumn<Movie, Integer> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        tableView.getColumns().addAll(idCol, titleCol, dirCol, genreCol, yearCol, ratingCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ====== Buttons ======
        Button loadBtn = new Button("📂 Load File");
        Button addBtn = new Button("➕ Add Movie");
        Button deleteBtn = new Button("❌ Delete Movie");
        Button searchBtn = new Button("🔍 Search");
        Button sortBtn = new Button("↕️ Sort");
        Button statsBtn = new Button("📊 Statistics");
        Button saveBtn = new Button("💾 Save");

        // ====== Button Actions ======
        loadBtn.setOnAction(e -> loadMovies(primaryStage));
        addBtn.setOnAction(e -> addMovie());
        deleteBtn.setOnAction(e -> deleteMovie());
        searchBtn.setOnAction(e -> searchMovie());
        sortBtn.setOnAction(e -> sortMovies());
        statsBtn.setOnAction(e -> showStatistics());
        saveBtn.setOnAction(e -> saveMovies());

        // ====== Layout ======
        HBox buttonBar = new HBox(10, loadBtn, addBtn, deleteBtn, searchBtn, sortBtn, statsBtn, saveBtn);
        buttonBar.setPadding(new Insets(10));
        buttonBar.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, tableView, buttonBar);
        root.setPadding(new Insets(10));

        // ====== Scene ======
        Scene scene = new Scene(root, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ====== Load Movies ======
    private void loadMovies(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select movies.txt file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            ArrayList<Movie> loaded = FileHandler.readMoviesFromFile(file.getAbsolutePath());
            manager.getAllMovies().clear();
            manager.getAllMovies().addAll(loaded);
            refreshTable();
            showAlert("Success", "Movies loaded successfully!");
        }
    }

    // ====== Add Movie ======
    private void addMovie() {
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle("Add New Movie");

        // Labels + Inputs
        TextField idField = new TextField();
        TextField titleField = new TextField();
        TextField dirField = new TextField();
        TextField genreField = new TextField();
        TextField yearField = new TextField();
        TextField ratingField = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.addRow(0, new Label("Movie ID:"), idField);
        grid.addRow(1, new Label("Title:"), titleField);
        grid.addRow(2, new Label("Director:"), dirField);
        grid.addRow(3, new Label("Genre:"), genreField);
        grid.addRow(4, new Label("Release Year:"), yearField);
        grid.addRow(5, new Label("Rating (1–5):"), ratingField);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    return new Movie(
                            idField.getText(),
                            titleField.getText(),
                            dirField.getText(),
                            genreField.getText(),
                            Integer.parseInt(yearField.getText()),
                            Integer.parseInt(ratingField.getText())
                    );
                } catch (Exception ex) {
                    showAlert("Error", "Invalid input. Please enter valid data.");
                }
            }
            return null;
        });

        Optional<Movie> result = dialog.showAndWait();
        result.ifPresent(movie -> {
            manager.addMovie(movie);
            refreshTable();
        });
    }

    // ====== Delete Movie ======
    private void deleteMovie() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Movie");
        dialog.setHeaderText("Enter Movie ID to delete:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(id -> {
            boolean deleted = manager.deleteMovie(id);
            if (deleted) {
                refreshTable();
                showAlert("Deleted", "Movie deleted successfully!");
            } else {
                showAlert("Not Found", "No movie found with ID: " + id);
            }
        });
    }

    // ====== Search Movie ======
    private void searchMovie() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Movie");
        dialog.setHeaderText("Enter Movie Title or ID:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(keyword -> {
            ArrayList<Movie> found = manager.searchByTitle(keyword);
            Movie byID = manager.searchByID(keyword);
            if (byID != null && !found.contains(byID)) {
                found.add(byID);
            }

            if (found.isEmpty()) {
                showAlert("No Results", "No movies found for: " + keyword);
            } else {
                tableView.setItems(FXCollections.observableArrayList(found));
            }
        });
    }

    // ====== Sort ======
    private void sortMovies() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Title", "Title", "Director", "Year");
        dialog.setTitle("Sort Movies");
        dialog.setHeaderText("Choose sorting option:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(choice -> {
            switch (choice) {
                case "Title" -> manager.sortByTitle();
                case "Director" -> manager.sortByDirector();
                case "Year" -> manager.sortByYear();
            }
            refreshTable();
        });
    }

    // ====== Statistics ======
    private void showStatistics() {
        int maxYear = manager.getYearWithMaxMovies();
        int minYear = manager.getYearWithMinMovies();
        String topDirector = manager.getDirectorWithMaxMovies();
        String lowDirector = manager.getDirectorWithMinMovies();

        String stats = "🎬 Movie Statistics:\n\n" +
                "Year with Max Movies: " + maxYear + "\n" +
                "Year with Min Movies: " + minYear + "\n" +
                "Director with Most Movies: " + topDirector + "\n" +
                "Director with Fewest Movies: " + lowDirector + "\n";

        showAlert("Statistics", stats);
    }

    // ====== Save Movies ======
    private void saveMovies() {
        FileHandler.saveMoviesToFile("updatedMovies.txt", manager.getAllMovies());
        showAlert("Saved", "Movies saved successfully to updatedMovies.txt");
    }

    // ====== Helper ======
    private void refreshTable() {
        observableMovies = FXCollections.observableArrayList(manager.getAllMovies());
        tableView.setItems(observableMovies);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ====== Main ======
    public static void main(String[] args) {
        launch(args);
    }
}
