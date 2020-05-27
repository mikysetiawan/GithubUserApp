package com.lingkarinovasimuda.githubuserapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GithubUser implements Parcelable {
    private String username;
    private String name;
    private String avatar;
    private String company;
    private String location;
    private String repository;
    private String follower;
    private String following;
    private List<ListRepo> list_repo;

    public GithubUser() {

    }

    protected GithubUser(Parcel in) {
        username = in.readString();
        name = in.readString();
        avatar = in.readString();
        company = in.readString();
        location = in.readString();
        repository = in.readString();
        follower = in.readString();
        following = in.readString();
        list_repo = in.createTypedArrayList(ListRepo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(name);
        parcel.writeString(avatar);
        parcel.writeString(company);
        parcel.writeString(location);
        parcel.writeString(repository);
        parcel.writeString(follower);
        parcel.writeString(following);
        parcel.writeTypedList(list_repo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GithubUser> CREATOR = new Creator<GithubUser>() {
        @Override
        public GithubUser createFromParcel(Parcel in) {
            return new GithubUser(in);
        }

        @Override
        public GithubUser[] newArray(int size) {
            return new GithubUser[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public List<ListRepo> getList_repo() {
        return list_repo;
    }

    public void setList_repo(List<ListRepo> list_repo) {
        this.list_repo = list_repo;
    }
}
