package com.example.mybooks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybooks.adapters.BooksListAdapter;
import com.example.mybooks.db.Books;
import com.example.mybooks.db.DatabaseHelper;

import java.util.List;

public class BookDetailsScreen extends AppCompatActivity {
    private BooksListAdapter booksListAdapter;
    private Books selectedBook;
    //private String selectedBookGenre;
    private TextView mTitle,mAuthor,mGenres,mPages,mRead;
    long val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details_screen);
        //changes color of the action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6b8583")));


        //intent for receiveg data from prior activity Main Screen
        Intent intent = getIntent();
        val= intent.getLongExtra("id",0);





        //loads books data with the id
        loadBookByID((int)val);

        mTitle=findViewById(R.id.title_details);
        mAuthor=findViewById(R.id.author_details);
        mGenres=findViewById(R.id.genres_details);
        mPages=findViewById(R.id.pages_details);
        mRead=findViewById(R.id.read_details);


        showDataOnScreen(mTitle,mAuthor,mGenres,mPages,mRead);



        switchToGenresScreen(mGenres.getText().toString());



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
            switchToAllBooksScreen();
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
    private void switchToAllBooksScreen() {
        Intent intent=new Intent(this, MainScreen.class);
        startActivity(intent);
    }
    private void switchToUnreadBooks(){
        Intent intent=new Intent(this, UnreadBooksScreen.class);
        startActivity(intent);

    }
    //switching to genres screen and sending intent data
    private void switchToGenresScreen(String val){
        TextView mGenres = findViewById(R.id.genres_details);


        //intent for sending id to bookGenresScreen
        Intent intent2=new Intent(this, BookGenresScreen.class);
        intent2.putExtra("idBookGenre",val);
        mGenres.setOnClickListener(v -> startActivity(intent2));


    }


    private void loadBooksList() {
        DatabaseHelper db = DatabaseHelper.getDbInstance(this.getApplicationContext());
        List<Books> booksList =db.booksDao().getAllBooks();

        booksListAdapter.setBooksList(booksList);
    }
    private void loadBookByID(int id){
        DatabaseHelper db = DatabaseHelper.getDbInstance(this.getApplicationContext());
        List<Books> booksList =db.booksDao().getBookById(id);

        selectedBook=booksList.get(0);
    }
    //function for deleting book by specific id and notifying books adapter about change
    private void deleteBookById(int id){
        DatabaseHelper db = DatabaseHelper.getDbInstance(this.getApplicationContext());
        db.booksDao().deleteBookById(id);
        booksListAdapter.notifyDataSetChanged();
    }
    //function for displaying data on screen
    private void showDataOnScreen(TextView mTitle,TextView mAuthor,TextView mGenres, TextView mPages,TextView mRead){
        mTitle.setText(selectedBook.getTitle());
        mAuthor.setText(selectedBook.getAuthor());
        mGenres.setText(selectedBook.getGenre());
        mPages.setText("Broj stranica: "+selectedBook.getPages());
        if(selectedBook.isRead()){
            mRead.setText("Procitana");
        }else{
            mRead.setText("Neprocitana");
        }



    }
    //function for button to switch to another activity with data also
    public void switchToEditScreen(View view){
        //intent for sending id to bookGenresScreen
        Intent intent2=new Intent(this, EditBookScreen.class);
        intent2.putExtra("idEdit",val);
        startActivity(intent2);

    }
    //function for button for deleting book by id with alert which asks yes/no question
    public void deleteBook(View view) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                  //Use the Builder class for convenient dialog construction
                  builder.setMessage("Da li ste sigurni da cete da izbrisete knjigu?")
                          .setPositiveButton("Da", (dialog, id) -> {
                              deleteBookById((int) val);
                              Toast.makeText(BookDetailsScreen.this, "Uspjesno obrisana knjiga", Toast.LENGTH_SHORT).show();
                          })
                          .setNegativeButton("Ne", (dialog, id) -> {
                              return;
                          });
                  //Created the AlertDialog object and show it
                  builder.create().show();




        }

    }







