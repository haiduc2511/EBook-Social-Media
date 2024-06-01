package com.example.ebookapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebookapplication.Activities.BookDetailActivity;
import com.example.ebookapplication.Activities.BookListActivity;
import com.example.ebookapplication.Activities.BookRecommendListActivity;
import com.example.ebookapplication.Activities.ReadingActivity;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.databinding.ItemBookBinding;
import com.example.ebookapplication.databinding.ItemRecommendedBookHorizontalBinding;

import java.util.List;

public class BookAdapterHorizontal extends RecyclerView.Adapter<BookAdapterHorizontal.BookHorizontalViewHolder>{

    private Context context;
    private List<BookModel> bookModelList;

    public BookAdapterHorizontal(Context context) {
        this.context = context;

    }

    public Context getContext() {
        return context;
    }


    @NonNull
    @Override
    public BookHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecommendedBookHorizontalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_recommended_book_horizontal, parent, false);
        return new BookHorizontalViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull BookHorizontalViewHolder holder, int position) {
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


    public class BookHorizontalViewHolder extends RecyclerView.ViewHolder {
        ItemRecommendedBookHorizontalBinding binding;


        public BookHorizontalViewHolder(@NonNull ItemRecommendedBookHorizontalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
