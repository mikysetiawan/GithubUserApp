package com.lingkarinovasimuda.githubuserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lingkarinovasimuda.githubuserapp.adapter.ListRepoAdapter;
import com.lingkarinovasimuda.githubuserapp.model.DetailUser;
import com.lingkarinovasimuda.githubuserapp.model.UserItem;
import com.lingkarinovasimuda.githubuserapp.model.UserRepo;
import com.lingkarinovasimuda.githubuserapp.services.ApiServices;
import com.lingkarinovasimuda.githubuserapp.services.GetServices;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.EXTRA_USER;
import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.getImageId;

public class DetailActivity extends AppCompatActivity implements ListRepoAdapter.ClickListenerRecycler {

    UserItem githubUser;
    CircleImageView imgAva;
    TextView tvRepo, tvFollower, tvFollowing, tvUsername, tvName, tvCompany, tvLocation;
    RecyclerView rlv_list_repo;
    GetServices service;
    ProgressDialog progressDialog;
    DetailUser detailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        githubUser = getIntent().getParcelableExtra(EXTRA_USER);
        imgAva = findViewById(R.id.img_ava);
        tvRepo = findViewById(R.id.tv_repo);
        tvFollower = findViewById(R.id.tv_follower);
        tvFollowing = findViewById(R.id.tv_following);
        tvUsername = findViewById(R.id.tv_username);
        tvName = findViewById(R.id.tv_name);
        tvCompany = findViewById(R.id.tv_company);
        tvLocation = findViewById(R.id.tv_location);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(githubUser.getLogin());
        }

        service = ApiServices.getRetrofitInstance().create(GetServices.class);

        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        Call<DetailUser> call = service.getDetailUser(githubUser.getLogin());
        call.enqueue(new Callback<DetailUser>() {
            @Override
            public void onResponse(Call<DetailUser> call, Response<DetailUser> response) {
                progressDialog.dismiss();
//                Log.d("RESPONSE GITHUB", "onResponse: ConfigurationListener::"+call.request().url());
//                Log.d("RESPONSE GITHUB", "onResponse: "+response.body().getPublicRepos());
                if (response.body() != null) {
                    detailUser = response.body();
                    Picasso.Builder builder = new Picasso.Builder(DetailActivity.this);
                    builder.downloader(new OkHttp3Downloader(DetailActivity.this));
                    builder.build().load(detailUser.getAvatarUrl())
                            .placeholder((R.drawable.ic_launcher_background))
                            .error(R.drawable.ic_launcher_background)
                            .into(imgAva);

                    tvRepo.setText(detailUser.getPublicRepos().toString());
                    tvFollower.setText(detailUser.getFollowers().toString());
                    tvFollowing.setText(detailUser.getFollowing().toString());
                    tvName.setText(detailUser.getName());

                    if (detailUser.getCompany() == null) {
                        tvCompany.setVisibility(View.GONE);
                    } else {
                        tvCompany.setVisibility(View.VISIBLE);
                        tvCompany.setText(detailUser.getCompany());
                    }

                    if (detailUser.getLocation() == null) {
                        tvLocation.setVisibility(View.GONE);
                    } else {
                        tvLocation.setVisibility(View.VISIBLE);
                        tvLocation.setText(detailUser.getLocation());
                    }

                    tvFollower.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(DetailActivity.this, FollowersFollowingActivity.class);
                            intent.putExtra("tabOpen", 1);
                            intent.putExtra(EXTRA_USER, detailUser);
                            startActivity(intent);
                        }
                    });

                    tvFollowing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(DetailActivity.this, FollowersFollowingActivity.class);
                            intent.putExtra("tabOpen", 2);
                            intent.putExtra(EXTRA_USER, detailUser);
                            startActivity(intent);
                        }
                    });

                    generateRepoList(githubUser.getLogin());
                } else {
                    Log.e("RESPONSE GITHUB", "Response null: " + response.body());
                    Toast.makeText(DetailActivity.this, "No User Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailUser> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("RESPONSE GITHUB", "onFailure: ", t);
                Toast.makeText(DetailActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void generateRepoList(String username) {
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        Call<List<UserRepo>> call = service.getUserRepo(username);
        call.enqueue(new Callback<List<UserRepo>>() {
            @Override
            public void onResponse(Call<List<UserRepo>> call, Response<List<UserRepo>> response) {
                progressDialog.dismiss();
//                Log.d("RESPONSE GITHUB", "onResponse: ConfigurationListener::"+call.request().url());
//                Log.d("RESPONSE GITHUB", "onResponse: "+response.body());
                if (response.body() != null && response.body().size() > 0) {
                    // set up the RecyclerView
                    rlv_list_repo = findViewById(R.id.rlv_list_repo);
                    rlv_list_repo.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
                    ListRepoAdapter adapter = new ListRepoAdapter(response.body());
                    adapter.setClickListener(DetailActivity.this);
                    rlv_list_repo.setAdapter(adapter);
                } else {
                    Log.e("RESPONSE GITHUB", "Response null: " + response.body());
                    Toast.makeText(DetailActivity.this, "No Repo Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserRepo>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("RESPONSE GITHUB", "onFailure: ", t);
                Toast.makeText(DetailActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(UserRepo data) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getHtmlUrl()));
        startActivity(browserIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
