package com.example.kunaal_mybookwishlist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

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
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RequestCodes.ADD_BOOK_REQUEST) {
                String title = data.getStringExtra(AddEditBookActivity.EXTRA_TITLE);
                String author = data.getStringExtra(AddEditBookActivity.EXTRA_AUTHOR);
                String genre = data.getStringExtra(AddEditBookActivity.EXTRA_GENRE);
                int year = data.getIntExtra(AddEditBookActivity.EXTRA_YEAR, 0);
                boolean isRead = data.getBooleanExtra(AddEditBookActivity.EXTRA_READ, false);

                Book newBook = new Book(title, author, genre, year, isRead);
                booksList.add(newBook);
                adapter.notifyDataSetChanged();
            } else if (requestCode == RequestCodes.EDIT_BOOK_REQUEST) {
                // Handle book editing
            }

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

    public void addBook(Book book) {
        if (booksList != null && adapter != null) {
            booksList.add(book);
            adapter.notifyDataSetChanged(); // Notify the adapter to refresh the ListView
        }
    }
}
