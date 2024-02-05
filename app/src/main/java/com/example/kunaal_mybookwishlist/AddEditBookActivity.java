package com.example.kunaal_mybookwishlist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditBookActivity extends AppCompatActivity {
    /**
     * It facilitates adding a new book to the wishlist or editing an existing book's details.
       Presents a form where users can input or modify the book's title, author, genre publication year, and read status.

     Purpose:
      - It provides a user interface for entering the details of a new book or editing the details of an existing book.
      - It also allow returning the entered or modified book details to the MainActivity.

     * Design Rationale:
          - The activity uses EditTexts for text inputs (title, author, year), a Spinner for
            selecting a genre from a predefined list, and a CheckBox for marking the book as read or
             unread.
          - Intent extras are used to pass book details between MainActivity and AddEditBookActivity, allowing for the pre-population.
            of fields in edit mode and the return of new or edited book details.
          - A save button collects input data, validates it, and
            packages it into an intent result that is sent back to MainActivity.

     * Outstanding Issues:
        - No outstanding issues as such

     **/


    public static final String extraTitle = "com.example.kunaal_mybookwishlist.EXTRA_TITLE";
    public static final String extraAuthor = "com.example.kunaal_mybookwishlist.EXTRA_AUTHOR";
    public static final String extraGenre = "com.example.kunaal_mybookwishlist.EXTRA_GENRE";
    public static final String extraYear = "com.example.kunaal_mybookwishlist.EXTRA_YEAR";
    public static final String extraRead = "com.example.kunaal_mybookwishlist.EXTRA_READ";
    public static final String extraPosition = "com.example.kunaal_mybookwishlist.EXTRA_POSITION";

    private EditText editTextBookTitle, editTextAuthor, editTextPublicationYear;
    private Spinner genreSpinner;
    private CheckBox readCheckBox;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        editTextBookTitle = findViewById(R.id.editTextBookTitle);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextPublicationYear = findViewById(R.id.editTextPublicationYear);
        genreSpinner = findViewById(R.id.genreSpinner);
        readCheckBox = findViewById(R.id.readCheckBox);
        saveButton = findViewById(R.id.saveButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genres_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        saveButton.setOnClickListener(view -> saveBook());

        if (getIntent().hasExtra(extraPosition)) { // This checks if we're editing
            setTitle("Edit Book");
            editTextBookTitle.setText(getIntent().getStringExtra(extraTitle));
            editTextAuthor.setText(getIntent().getStringExtra(extraAuthor));
            editTextPublicationYear.setText(String.valueOf(getIntent().getIntExtra(extraYear, 0))); // Ensure year is passed as int
            readCheckBox.setChecked(getIntent().getBooleanExtra(extraRead, false));

            // Set the spinner to the correct genre
            if (genreSpinner.getAdapter() != null) {
                int spinnerPosition = adapter.getPosition(getIntent().getStringExtra(extraGenre));
                genreSpinner.setSelection(spinnerPosition);
            }
        } else {
            setTitle("Add Book");
        }
    }

    private void saveBook() {
        String title = editTextBookTitle.getText().toString();
        String author = editTextAuthor.getText().toString();
        String genre = genreSpinner.getSelectedItem().toString();
        String yearString = editTextPublicationYear.getText().toString();
        boolean isRead = readCheckBox.isChecked();

        // Check for empty fields
        if (title.trim().isEmpty() || author.trim().isEmpty() || yearString.trim().isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate title length
        if (title.length() > 50) {
            Toast.makeText(this, "Title can be up to 50 characters long.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate author length
        if (author.length() > 30) {
            Toast.makeText(this, "Author name can be up to 30 characters long.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate publication year format and range
        int year;
        try {
            year = Integer.parseInt(yearString);
            if (yearString.length() != 4) {
                throw new NumberFormatException("Year must be a four-digit number.");
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid year format. Please enter a four-digit year.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Package the data and send it back to the calling activity
        Intent data = new Intent();
        int position = getIntent().getIntExtra(extraPosition, -1);
        if (position != -1) {
            data.putExtra(extraPosition, position);
        }
        data.putExtra(extraTitle, title);
        data.putExtra(extraAuthor, author);
        data.putExtra(extraGenre, genre);
        data.putExtra(extraYear, year);
        data.putExtra(extraRead, isRead);
        setResult(RESULT_OK, data);
        finish();
    }

}
