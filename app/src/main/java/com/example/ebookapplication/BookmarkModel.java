package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class BookmarkModel implements Parcelable {

    @NonNull
    public String bmFirebaseId;
    public String userId;
    public String bookId;
    public String bookmarkName;
    public int likes;
    public int saves;

    public BookmarkModel() {
    }

    protected BookmarkModel(Parcel in) {
        bmFirebaseId = in.readString();
        userId = in.readString();
        bookId = in.readString();
        bookmarkName = in.readString();
        likes = in.readInt();
        saves = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bmFirebaseId);
        dest.writeString(userId);
        dest.writeString(bookId);
        dest.writeString(bookmarkName);
        dest.writeInt(likes);
        dest.writeInt(saves);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookmarkModel> CREATOR = new Creator<BookmarkModel>() {
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
                "bmFirebaseId='" + bmFirebaseId + '\'' +
                ", userId='" + userId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookmarkName='" + bookmarkName + '\'' +
                ", likes='" + likes + '\'' +
                ", saves='" + saves + '\'' +
                '}';
    }
}