package com.example.kunaal_mybookwishlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditBookActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_ID = "com.example.kunaal_mybookwishlist.EXTRA_BOOK_ID";
    public static final String EXTRA_TITLE = "com.example.kunaal_mybookwishlist.EXTRA_TITLE";
    public static final String EXTRA_AUTHOR = "com.example.kunaal_mybookwishlist.EXTRA_AUTHOR";
    public static final String EXTRA_GENRE = "com.example.kunaal_mybookwishlist.EXTRA_GENRE";
    public static final String EXTRA_YEAR = "com.example.kunaal_mybookwishlist.EXTRA_YEAR";
    public static final String EXTRA_READ = "com.example.kunaal_mybookwishlist.EXTRA_READ";
    public static final String EXTRA_POSITION = "com.example.kunaal_mybookwishlist.EXTRA_POSITION";

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

        if (getIntent().hasExtra(EXTRA_POSITION)) { // This checks if we're editing
            setTitle("Edit Book");
            editTextBookTitle.setText(getIntent().getStringExtra(EXTRA_TITLE));
            editTextAuthor.setText(getIntent().getStringExtra(EXTRA_AUTHOR));
            editTextPublicationYear.setText(String.valueOf(getIntent().getIntExtra(EXTRA_YEAR, 0))); // Ensure year is passed as int
            readCheckBox.setChecked(getIntent().getBooleanExtra(EXTRA_READ, false));

            // Set the spinner to the correct genre
            if (genreSpinner.getAdapter() != null) {
                int spinnerPosition = adapter.getPosition(getIntent().getStringExtra(EXTRA_GENRE));
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

        if (title.trim().isEmpty() || author.trim().isEmpty() || yearString.trim().isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid year format.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        // Retrieve the position if editing a book, otherwise, this won't be included
        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        if (position != -1) {
            data.putExtra(EXTRA_POSITION, position);
        }
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_AUTHOR, author);
        data.putExtra(EXTRA_GENRE, genre);
        data.putExtra(EXTRA_YEAR, year);
        data.putExtra(EXTRA_READ, isRead);
        setResult(RESULT_OK, data);
        finish();
    }

}
