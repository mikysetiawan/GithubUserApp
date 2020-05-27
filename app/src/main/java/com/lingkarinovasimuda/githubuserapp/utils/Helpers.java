package com.lingkarinovasimuda.githubuserapp.utils;

import android.content.Context;

public class Helpers {
    public static final String EXTRA_USER = "extra_user";

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, null, context.getPackageName());
    }
}
