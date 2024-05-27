package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class BookRatingModel implements Parcelable {

    @PrimaryKey
    @NonNull
    public String rFirebaseId;
    public String bookId;
    public String userId;
    public String star;
    public String comment;
    public BookRatingModel() {
    }

    protected BookRatingModel(Parcel in) {
        rFirebaseId = in.readString();
        bookId = in.readString();
        userId = in.readString();
        star = in.readString();
        comment = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rFirebaseId);
        dest.writeString(bookId);
        dest.writeString(userId);
        dest.writeString(star);
        dest.writeString(comment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PageModel> CREATOR = new Creator<PageModel>() {
        @Override
        public PageModel createFromParcel(Parcel in) {
            return new PageModel(in);
        }

        @Override
        public PageModel[] newArray(int size) {
            return new PageModel[size];
        }
    };

    @Override
    public String toString() {
        return "BookRating{" +
                "rFirebaseId='" + rFirebaseId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", userId='" + userId + '\'' +
                ", star='" + star + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}