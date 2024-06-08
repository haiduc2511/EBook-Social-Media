package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class NoteModel implements Parcelable {

    @NonNull
    public String nFirebaseId;
    public String bookmarkId;
    public String pageId;
    public String noteContent;

    public NoteModel() {
    }

    protected NoteModel(Parcel in) {
        nFirebaseId = in.readString();
        bookmarkId = in.readString();
        pageId = in.readString();
        noteContent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nFirebaseId);
        dest.writeString(bookmarkId);
        dest.writeString(pageId);
        dest.writeString(noteContent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteModel> CREATOR = new Creator<NoteModel>() {
        @Override
        public NoteModel createFromParcel(Parcel in) {
            return new NoteModel(in);
        }

        @Override
        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };

    @Override
    public String toString() {
        return "BookmarkModel{" +
                "nFirebaseId='" + nFirebaseId + '\'' +
                ", bookmarkId='" + bookmarkId + '\'' +
                ", pageId='" + pageId + '\'' +
                ", noteContent='" + noteContent + '\'' +
                '}';
    }
}