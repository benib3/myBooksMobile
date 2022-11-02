package com.example.mybooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mybooks.adapters.BooksListAdapter;
import com.example.mybooks.db.Books;
import com.example.mybooks.db.DatabaseHelper;

import java.util.List;

public class BookGenresScreen extends AppCompatActivity {
    private BooksListAdapter booksListAdapter;
    private TextView mGenre;
    private String valueGenre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_genres_screen);
        //changes color of the action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6b8583")));

        //reciving intent data from detaisScreen
          Intent intent=getIntent();
          valueGenre=intent.getStringExtra("idBookGenre");

        initRecyclerView();

        loadGenresBooksList(valueGenre);
        mGenre=findViewById(R.id.idZanr);
        mGenre.setText("Zanr: "+valueGenre);

    }
    //toolbar navigation bar :)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem i){
        int id=i.getItemId();
        if(id==R.id.allBooks_menu_button){
            loadBooksList();
            switchToAllBoksScreen();
            return false;
        }
        if(id==R.id.unreadBook_menu_button){
            switchToUnreadBooks();
            return false;
        }
        if(id==R.id.add_menu_button){
            switchToAddBookScreen();
            return false;
        }

        return super.onOptionsItemSelected(i);
    }
    private void switchToAddBookScreen() {
        Intent intent=new Intent(this, AddBookScreen.class);
        startActivity(intent);
    }
    private void switchToAllBoksScreen() {
        Intent intent=new Intent(this, MainScreen.class);
        startActivity(intent);
    }
    private void switchToUnreadBooks(){
        Intent intent=new Intent(this, UnreadBooksScreen.class);
        startActivity(intent);

    }
    private void loadBooksList() {
        DatabaseHelper db = DatabaseHelper.getDbInstance(this.getApplicationContext());
        List<Books> booksList =db.booksDao().getAllBooks();


        booksListAdapter.setBooksList(booksList);
    }
    //loads books by specific genre which is passed from Book details screen
    private void loadGenresBooksList(String valueGenre){
        DatabaseHelper db = DatabaseHelper.getDbInstance(this.getApplicationContext());
        List<Books> booksList =db.booksDao().getAllGenres(valueGenre);
        booksListAdapter.setBooksList(booksList);
    }
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        booksListAdapter = new BooksListAdapter(this);
        recyclerView.setAdapter(booksListAdapter);



    }
}