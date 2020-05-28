package com.lingkarinovasimuda.githubuserapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lingkarinovasimuda.githubuserapp.adapter.GithubUserAdapter;
import com.lingkarinovasimuda.githubuserapp.model.UserItem;
import com.lingkarinovasimuda.githubuserapp.model.ResponseGithubSearchUser;
import com.lingkarinovasimuda.githubuserapp.services.ApiServices;
import com.lingkarinovasimuda.githubuserapp.services.GetServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.EXTRA_USER;

public class MainActivity extends AppCompatActivity implements GithubUserAdapter.ClickListenerRecycler {

    GithubUserAdapter adapter;
    ProgressDialog progressDialog;
    EditText edtSearchUsername;
    TextView tvErrorList;
    RecyclerView rlvListUser;
    GetServices service;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearchUsername = findViewById(R.id.edt_search_username);
        tvErrorList = findViewById(R.id.tv_error_list);
        rlvListUser = findViewById(R.id.rlv_list_user);

        service = ApiServices.getRetrofitInstance().create(GetServices.class);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        Call<List<UserItem>> call = service.getListUser();
        call.enqueue(new Callback<List<UserItem>>() {
            @Override
            public void onResponse(Call<List<UserItem>> call, Response<List<UserItem>> response) {
                progressDialog.dismiss();
//                Log.d("RESPONSE GITHUB", "onResponse: ConfigurationListener::"+call.request().url());
//                Log.d("RESPONSE GITHUB", "onResponse: "+response.body());
                if (response.body() != null && response.body().size() > 0) {
                    tvErrorList.setVisibility(View.GONE);
                    rlvListUser.setVisibility(View.VISIBLE);

                    generateDataList(response.body());
                } else {
                    tvErrorList.setText(R.string.no_user_found);
                    tvErrorList.setVisibility(View.VISIBLE);
                    rlvListUser.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<UserItem>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("RESPONSE GITHUB", "onFailure: ", t);
                tvErrorList.setText(R.string.check_connection);
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        edtSearchUsername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                //Get event when user click the search icon
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (edtSearchUsername.getRight() - edtSearchUsername.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        searchUserGithub(edtSearchUsername.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void searchUserGithub(String username) {
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        Call<ResponseGithubSearchUser> call = service.getSearchUser(username);
        call.enqueue(new Callback<ResponseGithubSearchUser>() {
            @Override
            public void onResponse(Call<ResponseGithubSearchUser> call, Response<ResponseGithubSearchUser> response) {
                progressDialog.dismiss();
//                Log.d("RESPONSE GITHUB", "onResponse: ConfigurationListener::"+call.request().url());
//                Log.d("RESPONSE GITHUB", "onResponse: "+response.body().getTotalCount());
                if (response.body() != null && response.body().getTotalCount() > 0) {
                    tvErrorList.setVisibility(View.GONE);
                    rlvListUser.setVisibility(View.VISIBLE);

                    List<UserItem> user = response.body().getItems();
                    Log.d("RESPONSE GITHUB", "onResponse: " + response.body());
                    generateDataList(user);
                } else {
                    tvErrorList.setText(R.string.no_user_found);
                    tvErrorList.setVisibility(View.VISIBLE);
                    rlvListUser.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseGithubSearchUser> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("RESPONSE GITHUB", "onFailure: ", t);
                tvErrorList.setText(R.string.check_connection);
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
