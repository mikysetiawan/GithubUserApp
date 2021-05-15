package com.lingkarinovasimuda.githubuserapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lingkarinovasimuda.githubuserapp.R;
import com.lingkarinovasimuda.githubuserapp.adapter.GithubUserAdapter;
import com.lingkarinovasimuda.githubuserapp.db.FavoriteUserContract;
import com.lingkarinovasimuda.githubuserapp.db.FavoriteUserHelper;
import com.lingkarinovasimuda.githubuserapp.model.UserItem;
import com.lingkarinovasimuda.githubuserapp.utils.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.EXTRA_USER;

public class FavoriteActivity extends AppCompatActivity implements GithubUserAdapter.ClickListenerRecycler, LoadDetailUserCallback {
    private Context context;
    private GithubUserAdapter adapter;
    private TextView tvErrorList;
    private RecyclerView rlvListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        context = FavoriteActivity.this;

        tvErrorList = findViewById(R.id.tv_error_list);
        rlvListUser = findViewById(R.id.rlv_list_user);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // get data
        new LoadDetailUserAsync(this, this).execute();

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(FavoriteUserContract.CONTENT_URI, true, myObserver);
    }

    private void generateDataList(List<UserItem> listUser) {
        // set up the RecyclerView
        rlvListUser.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GithubUserAdapter(listUser);
        adapter.setClickListener(this);
        rlvListUser.setAdapter(adapter);
    }

    @Override
    public void onItemClick(UserItem data) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_USER, data);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle back button arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<UserItem> userItems) {
        if (userItems.size() > 0) {
            generateDataList(userItems);
        } else {
            tvErrorList.setText(R.string.no_user_found);
            tvErrorList.setVisibility(View.VISIBLE);
            rlvListUser.setVisibility(View.GONE);
        }
    }

    private static class LoadDetailUserAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadDetailUserCallback> weakCallback;
        private LoadDetailUserAsync(Context context, LoadDetailUserCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }
        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            weakCallback.get().preExecute();
            executor.execute(() -> {
                Context context = weakContext.get();
                Cursor dataCursor = context.getContentResolver().query(FavoriteUserContract.CONTENT_URI, null, null, null, null);
                ArrayList<UserItem> userItems = MappingHelper.mapCursorToArrayList(dataCursor);

                handler.post(() -> weakCallback.get().postExecute(userItems));
            });
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadDetailUserAsync(context, (LoadDetailUserCallback) context).execute();
        }
    }
}

interface LoadDetailUserCallback {
    void preExecute();
    void postExecute(ArrayList<UserItem> userItems);
}