# 🎬 Movie Library Management System

## 🚀 About the Project

This project is a Java-based desktop application for managing a digital movie collection. It allows users to load movie data from a file, manage records, and perform various operations such as searching, sorting, and generating statistics.

The application is built using JavaFX and demonstrates object-oriented design, file handling, and data processing.

---

## 💡 Features

* 📂 Load movie data from a text file
* ➕ Add new movies
* ❌ Delete movies by ID
* 🔍 Search movies by title or ID
* ✏️ Edit existing movie records
* ↕️ Sort movies (by title, director, or release year)
* 📊 Generate statistics:

  * Movies by genre
  * Movies by director
  * Movies per year
  * Most/least active years
  * Top/least frequent directors
  * Active director detection
* 💾 Save updated data to a file

---

## 🧱 Tech Stack

* **Java**
* **JavaFX** (GUI)
* **ArrayList** (Data structure)
* **File Handling (I/O)**

---

## 🏗️ System Design

The project is structured into multiple classes:

* `Movie` → Represents a movie entity
* `MovieManager` → Handles all operations (CRUD, search, sort, statistics)
* `FileHandler` → Reads/writes data from/to files
* `MovieLibraryApp` → Main JavaFX application (UI + interaction)

This separation improves maintainability and code organization.

---

## ▶️ How to Run

1. Open the project in IntelliJ IDEA
2. Make sure JavaFX is configured
3. Run `MovieLibraryApp.java`
4. Load a `.txt` file containing movie records

---

## 📄 Sample Data Format

```id="q7u3mx"
MovieID, Title, Director, Genre, Release Year, Rating
001, The Matrix, Lana Wachowski, Sci-Fi, 1999, 5
002, Toy Story, John Lasseter, Animation, 1995, 5
```

---

## 🎯 What I Learned

* Building GUI applications using JavaFX
* Applying OOP principles (encapsulation, separation of concerns)
* Working with file input/output
* Implementing search, sorting, and data analysis features

---

## 🔮 Future Improvements

* Connect to a database instead of text files
* Add advanced filters and UI enhancements
* Export statistics as charts
* Improve user experience and validation

---

## 👩‍💻 Author

**Sama Baraawi**
Computer Science Student
