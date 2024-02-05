package com.example.kunaal_mybookwishlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private final Context context;
    private final ArrayList<Book> booksList;

    public BooksAdapter(Context context, ArrayList<Book> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book currentBook = booksList.get(position);
        holder.bookTitleTextView.setText(currentBook.getTitle());
        holder.bookAuthorTextView.setText(currentBook.getAuthor());
        holder.bookGenreTextView.setText(currentBook.getGenre());
        holder.bookStatusTextView.setText(currentBook.isRead() ? "Read" : "Unread");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use holder.getAdapterPosition() to get the latest position
                int latestPosition = holder.getAdapterPosition();
                if (latestPosition != RecyclerView.NO_POSITION) {
                    Book bookToEdit = booksList.get(latestPosition);
                    Intent intent = new Intent(view.getContext(), AddEditBookActivity.class);
                    intent.putExtra(AddEditBookActivity.EXTRA_POSITION, latestPosition);
                    intent.putExtra(AddEditBookActivity.EXTRA_TITLE, bookToEdit.getTitle());
                    intent.putExtra(AddEditBookActivity.EXTRA_AUTHOR, bookToEdit.getAuthor());
                    intent.putExtra(AddEditBookActivity.EXTRA_GENRE, bookToEdit.getGenre());
                    intent.putExtra(AddEditBookActivity.EXTRA_YEAR, bookToEdit.getPublicationYear());
                    intent.putExtra(AddEditBookActivity.EXTRA_READ, bookToEdit.isRead());
                    ((Activity) view.getContext()).startActivityForResult(intent, RequestCodes.EDIT_BOOK_REQUEST);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView bookTitleTextView, bookAuthorTextView, bookGenreTextView, bookStatusTextView;

        public BookViewHolder(View itemView) {
            super(itemView);
            bookTitleTextView = itemView.findViewById(R.id.bookTitleTextView);
            bookAuthorTextView = itemView.findViewById(R.id.bookAuthorTextView);
            bookGenreTextView = itemView.findViewById(R.id.bookGenreTextView);
            bookStatusTextView = itemView.findViewById(R.id.bookStatusTextView);
        }
    }
}
