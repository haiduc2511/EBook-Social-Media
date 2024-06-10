package com.example.ebookapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebookapplication.Activities.NoteActivity;
import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.NoteModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.databinding.ItemBookmarkBinding;
import com.example.ebookapplication.databinding.ItemNoteBinding;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private Context context;
    private List<NoteModel> noteModelList;

    public NoteAdapter(Context context) {
        this.context = context;

    }

    public Context getContext() {
        return context;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_note, parent, false);
        return new NoteViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (!noteModelList.isEmpty()) {
            NoteModel noteModel = noteModelList.get(position);
            holder.binding.setNoteModel(noteModel);
        }
    }

    @Override
    public int getItemCount() {
        if (noteModelList == null) {
            return 0;
        } else {
            return noteModelList.size();
        }
    }

    public void setNotes(List<NoteModel> noteModels) {
        noteModelList = noteModels;
        notifyDataSetChanged();
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        ItemNoteBinding binding;


        public NoteViewHolder(@NonNull ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}