package com.example.kunaal_mybookwishlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Book> booksList;

    private BooksAdapter adapter;
    private RecyclerView booksRecyclerView;
    private TextView totalCountTextView;
    private FloatingActionButton addBookFab;
    private ListView booksListView;



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
            startActivityForResult(intent, RequestCodes.ADD_BOOK_REQUEST);
        });

        booksListView.setOnItemClickListener((parent, view, position, id) -> {
            Book selectedBook = booksList.get(position);
            Intent intent = new Intent(MainActivity.this, AddEditBookActivity.class);
            intent.putExtra(AddEditBookActivity.EXTRA_POSITION, position); // Make sure this is used to pass the list position or a unique book ID
            intent.putExtra(AddEditBookActivity.EXTRA_TITLE, selectedBook.getTitle());
            intent.putExtra(AddEditBookActivity.EXTRA_AUTHOR, selectedBook.getAuthor());
            intent.putExtra(AddEditBookActivity.EXTRA_GENRE, selectedBook.getGenre());
            intent.putExtra(AddEditBookActivity.EXTRA_YEAR, selectedBook.getPublicationYear());
            intent.putExtra(AddEditBookActivity.EXTRA_READ, selectedBook.isRead());
            startActivityForResult(intent, RequestCodes.EDIT_BOOK_REQUEST);
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
            if (requestCode == RequestCodes.ADD_BOOK_REQUEST) {
                // Extract book details from Intent and add a new Book object to the list
                String title = data.getStringExtra(AddEditBookActivity.EXTRA_TITLE);
                String author = data.getStringExtra(AddEditBookActivity.EXTRA_AUTHOR);
                String genre = data.getStringExtra(AddEditBookActivity.EXTRA_GENRE);
                int year = data.getIntExtra(AddEditBookActivity.EXTRA_YEAR, 0);
                boolean isRead = data.getBooleanExtra(AddEditBookActivity.EXTRA_READ, false);

                Book newBook = new Book(title, author, genre, year, isRead);
                booksList.add(newBook);
            } else if (requestCode == RequestCodes.EDIT_BOOK_REQUEST) {
                // Extract updated book details and position from Intent
                int position = data.getIntExtra(AddEditBookActivity.EXTRA_POSITION, -1);
                if (position != -1) {
                    String title = data.getStringExtra(AddEditBookActivity.EXTRA_TITLE);
                    String author = data.getStringExtra(AddEditBookActivity.EXTRA_AUTHOR);
                    String genre = data.getStringExtra(AddEditBookActivity.EXTRA_GENRE);
                    int year = data.getIntExtra(AddEditBookActivity.EXTRA_YEAR, 0);
                    boolean isRead = data.getBooleanExtra(AddEditBookActivity.EXTRA_READ, false);

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
