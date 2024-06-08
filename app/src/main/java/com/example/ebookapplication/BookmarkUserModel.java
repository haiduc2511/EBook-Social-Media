package com.example.ebookapplication;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BookmarkUserModel implements Parcelable{
    @NonNull
    public String buFirebaseId;
    public String bookmarkId;
    public String userId;
    public boolean liked;
    public boolean saved;



    public BookmarkUserModel() {
    }

    protected BookmarkUserModel(Parcel in) {
        buFirebaseId = in.readString();
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
        dest.writeString(buFirebaseId);
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

    public static final Parcelable.Creator<BookmarkUserModel> CREATOR = new Parcelable.Creator<BookmarkUserModel>() {
        @Override
        public BookmarkUserModel createFromParcel(Parcel in) {
            return new BookmarkUserModel(in);
        }

        @Override
        public BookmarkUserModel[] newArray(int size) {
            return new BookmarkUserModel[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "BookmarkModel{" +
                "buFirebaseId='" + buFirebaseId + '\'' +
                "bookmarkId='" + bookmarkId + '\'' +
                ", userId='" + userId + '\'' +
                ", liked='" + liked + '\'' +
                ", saved='" + saved + '\'' +
                '}';
    }
}
