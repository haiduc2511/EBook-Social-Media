package com.example.ebookapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(BookModel bookModel);

    @Update
    void updateBook(BookModel bookModel);

    @Delete
    void deleteBook(BookModel bookModel);

    @Query("SELECT * FROM book")
    LiveData<List<BookModel>> getAllBooksLive();

    @Query("SELECT * FROM book")
    List<BookModel> getAllBooksFuture();
    @Query("SELECT * FROM book")
    List<BookModel> getAllBooks();

    @Query("SELECT * FROM book WHERE bId=:id")
    BookModel getBook(int id);

}
