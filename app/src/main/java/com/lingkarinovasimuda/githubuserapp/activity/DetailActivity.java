package com.lingkarinovasimuda.githubuserapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lingkarinovasimuda.githubuserapp.R;
import com.lingkarinovasimuda.githubuserapp.adapter.ListRepoAdapter;
import com.lingkarinovasimuda.githubuserapp.db.FavoriteUserContract;
import com.lingkarinovasimuda.githubuserapp.db.FavoriteUserHelper;
import com.lingkarinovasimuda.githubuserapp.model.DetailUser;
import com.lingkarinovasimuda.githubuserapp.model.UserItem;
import com.lingkarinovasimuda.githubuserapp.model.UserRepo;
import com.lingkarinovasimuda.githubuserapp.services.ApiServices;
import com.lingkarinovasimuda.githubuserapp.services.GetServices;
import com.lingkarinovasimuda.githubuserapp.utils.MappingHelper;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lingkarinovasimuda.githubuserapp.db.FavoriteUserContract.CONTENT_URI;
import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.EXTRA_USER;

public class DetailActivity extends AppCompatActivity implements ListRepoAdapter.ClickListenerRecycler {
    private String TAG = "DetailActivity";
    private Context context;
    private UserItem githubUser;
    private CircleImageView imgAva;
    private TextView tvRepo, tvFollower, tvFollowing, tvUsername, tvName, tvCompany, tvLocation;
    private RecyclerView rlv_list_repo;
    private GetServices service;
    private ProgressDialog progressDialog;
    private DetailUser detailUser;
    private Button btnFavorite;
    private Boolean statusFavorite = false, isEdit = false;
    private FavoriteUserHelper favoriteUserHelper;
    private Uri uriWithId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = getApplicationContext();

        imgAva = findViewById(R.id.img_ava);
        tvRepo = findViewById(R.id.tv_repo);
        tvFollower = findViewById(R.id.tv_follower);
        tvFollowing = findViewById(R.id.tv_following);
        tvUsername = findViewById(R.id.tv_username);
        tvName = findViewById(R.id.tv_name);
        tvCompany = findViewById(R.id.tv_company);
        tvLocation = findViewById(R.id.tv_location);
        btnFavorite = findViewById(R.id.btn_favorite);

        favoriteUserHelper = FavoriteUserHelper.getInstance(getApplicationContext());
        favoriteUserHelper.open();

        githubUser = getIntent().getParcelableExtra(EXTRA_USER);

//        Cursor dataCursor = favoriteUserHelper.queryById(String.valueOf(githubUser.getId()));
//        detailUser = MappingHelper.mapCursorToDetailUser(dataCursor);
        // Uri yang di dapatkan disini akan digunakan untuk ambil data dari provider
        uriWithId = Uri.parse(CONTENT_URI + "/" + githubUser.getId());
        if (uriWithId != null) {
            Cursor cursor = getContentResolver().query(uriWithId, null, null, null, null);
            if (cursor != null) {
                detailUser = MappingHelper.mapCursorToDetailUser(cursor);
                cursor.close();
            }
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(githubUser.getLogin());
        }

        service = ApiServices.getRetrofitInstance().create(GetServices.class);

        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Loading....");

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFavoriteToDB();
            }
        });

        if(detailUser.getId() != null){
            setDataToUI();
            setStatusFavoriteUI();
        }else{
            progressDialog.show();

            Call<DetailUser> call = service.getDetailUser(githubUser.getLogin());
            call.enqueue(new Callback<DetailUser>() {
                @Override
                public void onResponse(Call<DetailUser> call, Response<DetailUser> response) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        detailUser = response.body();
                        setDataToUI();
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

    }

    private void setDataToUI(){
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
        // handle back button arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void setStatusFavoriteUI(){
        if(!statusFavorite){
            //set button after saving to favorite
            btnFavorite.setText(R.string.remove_favorite);
            btnFavorite.setBackgroundResource(R.drawable.btn_outline_blue);
            btnFavorite.setTextColor(getResources().getColor(R.color.blue_500));
        }else{
            //set button after remove from favorite
            btnFavorite.setText(R.string.add_favorite);
            btnFavorite.setBackgroundResource(R.drawable.btn_fill_blue);
            btnFavorite.setTextColor(getResources().getColor(R.color.white));
        }
        statusFavorite = !statusFavorite;
    }

    private void saveFavoriteToDB(){
        ContentValues values = new ContentValues();
        // Uri yang di dapatkan disini akan digunakan untuk ambil data dari provider
        // content://com.lingkarinovasimuda.githubuserapp/favorite_user/id

        if(statusFavorite){
            //Remove from favorite
            getContentResolver().delete(uriWithId, null, null);
            setStatusFavoriteUI();
        }else{
            //Save to favorite
            values.put(FavoriteUserContract.FavoriteUserColumn._ID, detailUser.getId());
            values.put(FavoriteUserContract.FavoriteUserColumn.LOGIN, detailUser.getLogin());
            values.put(FavoriteUserContract.FavoriteUserColumn.NAME, detailUser.getName());
            values.put(FavoriteUserContract.FavoriteUserColumn.AVATAR_URL, detailUser.getAvatarUrl());
            values.put(FavoriteUserContract.FavoriteUserColumn.PUBLIC_REPOS, detailUser.getPublicRepos());
            values.put(FavoriteUserContract.FavoriteUserColumn.PUBLIC_GISTS, detailUser.getPublicGists());
            values.put(FavoriteUserContract.FavoriteUserColumn.FOLLOWERS, detailUser.getFollowers());
            values.put(FavoriteUserContract.FavoriteUserColumn.FOLLOWING, detailUser.getFollowing());

            if (CONTENT_URI != null) {
                getContentResolver().insert(CONTENT_URI, values);
                setStatusFavoriteUI();
            }
        }
    }
}
