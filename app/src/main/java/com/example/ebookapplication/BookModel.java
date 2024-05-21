package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book")
public class BookModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int bId;

    @ColumnInfo(name = "b_title")
    public String bookTitle;
    public String authorName;
    public int numberOfPages;
    public String bookCategory;
    public String bookSummary;
    public BookModel() {
    }

    protected BookModel(Parcel in) {
        bId = in.readInt();
        bookTitle = in.readString();
        authorName = in.readString();
        numberOfPages = in.readInt();
        bookCategory = in.readString();
        bookSummary = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bId);
        dest.writeString(bookTitle);
        dest.writeString(authorName);
        dest.writeInt(numberOfPages);
        dest.writeString(bookCategory);
        dest.writeString(bookSummary);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookModel> CREATOR = new Creator<BookModel>() {
        @Override
        public BookModel createFromParcel(Parcel in) {
            return new BookModel(in);
        }

        @Override
        public BookModel[] newArray(int size) {
            return new BookModel[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        String information = "Title" + bookTitle + "\n" +
                "AuthorName" + authorName + "\n" +
                "numberOfPages" + numberOfPages + "\n" +
                "bookCategory" + bookCategory + "\n" +
                "bookSummary" + bookSummary ;
        return information;
    }
}
