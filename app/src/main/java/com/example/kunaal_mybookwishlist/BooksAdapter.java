package com.example.kunaal_mybookwishlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class BooksAdapter extends BaseAdapter {

    private static final String TAG = "BooksAdapter";
    private final Context context;
    private final ArrayList<Book> booksList;

    public BooksAdapter(Context context, ArrayList<Book> booksList) {
        this.context = context;
        this.booksList = booksList;
        Log.d(TAG, "BooksAdapter initialized with booksList size: " + booksList.size());
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Book getItem(int position) {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
            holder = new ViewHolder();
            holder.bookTitleTextView = convertView.findViewById(R.id.bookTitleTextView);
            holder.bookAuthorTextView = convertView.findViewById(R.id.bookAuthorTextView);
            holder.bookGenreTextView = convertView.findViewById(R.id.bookGenreTextView);
            holder.bookStatusTextView = convertView.findViewById(R.id.bookStatusTextView);
            convertView.setTag(holder);
            Log.d(TAG, "View created for position: " + position);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Book currentBook = getItem(position);
        holder.bookTitleTextView.setText(currentBook.getTitle());
        holder.bookAuthorTextView.setText(currentBook.getAuthor());
        holder.bookGenreTextView.setText(currentBook.getGenre());
        holder.bookStatusTextView.setText(currentBook.isRead() ? "Read" : "Unread");

        Log.d(TAG, "View set for position: " + position + " with Title: " + currentBook.getTitle());

        return convertView;
    }

    static class ViewHolder {
        TextView bookTitleTextView;
        TextView bookAuthorTextView;
        TextView bookGenreTextView;
        TextView bookStatusTextView;
    }
}
