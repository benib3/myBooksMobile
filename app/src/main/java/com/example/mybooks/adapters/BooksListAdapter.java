package com.example.mybooks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybooks.R;
import com.example.mybooks.db.Books;

import java.util.List;

public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.MyViewHolder> {

    private final Context context;
    private List<Books> booksList;

    public BooksListAdapter(Context context) {


        this.context = context;
    }

    public void setBooksList(List<Books> booksList) {
        this.booksList = booksList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BooksListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksListAdapter.MyViewHolder holder, int position) {
        Books book=booksList.get(position);



        holder.mdata_title.setText(this.booksList.get(position).getTitle());
        holder.mdata_author.setText(this.booksList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return this.booksList.size();
    }
    public int getId(int position){
        return booksList.get(position).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mdata_title;
        TextView mdata_author;

        public MyViewHolder(View view) {
            super(view);
            mdata_title = view.findViewById(R.id.data_title);
            mdata_author = view.findViewById(R.id.data_author);

        }
    }
}
