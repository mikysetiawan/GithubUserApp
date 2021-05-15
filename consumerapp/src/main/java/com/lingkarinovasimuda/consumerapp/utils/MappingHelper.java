package com.lingkarinovasimuda.consumerapp.utils;

import android.database.Cursor;

import com.lingkarinovasimuda.consumerapp.db.DatabaseContract;
import com.lingkarinovasimuda.consumerapp.model.DetailUser;
import com.lingkarinovasimuda.consumerapp.model.UserItem;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<UserItem> mapCursorToArrayList(Cursor userCursor) {
        ArrayList<UserItem> userList = new ArrayList<>();
        while (userCursor.moveToNext()) {
            int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn._ID));
            String login = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.LOGIN));
            String avatarUrl = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.AVATAR_URL));

            userList.add(new UserItem(id, login, avatarUrl));
        }
        return userList;
    }

    public static DetailUser mapCursorToDetailUser(Cursor userCursor) {
        DetailUser detailUser = new DetailUser();

        while (userCursor.moveToNext()) {
            int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn._ID));
            String login = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.LOGIN));
            String name = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.NAME));
            String avatarUrl = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.AVATAR_URL));
            int publicRepos = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.PUBLIC_REPOS));
            int publicGists = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.PUBLIC_GISTS));
            int following = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.FOLLOWING));
            int follower = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.FOLLOWERS));

            detailUser = new DetailUser(id, login, name, avatarUrl, publicRepos, publicGists, following, follower);
        }

        return detailUser;
    }

    public static DetailUser mapCursorToObject(Cursor userCursor) {
        userCursor.moveToFirst();

        int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn._ID));
        String login = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.LOGIN));
        String name = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.NAME));
        String avatarUrl = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.AVATAR_URL));
        int publicRepos = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.PUBLIC_REPOS));
        int publicGists = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.PUBLIC_GISTS));
        int following = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.FOLLOWING));
        int follower = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.FOLLOWERS));

        return new DetailUser(id, login, name, avatarUrl, publicRepos, publicGists, following, follower);
    }
}
