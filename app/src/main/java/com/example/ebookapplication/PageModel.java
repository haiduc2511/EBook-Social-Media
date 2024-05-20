package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "page")
public class PageModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int pId;

    public String bookId;
    public String content;
    public PageModel() {
    }

    protected PageModel(Parcel in) {
        pId = in.readInt();
        bookId = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pId);
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
}
