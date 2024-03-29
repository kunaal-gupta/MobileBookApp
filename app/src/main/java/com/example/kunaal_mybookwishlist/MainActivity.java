package com.example.kunaal_mybookwishlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    /**
     * MainActivity serves as the entry point for the app, displaying a list of books and providing options to add, edit, and delete books.

     * Purpose:
         - MainActivity serves as the primary interface for displaying and managing a list of
            books, allowing users to add, edit, and delete book entries.

     * Design Rationales:
         - ListView with Custom Adapter: Offers efficient display and customization of list items, enhancing user experience and performance.
         - FloatingActionButton: Used for adding new books, providing a clear and accessible action point following Material Design principles.
         - Activity Results: Facilitates data exchange between MainActivity and AddEditBookActivity, ensuring UI updates only upon confirmed changes.
         - Click Listeners: Enable intuitive interaction with list items for editing and deletion, leveraging familiar user gestures.
         - AlertDialog for Deletion: Confirms user intent before deleting a book, preventing accidental data loss.

     * Outstanding Issues:
        - No such issues at present

     **/

    private ArrayList<Book> booksList;

    private BooksAdapter adapter;
    private TextView totalCountTextView;
    private FloatingActionButton addBookFab;
    private ListView booksListView;

    int addBookRequest = 1;
    int editBookRequest = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        booksList = new ArrayList<>();
        adapter = new BooksAdapter(this, booksList); // Ensure BooksAdapter is compatible with ListView
        booksListView = findViewById(R.id.booksListView);
        booksListView.setAdapter(adapter);

        totalCountTextView = findViewById(R.id.totalCountTextView);
        updateTotalCount();

        addBookFab = findViewById(R.id.addBookFab);
        addBookFab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddEditBookActivity.class);
            startActivityForResult(intent, addBookRequest);
        });

        booksListView.setOnItemClickListener((parent, view, position, id) -> {
            Book selectedBook = booksList.get(position);
            Intent intent = new Intent(MainActivity.this, AddEditBookActivity.class);
            intent.putExtra(AddEditBookActivity.extraPosition, position); // Make sure this is used to pass the list position or a unique book ID
            intent.putExtra(AddEditBookActivity.extraTitle, selectedBook.getTitle());
            intent.putExtra(AddEditBookActivity.extraAuthor, selectedBook.getAuthor());
            intent.putExtra(AddEditBookActivity.extraGenre, selectedBook.getGenre());
            intent.putExtra(AddEditBookActivity.extraYear, selectedBook.getPublicationYear());
            intent.putExtra(AddEditBookActivity.extraRead, selectedBook.isRead());
            startActivityForResult(intent, editBookRequest);
        });

        booksListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Book")
                        .setMessage("Are you sure you want to delete this book?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            booksList.remove(position);
                            adapter.notifyDataSetChanged();
                            updateTotalCount();
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true; // return true to indicate that we have handled the event
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == addBookRequest) {
                // Extract book details from Intent and add a new Book object to the list
                String title = data.getStringExtra(AddEditBookActivity.extraTitle);
                String author = data.getStringExtra(AddEditBookActivity.extraAuthor);
                String genre = data.getStringExtra(AddEditBookActivity.extraGenre);
                int year = data.getIntExtra(AddEditBookActivity.extraYear, 0);
                boolean isRead = data.getBooleanExtra(AddEditBookActivity.extraRead, false);

                Book newBook = new Book(title, author, genre, year, isRead);
                booksList.add(newBook);
            } else if (requestCode == editBookRequest) {
                // Extract updated book details and position from Intent
                int position = data.getIntExtra(AddEditBookActivity.extraPosition, -1);
                if (position != -1) {
                    String title = data.getStringExtra(AddEditBookActivity.extraTitle);
                    String author = data.getStringExtra(AddEditBookActivity.extraAuthor);
                    String genre = data.getStringExtra(AddEditBookActivity.extraGenre);
                    int year = data.getIntExtra(AddEditBookActivity.extraYear, 0);
                    boolean isRead = data.getBooleanExtra(AddEditBookActivity.extraRead, false);

                    // Create an updated Book object and replace the old one at the position
                    Book updatedBook = new Book(title, author, genre, year, isRead);
                    booksList.set(position, updatedBook);
                }
            }

            // Notify the adapter of data changes and update the book count display
            adapter.notifyDataSetChanged();
            updateTotalCount();
        }
    }


    private void updateTotalCount() {
        int total = booksList.size();
        int readCount = 0;
        for (Book book : booksList) {
            if (book.isRead()) readCount++;
        }
        totalCountTextView.setText(String.format(Locale.getDefault(), "Total Books: %d, Read: %d", total, readCount));
    }

}
