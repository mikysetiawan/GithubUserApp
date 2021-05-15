package com.lingkarinovasimuda.githubuserapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.lingkarinovasimuda.githubuserapp.db.FavoriteUserHelper;

import static com.lingkarinovasimuda.githubuserapp.db.FavoriteUserContract.AUTHORITY;
import static com.lingkarinovasimuda.githubuserapp.db.FavoriteUserContract.CONTENT_URI;
import static com.lingkarinovasimuda.githubuserapp.db.FavoriteUserContract.TABLE_NAME;

public class FavoriteUserProvider extends ContentProvider {
    private static final int USER = 1;
    private static final int USER_ID = 2;
    private FavoriteUserHelper favoriteUserHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        // content://com.lingkarinovasimuda.githubuserapp/favorite_user
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER);
        // content://com.lingkarinovasimuda.githubuserapp/favorite_user/id
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", USER_ID);
    }

    public FavoriteUserProvider() {
    }

    @Override
    public boolean onCreate() {
        favoriteUserHelper = FavoriteUserHelper.getInstance(getContext());
        favoriteUserHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case USER:
                cursor = favoriteUserHelper.queryAll();
                break;
            case USER_ID:
                cursor = favoriteUserHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        if (sUriMatcher.match(uri) == USER) {
            added = favoriteUserHelper.insert(values);
        } else {
            added = 0;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updated;
        if (sUriMatcher.match(uri) == USER_ID) {
            updated = favoriteUserHelper.update(uri.getLastPathSegment(), values);
        } else {
            updated = 0;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return updated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        if (sUriMatcher.match(uri) == USER_ID) {
            deleted = favoriteUserHelper.deleteById(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return deleted;
    }
}