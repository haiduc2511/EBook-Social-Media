package com.example.ebookapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebookapplication.Activities.BookDetailActivity;
import com.example.ebookapplication.Activities.BookListActivity;
import com.example.ebookapplication.Activities.BookRecommendListActivity;
import com.example.ebookapplication.Activities.ReadingActivity;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.Utils.SharedPrefManager;
import com.example.ebookapplication.databinding.ItemBookBinding;
import com.example.ebookapplication.databinding.ItemBookmarkBinding;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>{

    private Context context;
    private List<BookmarkModel> bookmarkModelList;

    public BookmarkAdapter(Context context) {
        this.context = context;

    }

    public Context getContext() {
        return context;
    }


    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookmarkBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_bookmark, parent, false);
        return new BookmarkViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        if (!bookmarkModelList.isEmpty()) {
            BookmarkModel bookmarkModel = bookmarkModelList.get(position);
            holder.binding.setBookmarkModel(bookmarkModel);
            holder.binding.cvMain.setOnClickListener(v -> {
                Toast.makeText(context, "You are clicking on bookmark " + bookmarkModel.bookmarkName, Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public int getItemCount() {
        if (bookmarkModelList == null) {
            return 0;
        } else {
            return bookmarkModelList.size();
        }
    }

    public void setBookmarks(List<BookmarkModel> bookmarks) {
        bookmarkModelList = bookmarks;
        notifyDataSetChanged();
    }


    public class BookmarkViewHolder extends RecyclerView.ViewHolder {
        ItemBookmarkBinding binding;


        public BookmarkViewHolder(@NonNull ItemBookmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}