package com.example.mybooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybooks.adapters.BooksListAdapter;
import com.example.mybooks.db.Books;
import com.example.mybooks.db.BooksDao;
import com.example.mybooks.db.DatabaseHelper;

import java.util.List;

public class EditBookScreen extends AppCompatActivity {
    private BooksListAdapter booksListAdapter;
    private String[] spinnerData={"Fantazija","Triler","Drama","Krimi","Hentai :)"};
    private String selectedSpine;
    private EditText title_txt,author_txt,pages_txt;
    private Spinner genres;
    private CheckBox isRead_chckbox;
    private Button editBook_button;
    private long val;
    private Books passedBook;
    private Books updatedBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_screen);
        //changes color of the action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6b8583")));


        //intent for receiving data from prior activity BookDetailsScreen
        Intent intent = getIntent();
        val = intent.getLongExtra("idEdit",0);

        Log.println(Log.ASSERT,"Error:",""+val);
        loadBookByID((int)val);


        //getting view by id
        title_txt=(EditText)findViewById(R.id.title_editext);
        author_txt=(EditText)findViewById(R.id.author_editext);
        pages_txt=(EditText)findViewById(R.id.pages_editext);
        genres=(Spinner)findViewById(R.id.genres_spinner_edit);
        isRead_chckbox=(CheckBox) findViewById(R.id.checkBox_edit);
        editBook_button=(Button) findViewById(R.id.update_book_edit);



        //inserting in spinner data for book genres and using simple spiner dropdown layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditBookScreen.this,android.R.layout.simple_spinner_item,spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genres.setAdapter(adapter);



        //populating fields
        populateEditText(title_txt,author_txt,genres,pages_txt,isRead_chckbox,adapter);


    }
    //function for button which updates selected data  from editText
    // and checks if title is used if not error is displayed
    // Also data is stored to database and bookListAdapter is notified about changes occurred
    public void onClickUpdate(View view){
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
            updateBook(
                    (int)val,
                    tempTitle,
                    tempAuthor,
                    tempGenre,
                    tempPages,
                    tempRead

            );
            Toast.makeText(EditBookScreen.this, "Uspjesno izmjenjena knjiga", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, MainScreen.class);
            startActivity(intent);
            booksListAdapter.notifyDataSetChanged();

        }catch (Exception e){
            Log.println(Log.ERROR,"Error",""+e);
        }
        Toast.makeText(EditBookScreen.this, "Neuspjesan pokusaj izmjene knjige", Toast.LENGTH_SHORT).show();


    }
    //loading book by id from room db and getting the book with matching id
    private void loadBookByID(int id){
        DatabaseHelper db = DatabaseHelper.getDbInstance(this.getApplicationContext());
        List<Books> booksList =db.booksDao().getBookById(id);


        passedBook=booksList.get(0);
    }
    //TODO create favourite
    //function for updating current book in db
    private void updateBook(int id,String title,String author,String genre,int pages,boolean isRead) {
        DatabaseHelper db = DatabaseHelper.getDbInstance(this.getApplicationContext());
        Books book = new Books(id,author, title, genre, pages, isRead,false);
        db.booksDao().update(book);
        finish();
    }
    //function for parsing data from details screen about book to the editBookScreen
    // text inputs for easier editing
    public void populateEditText(EditText mTitle, EditText mAuthor, Spinner mGenres, EditText mPages, CheckBox mRead , ArrayAdapter adapter){
        mTitle.setText(passedBook.getTitle());
        mAuthor.setText(passedBook.getAuthor());
        int spinnerPosition = adapter.getPosition(passedBook.getGenre());
        mGenres.setSelection(spinnerPosition);
        mPages.setText(String.valueOf(passedBook.getPages()));
        if(passedBook.isRead()){
            mRead.setChecked(true);
        }else{
            mRead.setChecked(false);
        }

    }


}