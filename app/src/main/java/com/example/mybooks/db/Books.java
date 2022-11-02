package com.example.mybooks.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Books {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    private int id;
    @ColumnInfo(name = "book_author")
    private String author;
    @ColumnInfo(name = "book_title")
    private String title;
    @ColumnInfo(name = "book_genres")
    private String genre;
    @ColumnInfo(name = "book_pages")
    private int pages;
    @ColumnInfo(name = "book_isRead")
    private boolean isRead;
    @ColumnInfo(name = "book_isFav")
    private boolean isFav;

    @Ignore
    public Books(){

    }
    @Ignore
    public Books(int id, String author, String title, String genre, int pages, boolean isRead, boolean isFav) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.pages = pages;
        this.isRead = isRead;
        this.isFav = isFav;
    }

    public Books(String author, String title, String genre, int pages, boolean isRead) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.pages = pages;
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
}
