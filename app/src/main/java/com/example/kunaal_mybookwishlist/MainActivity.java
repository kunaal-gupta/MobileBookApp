package com.example.kunaal_mybookwishlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        booksList = new ArrayList<>();
        adapter = new BooksAdapter(this, booksList);
        booksRecyclerView = findViewById(R.id.booksRecyclerView);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksRecyclerView.setAdapter(adapter);

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
            // Assuming the Intent data includes book details as extras
            if (requestCode == RequestCodes.ADD_BOOK_REQUEST) {
                // Extract book details and create a new Book object
                Book newBook = new Book(
                        data.getStringExtra("title"),
                        data.getStringExtra("author"),
                        data.getStringExtra("genre"),
                        data.getIntExtra("year", 0),
                        data.getBooleanExtra("read", false)
                );
                booksList.add(newBook);
                System.out.println("hiiiiiiiiiiiiiiiiii");
                System.out.println(booksList);
                adapter.notifyDataSetChanged();

            } else if (requestCode == RequestCodes.EDIT_BOOK_REQUEST) {
                // Update the existing book details
                // This requires identifying which book to update, which might involve passing an identifier or index
            }

            updateTotalCount();
            adapter.notifyDataSetChanged();
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
