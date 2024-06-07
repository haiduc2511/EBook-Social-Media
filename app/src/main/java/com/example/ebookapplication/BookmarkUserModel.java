package com.example.ebookapplication;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class BookmarkUserModel implements Parcelable{
    String bookmarkId;
    String userId;
    boolean liked;
    boolean saved;


    public BookmarkUserModel() {
    }

    protected BookmarkUserModel(Parcel in) {
        bookmarkId = in.readString();
        userId = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            liked = in.readBoolean();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saved = in.readBoolean();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookmarkId);
        dest.writeString(userId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(liked);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(saved);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<BookmarkModel> CREATOR = new Parcelable.Creator<BookmarkModel>() {
        @Override
        public BookmarkModel createFromParcel(Parcel in) {
            return new BookmarkModel(in);
        }

        @Override
        public BookmarkModel[] newArray(int size) {
            return new BookmarkModel[size];
        }
    };

    @Override
    public String toString() {
        return "BookmarkModel{" +
                "bookmarkId='" + bookmarkId + '\'' +
                ", userId='" + userId + '\'' +
                ", liked='" + liked + '\'' +
                ", saved='" + saved + '\'' +
                '}';
    }
}
