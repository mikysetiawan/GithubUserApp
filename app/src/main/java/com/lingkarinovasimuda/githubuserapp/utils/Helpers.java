package com.lingkarinovasimuda.githubuserapp.utils;

import android.content.Context;

public class Helpers {
    public static final String BASE_URL = "https://api.github.com/";
    public static final String TOKEN_GITHUB = "9f684532ecb7d62b5430744f97b7d907f6292c26";
    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_POSITION = "extra_position";

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, null, context.getPackageName());
    }
}
