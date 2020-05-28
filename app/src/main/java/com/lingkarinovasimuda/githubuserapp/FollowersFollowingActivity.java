package com.lingkarinovasimuda.githubuserapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lingkarinovasimuda.githubuserapp.followers_following_fragment.TabFollowersFragment;
import com.lingkarinovasimuda.githubuserapp.followers_following_fragment.TabFollowingFragment;
import com.lingkarinovasimuda.githubuserapp.model.DetailUser;
import com.lingkarinovasimuda.githubuserapp.model.UserItem;
import com.lingkarinovasimuda.githubuserapp.ui.main.TabAdapter;

import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.EXTRA_USER;

public class FollowersFollowingActivity extends AppCompatActivity {

    private DetailUser detailUser;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Integer tabOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_following);

        detailUser = getIntent().getParcelableExtra(EXTRA_USER);
        tabOpened = getIntent().getIntExtra("tabOpen", 1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(detailUser.getLogin());
        }

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);

        Fragment fragmentFollowers = new TabFollowersFragment();
        Bundle bundle_followers = new Bundle();
        bundle_followers.putParcelable(EXTRA_USER, detailUser);
        fragmentFollowers.setArguments(bundle_followers);

        Fragment fragmentFollowing = new TabFollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_USER, detailUser);
        fragmentFollowing.setArguments(bundle);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(fragmentFollowers, detailUser.getFollowers() + " Followers");
        adapter.addFragment(fragmentFollowing, detailUser.getFollowing() + " Following");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        if (tabOpened == 2) {
            viewPager.setCurrentItem(2, true);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}