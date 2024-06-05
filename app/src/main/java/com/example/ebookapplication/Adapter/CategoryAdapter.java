package com.example.ebookapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ebookapplication.CategoryModel;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CategoryModel> {

    private List<CategoryModel> categories;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context, int resource, List<CategoryModel> categories) {
        super(context, resource, categories);
        this.categories = categories;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_item, null);
        }

        CategoryModel category = categories.get(position);
        if (category != null) {
            TextView categoryTextView = view.findViewById(android.R.id.text1);
            if (categoryTextView != null) {
                categoryTextView.setText(category.categoryName);
            }
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        }

        CategoryModel category = categories.get(position);
        if (category != null) {
            TextView categoryTextView = view.findViewById(android.R.id.text1);
            if (categoryTextView != null) {
                categoryTextView.setText(category.categoryName);
            }
        }

        return view;
    }
}
