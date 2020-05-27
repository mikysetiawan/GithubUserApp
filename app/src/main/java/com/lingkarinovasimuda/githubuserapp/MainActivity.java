package com.lingkarinovasimuda.githubuserapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lingkarinovasimuda.githubuserapp.adapter.GithubUserAdapter;
import com.lingkarinovasimuda.githubuserapp.model.GithubUser;
import com.lingkarinovasimuda.githubuserapp.model.ListRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.EXTRA_USER;

public class MainActivity extends AppCompatActivity implements GithubUserAdapter.ClickListenerRecycler{

    GithubUserAdapter adapter;
    List<GithubUser> arr_githubuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arr_githubuser = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this) );
            JSONArray arr_list_user = obj.getJSONArray("users");

            for (int i = 0; i < arr_list_user.length(); i++) {
                JSONObject user_obj = arr_list_user.getJSONObject(i);
//                Log.d("testing", "onCreate: "+user_obj);
                GithubUser user = new GithubUser();
                user.setUsername(user_obj.getString("username"));
                user.setName(user_obj.getString("name"));
                user.setAvatar(user_obj.getString("avatar"));
                user.setCompany(user_obj.getString("company"));
                user.setLocation(user_obj.getString("location"));
                user.setRepository(user_obj.getString("repository"));
                user.setFollower(user_obj.getString("follower"));
                user.setFollowing(user_obj.getString("following"));

                //Get list repo
                JSONArray arr_list_repo = user_obj.getJSONArray("list_repo");
                List<ListRepo> list_repo = new ArrayList<>();
                for (int j = 0; j < arr_list_repo.length(); j++) {
                    JSONObject repo_obj = arr_list_repo.getJSONObject(j);
                    Log.d("testing", "list repo : "+repo_obj);
                    ListRepo list = new ListRepo();
                    list.setRepo_name(repo_obj.getString("name"));
                    list.setUrl(repo_obj.getString("url"));
                    list_repo.add(list);
                }
                user.setList_repo(list_repo);

                arr_githubuser.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rlv_list_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GithubUserAdapter(arr_githubuser);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("githubuser.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    public void onItemClick(GithubUser data) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_USER, data);
        startActivity(intent);
    }
}
