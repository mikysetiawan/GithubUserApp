package com.lingkarinovasimuda.githubuserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lingkarinovasimuda.githubuserapp.adapter.ListRepoAdapter;
import com.lingkarinovasimuda.githubuserapp.model.GithubUser;
import com.lingkarinovasimuda.githubuserapp.model.ListRepo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.EXTRA_USER;
import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.getImageId;

public class DetailActivity extends AppCompatActivity implements ListRepoAdapter.ClickListenerRecycler{

    GithubUser github_user;
    CircleImageView img_ava;
    TextView tv_repo, tv_follower, tv_following, tv_username, tv_name, tv_company, tv_location;
    RecyclerView rlv_list_repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        github_user = getIntent().getParcelableExtra(EXTRA_USER);
        img_ava = findViewById(R.id.img_ava);
        tv_repo = findViewById(R.id.tv_repo);
        tv_follower = findViewById(R.id.tv_follower);
        tv_following = findViewById(R.id.tv_following);
        tv_username = findViewById(R.id.tv_username);
        tv_name = findViewById(R.id.tv_name);
        tv_company = findViewById(R.id.tv_company);
        tv_location = findViewById(R.id.tv_location);

        // configure toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(github_user.getUsername());
        }

        img_ava.setImageResource(getImageId(this, github_user.getAvatar()));
        tv_repo.setText(github_user.getRepository());
        tv_follower.setText(github_user.getFollower());
        tv_following.setText(github_user.getFollowing());
        tv_name.setText(github_user.getName());
        tv_company.setText(github_user.getCompany());
        tv_location.setText(github_user.getLocation());

        rlv_list_repo = findViewById(R.id.rlv_list_repo);
        rlv_list_repo.setLayoutManager(new LinearLayoutManager(this));
        ListRepoAdapter adapter = new ListRepoAdapter(github_user.getList_repo());
        adapter.setClickListener(this);
        rlv_list_repo.setAdapter(adapter);
    }

    @Override
    public void onItemClick(ListRepo data) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getUrl()));
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
