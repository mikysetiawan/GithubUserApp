package com.lingkarinovasimuda.githubuserapp.services;

import com.lingkarinovasimuda.githubuserapp.model.DetailUser;
import com.lingkarinovasimuda.githubuserapp.model.ResponseGithubSearchUser;
import com.lingkarinovasimuda.githubuserapp.model.UserItem;
import com.lingkarinovasimuda.githubuserapp.model.UserRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.TOKEN_GITHUB;

public interface GetServices {

    @GET("users")
    @Headers("Authorization: token " + TOKEN_GITHUB)
    Call<List<UserItem>> getListUser();

    @GET("search/users")
    @Headers("Authorization: token " + TOKEN_GITHUB)
    Call<ResponseGithubSearchUser> getSearchUser(
            @Query("q") String username
    );

    @GET("users/{username}")
    @Headers("Authorization: token " + TOKEN_GITHUB)
    Call<DetailUser> getDetailUser(
            @Path("username") String username
    );

    @GET("users/{username}/repos")
    @Headers("Authorization: token " + TOKEN_GITHUB)
    Call<List<UserRepo>> getUserRepo(
            @Path("username") String username
    );

    @GET("users/{username}/following")
    @Headers("Authorization: token " + TOKEN_GITHUB)
    Call<List<UserItem>> getUserFollowing(
            @Path("username") String username
    );

    @GET("users/{username}/followers")
    @Headers("Authorization: token " + TOKEN_GITHUB)
    Call<List<UserItem>> getUserFollowers(
            @Path("username") String username
    );

}
