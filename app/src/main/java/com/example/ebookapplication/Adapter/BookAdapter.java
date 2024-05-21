package com.example.ebookapplication.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebookapplication.BookDetailActivity;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.databinding.ItemBookBinding;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private Context context;

    private List<BookModel> bookModelList;

    public BookAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_book, parent, false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if (!bookModelList.isEmpty()) {
            BookModel bookModel = bookModelList.get(position);
            holder.binding.setBookModel(bookModel);
            holder.binding.cvMain.setOnClickListener(v -> {
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("book", bookModel);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (bookModelList == null) {
            return 0;
        } else {
            return bookModelList.size();
        }
    }

    public void setBooks(List<BookModel> books) {
        bookModelList = books;
        notifyDataSetChanged();
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {
        ItemBookBinding binding;


        public BookViewHolder(@NonNull ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
