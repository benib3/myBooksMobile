package com.example.mybooks.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BooksDao {
    @Query("SELECT * FROM books")
    List<Books> getAllBooks();
    @Query("SELECT * FROM books where book_isRead=0")
    List<Books> getAllunreadBooks();
    @Query("SELECT * FROM books where book_genres=:genre")
    List<Books> getAllGenres(String genre);
    @Query("SELECT * FROM books WHERE book_id =:bookId")
    List<Books> getBookById(int bookId);

    @Query("DELETE FROM books WHERE book_id =:bookId")
    void deleteBookById(int bookId);


    @Insert
    void insertBook(Books... book);

    @Delete
    void delete(Books book);

    @Update
    void update(Books book);

}
