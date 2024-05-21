package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "page")
public class PageModel implements Parcelable {

    @PrimaryKey
    @NonNull
    public String pFirebaseId;
    public String bookId;
    public String content;
    public PageModel() {
    }

    protected PageModel(Parcel in) {
        pFirebaseId = in.readString();
        bookId = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pFirebaseId);
        dest.writeString(bookId);
        dest.writeString(content);
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
        return "PageModel{" +
                "pFirebaseId='" + pFirebaseId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
