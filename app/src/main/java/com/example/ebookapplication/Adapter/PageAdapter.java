package com.example.ebookapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebookapplication.Activities.BookDetailActivity;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.PageModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.databinding.ItemPageBinding;

import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {
    List<PageModel> pageModelList;
    Context context;
    TextView tvPageNumber;

    public PageAdapter(Context context, TextView tvPageNumber) {
        this.context = context;
        this.tvPageNumber = tvPageNumber;
    }

    public void setPages(List<PageModel> pageModelList) {
        this.pageModelList = pageModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_page, parent, false);
        return new PageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        if (!pageModelList.isEmpty()) {
            PageModel pageModel = pageModelList.get(position);
            holder.binding.setPageModel(pageModel);
            tvPageNumber.setText(pageModel.pFirebaseId);
        }
    }

    @Override
    public int getItemCount() {
        if (pageModelList == null) {
            return 0;
        } else {
            return pageModelList.size();
        }
    }

    public static class PageViewHolder extends RecyclerView.ViewHolder {
        ItemPageBinding binding;
        public PageViewHolder (@NonNull ItemPageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
