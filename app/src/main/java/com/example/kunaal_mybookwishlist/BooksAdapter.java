package com.example.kunaal_mybookwishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class BooksAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Book> booksList;

    public BooksAdapter(Context context, ArrayList<Book> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int position) {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        }

        TextView bookTitleTextView = convertView.findViewById(R.id.bookTitleTextView);
        TextView bookAuthorTextView = convertView.findViewById(R.id.bookAuthorTextView);
        TextView bookGenreTextView = convertView.findViewById(R.id.bookGenreTextView);
        TextView bookStatusTextView = convertView.findViewById(R.id.bookStatusTextView);

        Book currentBook = booksList.get(position);
        bookTitleTextView.setText(currentBook.getTitle());
        bookAuthorTextView.setText(currentBook.getAuthor());
        bookGenreTextView.setText(currentBook.getGenre());
        bookStatusTextView.setText(currentBook.isRead() ? "Read" : "Unread");

        // Implement onClickListener if needed

        return convertView;
    }
}
