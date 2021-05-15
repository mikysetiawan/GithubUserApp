package com.lingkarinovasimuda.githubuserapp.utils;

import android.database.Cursor;

import com.lingkarinovasimuda.githubuserapp.db.FavoriteUserContract;
import com.lingkarinovasimuda.githubuserapp.model.DetailUser;
import com.lingkarinovasimuda.githubuserapp.model.UserItem;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<UserItem> mapCursorToArrayList(Cursor userCursor) {
        ArrayList<UserItem> userList = new ArrayList<>();
        while (userCursor.moveToNext()) {
            int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn._ID));
            String login = userCursor.getString(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.LOGIN));
            String avatarUrl = userCursor.getString(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.AVATAR_URL));

            userList.add(new UserItem(id, login, avatarUrl));
        }
        return userList;
    }

    public static DetailUser mapCursorToDetailUser(Cursor userCursor) {
        DetailUser detailUser = new DetailUser();

        while (userCursor.moveToNext()) {
            int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn._ID));
            String login = userCursor.getString(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.LOGIN));
            String name = userCursor.getString(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.NAME));
            String avatarUrl = userCursor.getString(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.AVATAR_URL));
            int publicRepos = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.PUBLIC_REPOS));
            int publicGists = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.PUBLIC_GISTS));
            int following = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.FOLLOWING));
            int follower = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.FOLLOWERS));

            detailUser = new DetailUser(id, login, name, avatarUrl, publicRepos, publicGists, following, follower);
        }

        return detailUser;
    }

    public static DetailUser mapCursorToObject(Cursor userCursor) {
        userCursor.moveToFirst();

        int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn._ID));
        String login = userCursor.getString(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.LOGIN));
        String name = userCursor.getString(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.NAME));
        String avatarUrl = userCursor.getString(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.AVATAR_URL));
        int publicRepos = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.PUBLIC_REPOS));
        int publicGists = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.PUBLIC_GISTS));
        int following = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.FOLLOWING));
        int follower = userCursor.getInt(userCursor.getColumnIndexOrThrow(FavoriteUserContract.FavoriteUserColumn.FOLLOWERS));

        return new DetailUser(id, login, name, avatarUrl, publicRepos, publicGists, following, follower);
    }
}
