package com.lingkarinovasimuda.githubuserapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteUserContract {
    public static final String AUTHORITY = "com.lingkarinovasimuda.githubuserapp";
    private static final String SCHEME = "content";
    public static String TABLE_NAME = "favorite_user";

    // untuk membuat URI content://com.lingkarinovasimuda.githubuserapp/favorite_user
    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static final class FavoriteUserColumn implements BaseColumns {
        public static String LOGIN = "login";
        public static String NAME = "name";
        public static String AVATAR_URL = "avatar_url";
        public static String PUBLIC_REPOS = "public_repos";
        public static String PUBLIC_GISTS = "public_gists";
        public static String FOLLOWERS = "followers";
        public static String FOLLOWING = "following";
    }
}
