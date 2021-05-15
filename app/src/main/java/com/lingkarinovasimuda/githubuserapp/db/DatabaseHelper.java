package com.lingkarinovasimuda.githubuserapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "github_user_app";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INT NOT NULL," +
                    " %s INT NOT NULL," +
                    " %s INT NOT NULL," +
                    " %s INT NOT NULL)",
            FavoriteUserContract.TABLE_NAME,
            FavoriteUserContract.FavoriteUserColumn._ID,
            FavoriteUserContract.FavoriteUserColumn.LOGIN,
            FavoriteUserContract.FavoriteUserColumn.NAME,
            FavoriteUserContract.FavoriteUserColumn.AVATAR_URL,
            FavoriteUserContract.FavoriteUserColumn.PUBLIC_REPOS,
            FavoriteUserContract.FavoriteUserColumn.PUBLIC_GISTS,
            FavoriteUserContract.FavoriteUserColumn.FOLLOWERS,
            FavoriteUserContract.FavoriteUserColumn.FOLLOWING
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteUserContract.TABLE_NAME);
        onCreate(db);
    }
}
