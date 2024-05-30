package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class BookCategoryModel implements Parcelable {

    @NonNull
    public String bcFirebaseId;
    public String bookId;
    public String categoryId;

    public String categoryName;
    public BookCategoryModel() {
    }

    protected BookCategoryModel(Parcel in) {
        bcFirebaseId = in.readString();
        bookId = in.readString();
        categoryId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bcFirebaseId);
        dest.writeString(bookId);
        dest.writeString(categoryId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookCategoryModel> CREATOR = new Creator<BookCategoryModel>() {
        @Override
        public BookCategoryModel createFromParcel(Parcel in) {
            return new BookCategoryModel(in);
        }

        @Override
        public BookCategoryModel[] newArray(int size) {
            return new BookCategoryModel[size];
        }
    };

    @Override
    public String toString() {
        return "BookCategoryModel{" +
                "bcFirebaseId='" + bcFirebaseId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}