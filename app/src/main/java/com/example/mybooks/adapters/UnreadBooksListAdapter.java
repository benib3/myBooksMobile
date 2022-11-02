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

public class UnreadBooksListAdapter extends RecyclerView.Adapter<UnreadBooksListAdapter.MyViewHolder> {
    private  Context context;
    private List<Books> booksList;
    public void setBooksList(List<Books> booksList) {
        this.booksList = booksList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);

        return new UnreadBooksListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnreadBooksListAdapter.MyViewHolder holder, int position) {
        holder.mdata_title.setText(this.booksList.get(position).getTitle());
        holder.mdata_author.setText(this.booksList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return this.booksList.size();
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
