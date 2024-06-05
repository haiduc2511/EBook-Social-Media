package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class UserModel implements Parcelable {
    public String uId;

    public String userName;
    public String userEmail;

    public int userAge;
    public String userGender;
    public int userFollowers;
    public UserModel() {
    }


    protected UserModel(Parcel in) {
        uId = in.readString();
        userName = in.readString();
        userEmail = in.readString();
        userAge = in.readInt();
        userGender = in.readString();
        userFollowers = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uId);
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

    @Override
    public String toString() {
        return "UserCategoryModel{" +
                "uId='" + uId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAge='" + userAge + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userFollowers='" + userFollowers + '\'' +
                '}';
    }
}