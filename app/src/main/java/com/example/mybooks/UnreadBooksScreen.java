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
import android.view.View;

import com.example.mybooks.adapters.BooksListAdapter;
import com.example.mybooks.adapters.RecyclerTouchListener;
import com.example.mybooks.db.Books;
import com.example.mybooks.db.DatabaseHelper;

import java.util.List;

public class UnreadBooksScreen extends AppCompatActivity {
    BooksListAdapter booksListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unread_books_screen);
        //changes color of the action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6b8583")));




        initRecyclerView();
        loadBooksList();
        returnSelectedBook();

    }
    //toolbar navigation bar :)
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem i){
        int id=i.getItemId();
        if(id==R.id.allBooks_menu_button){

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

    }//switching to new activity screen for details about the specific book
    private void switchToDetailsScren(long value){
        Intent intent=new Intent(this, BookDetailsScreen.class);
        intent.putExtra("id",value);
        startActivity(intent);
    }
    //function that has a listener implemented on recyclerview
    //to listen for which position did user click on
    private Books returnSelectedBook(){


        RecyclerView mRecyclerView = findViewById(R.id.recyclerView2);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getApplicationContext(), mRecyclerView,
                        new RecyclerTouchListener.OnTouchActionListener() {
                            @Override
                            public void onClick(View view, int position) {
                                long j=booksListAdapter.getId(position);

                                switchToDetailsScren(j);
                            }
                            @Override
                            public void onRightSwipe(View view, int position) {
                            }
                            @Override
                            public void onLeftSwipe(View view, int position) {
                            }
                        }));

        return null;
    }
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        booksListAdapter = new BooksListAdapter(this);
        recyclerView.setAdapter(booksListAdapter);



    }
    //function for calling from room db to load data from unread books
    // and using the adapter to fill the data
    private void loadBooksList() {
        DatabaseHelper db = DatabaseHelper.getDbInstance(this.getApplicationContext());
        List<Books> booksList =db.booksDao().getAllunreadBooks();
        booksListAdapter.setBooksList(booksList);


    }
}