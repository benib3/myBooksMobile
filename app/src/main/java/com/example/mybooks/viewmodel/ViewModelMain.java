package com.example.mybooks.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mybooks.db.Books;
import com.example.mybooks.db.DatabaseHelper;

import java.util.List;

public class ViewModelMain extends AndroidViewModel {
    private MutableLiveData<List<Books>> listOfBooks;
    private DatabaseHelper appDatabase;

    public ViewModelMain(Application application) {
        super(application);
        listOfBooks = new MutableLiveData<>();

        appDatabase = DatabaseHelper.getDbInstance(getApplication().getApplicationContext());
    }



    public MutableLiveData<List<Books>>  getListOfBooksObserver() {
        return listOfBooks;
    }

    public void getAllBooksAsList() {
        List<Books> categoryList=  appDatabase.booksDao().getAllBooks();
        if(categoryList.size() > 0)
        {
            listOfBooks.postValue(categoryList);
        }else {
            listOfBooks.postValue(null);
        }
    }

    public void insertBook(int id) {
        Books books = new Books();
        books.setId(id);
        appDatabase.booksDao().insertBook(books);
        getAllBooksAsList();
    }

 


    public void updateBook(Books book) {
        appDatabase.booksDao().update(book);
        getAllBooksAsList();
    }

    public void deleteBook(Books book) {
        appDatabase.booksDao().delete(book);
        getAllBooksAsList();
    }
}
