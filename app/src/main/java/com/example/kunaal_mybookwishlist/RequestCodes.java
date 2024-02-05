package com.example.kunaal_mybookwishlist;

public interface RequestCodes {
    /**
         Defines request codes used for startActivityForResult calls between activities.
         Helps in distinguishing the results for different actions like adding or editing a book.

         - ADD_BOOK_REQUEST: Used when starting AddEditBookActivity for adding a new book.
         - EDIT_BOOK_REQUEST: Used when starting AddEditBookActivity for editing an existing book.
     **/


    int ADD_BOOK_REQUEST = 1;
    int EDIT_BOOK_REQUEST = 2;
}
