package com.example.ebookapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class UserCategoryModel implements Parcelable {

    @NonNull
    public String ucFirebaseId;
    public String userId;
    public String categoryId;
    public int userCategoryPoints;

    public String categoryName;
    public UserCategoryModel() {
    }

    protected UserCategoryModel(Parcel in) {
        ucFirebaseId = in.readString();
        userId = in.readString();
        categoryId = in.readString();
        userCategoryPoints = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ucFirebaseId);
        dest.writeString(userId);
        dest.writeString(categoryId);
        dest.writeInt(userCategoryPoints);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserCategoryModel> CREATOR = new Creator<UserCategoryModel>() {
        @Override
        public UserCategoryModel createFromParcel(Parcel in) {
            return new UserCategoryModel(in);
        }

        @Override
        public UserCategoryModel[] newArray(int size) {
            return new UserCategoryModel[size];
        }
    };

    @Override
    public String toString() {
        return "UserCategoryModel{" +
                "ucFirebaseId='" + ucFirebaseId + '\'' +
                ", userId='" + userId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", userCategoryPoints='" + userCategoryPoints + '\'' +
                '}';
    }
}