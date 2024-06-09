package com.example.ebookapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebookapplication.Activities.OtherUserActivity;
import com.example.ebookapplication.Activities.UserActivity;
import com.example.ebookapplication.BookRatingModel;
import com.example.ebookapplication.PageModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.example.ebookapplication.databinding.ItemBookRatingBinding;
import com.example.ebookapplication.databinding.ItemPageBinding;

import java.util.List;

public class BookRatingAdapter extends RecyclerView.Adapter<BookRatingAdapter.BookRatingViewHolder> {
    List<BookRatingModel> bookRatingModelList;
    Context context;

    public BookRatingAdapter(Context context) {
        this.context = context;
    }

    public void setBookRatingModelList(List<BookRatingModel> bookRatingModelList) {
        this.bookRatingModelList = bookRatingModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookRatingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_book_rating, parent, false);
        return new BookRatingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookRatingViewHolder holder, int position) {
        if (!bookRatingModelList.isEmpty()) {
            BookRatingModel bookRatingModel = bookRatingModelList.get(position);
            holder.binding.setBookRatingModel(bookRatingModel);
            holder.binding.ivUserAvatar.setOnClickListener(v -> {
                if (bookRatingModel.userId.equals(FirebaseHelper.getInstance().getAuth().getCurrentUser().getUid())) {
                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("user", bookRatingModel.userId);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, OtherUserActivity.class);
                    intent.putExtra("user", bookRatingModel.userId);
                    context.startActivity(intent);
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        if (bookRatingModelList == null) {
            return 0;
        } else {
            return bookRatingModelList.size();
        }
    }

    public static class BookRatingViewHolder extends RecyclerView.ViewHolder {
        ItemBookRatingBinding binding;
        public BookRatingViewHolder (@NonNull ItemBookRatingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
