package com.example.mybooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mybooks.adapters.BooksListAdapter;
import com.example.mybooks.db.Books;
import com.example.mybooks.db.DatabaseHelper;

public class AddBookScreen extends AppCompatActivity {
    //view declaration
    private BooksListAdapter booksListAdapter;

    private EditText title_txt,author_txt,pages_txt;
    private Spinner genres;
    private CheckBox isRead_chckbox;
    private Button addBook_button;

    private String chosedGenre;
    private boolean isChosedChkbox;


    private String[] spinnerData={"Fantazija","Triler","Drama","Krimi","Hentai :)"};
    private String selectedSpine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_screen);
        //changes color of the action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6b8583")));

        //getting view by id
        title_txt=(EditText)findViewById(R.id.title_text);
        author_txt=(EditText)findViewById(R.id.author_text);
        pages_txt=(EditText)findViewById(R.id.pages_text);
        genres=(Spinner)findViewById(R.id.genres_spinner);
        isRead_chckbox=(CheckBox) findViewById(R.id.checkBox);
        addBook_button=(Button) findViewById(R.id.add_book_btn);




        //inserting in spinner data for book genres and using simple spiner dropdown layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddBookScreen.this,android.R.layout.simple_spinner_item,spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genres.setAdapter(adapter);

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
            switchToAllBoksScreen();
            return false;
        }
        if(id==R.id.add_menu_button){
            switchToAddBookScreen();
            return false;
        }

        return super.onOptionsItemSelected(i);
    }
    //navigation screens
    private void switchToAddBookScreen() {
        Intent intent=new Intent(this, AddBookScreen.class);
        startActivity(intent);
    }
    private void switchToAllBoksScreen() {
        Intent intent=new Intent(this, MainScreen.class);
        startActivity(intent);
    }
    //function for button to add book in db
    //Firstly it takes input data then it stores it by using create
    // function for saving new books
    //checks if title is used if not error is displayed
    public void addBook(View v){
        String tempTitle=title_txt.getText().toString();
        String tempAuthor=author_txt.getText().toString();
        String tempGenre=genres.getSelectedItem().toString();;
        int tempPages=Integer.parseInt(pages_txt.getText().toString());
        boolean tempRead=isRead_chckbox.isChecked();


        if (TextUtils.isEmpty(tempTitle.trim())){
            title_txt.setError("Unesite naslov knjige.");
            return;
        }

        try{
             saveNewBook(
                     tempTitle,
                     tempAuthor,
                     tempGenre,
                     tempPages,
                     tempRead

             );
            Toast.makeText(AddBookScreen.this, "Uspjesno dodata knjiga", Toast.LENGTH_SHORT).show();




        }catch (Exception e){
            Toast.makeText(AddBookScreen.this, "Greska s knjigom", Toast.LENGTH_SHORT).show();

            //Log.e(e);
        }

    }
    //fucntion for saving new book in room db
    private void saveNewBook(String title,String author,String genre,int pages,boolean isRead){
        DatabaseHelper db= DatabaseHelper.getDbInstance(this.getApplicationContext());
        Books book=new Books(author,title,genre,pages,isRead);
        db.booksDao().insertBook(book);
        finish();
    }


}