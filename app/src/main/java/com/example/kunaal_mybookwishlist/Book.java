package com.example.kunaal_mybookwishlist;

public class Book {
    /**
     *
     *  The Book class represents a book entity in the application. It has book details
        including its title, author, genre, publication year, and read status.

     * Purpose:
        - To model the essential attributes of a book necessary for the application's functionality.
        - To provide a clear and encapsulated representation of a book, making it easy to manage and
          manipulate book data.

    * Design Rationale:
        - The class uses private fields. It ensures that book details can only be accessed and
          modified through the provided public getters and setters, maintaining control over the integrity of the data.
        - A constructor initializes a book object with all its attributes, facilitating
          the creation of fully defined book objects in a single step.
        - Getters and setters are provided for all fields, allowing for both read and write operations
          on book attributes.

        **/

    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private boolean isRead;

    public Book(String title, String author, String genre, int publicationYear, boolean isRead) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.isRead = isRead;
    }

    public boolean isRead() {
        return isRead;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

}
