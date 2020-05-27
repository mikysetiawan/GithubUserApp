package com.lingkarinovasimuda.githubuserapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListRepo implements Parcelable {
    private String repo_name;

    public ListRepo(Parcel in) {
        repo_name = in.readString();
        url = in.readString();
    }

    public ListRepo() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(repo_name);
        parcel.writeString(url);
    }

    public static final Creator<ListRepo> CREATOR = new Creator<ListRepo>() {
        @Override
        public ListRepo createFromParcel(Parcel in) {
            return new ListRepo(in);
        }

        @Override
        public ListRepo[] newArray(int size) {
            return new ListRepo[size];
        }
    };

    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
}
