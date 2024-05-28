package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CategoryModel implements Parcelable {

    @NonNull
    public String cFirebaseId;
    public String categoryName;
    public CategoryModel() {
    }

    protected CategoryModel(Parcel in) {
        cFirebaseId = in.readString();
        categoryName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cFirebaseId);
        dest.writeString(categoryName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    @Override
    public String toString() {
        return "CategoryModel{" +
                "cFirebaseId='" + cFirebaseId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}