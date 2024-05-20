package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uId;

    public String userName;
    public String userEmail;

    public int userAge;
    public String userGender;
    public int userFollowers;
    public UserModel() {
    }

    protected UserModel(Parcel in) {
        uId = in.readInt();
        userName = in.readString();
        userEmail = in.readString();
        userAge = in.readInt();
        userGender = in.readString();
        userFollowers = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uId);
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeInt(userAge);
        dest.writeString(userGender);
        dest.writeInt(userFollowers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}