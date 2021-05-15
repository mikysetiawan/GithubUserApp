package com.lingkarinovasimuda.githubuserapp.followers_following_fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lingkarinovasimuda.githubuserapp.activity.DetailActivity;
import com.lingkarinovasimuda.githubuserapp.R;
import com.lingkarinovasimuda.githubuserapp.adapter.GithubUserAdapter;
import com.lingkarinovasimuda.githubuserapp.model.DetailUser;
import com.lingkarinovasimuda.githubuserapp.model.UserItem;
import com.lingkarinovasimuda.githubuserapp.services.ApiServices;
import com.lingkarinovasimuda.githubuserapp.services.GetServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.EXTRA_USER;

public class TabFollowingFragment extends Fragment implements GithubUserAdapter.ClickListenerRecycler{

    DetailUser detailUser;
    ProgressDialog progressDialog;
    RecyclerView rlvListUser;
    GetServices service;
    TextView tvErrorList;
    GithubUserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_following, container, false);
        rlvListUser = rootView.findViewById(R.id.rlv_list_following);
        tvErrorList = rootView.findViewById(R.id.tv_error_following);

        service = ApiServices.getRetrofitInstance().create(GetServices.class);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            detailUser = bundle.getParcelable(EXTRA_USER);

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading....");
            progressDialog.show();

            Call<List<UserItem>> call = service.getUserFollowing(detailUser.getLogin());
            call.enqueue(new Callback<List<UserItem>>() {
                @Override
                public void onResponse(Call<List<UserItem>> call, Response<List<UserItem>> response) {
                    progressDialog.dismiss();
//                Log.d("RESPONSE GITHUB", "onResponse: ConfigurationListener::"+call.request().url());
//                Log.d("RESPONSE GITHUB", "onResponse: "+response.body());
                    if(response.body() != null && response.body().size() > 0){
                        tvErrorList.setVisibility(View.GONE);
                        rlvListUser.setVisibility(View.VISIBLE);

                        generateDataList(response.body());
                    }else{
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
                    Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return rootView;
    }

    private void generateDataList(List<UserItem> listUser) {
        // set up the RecyclerView
        rlvListUser.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GithubUserAdapter(listUser);
        adapter.setClickListener(this);
        rlvListUser.setAdapter(adapter);
    }

    @Override
    public void onItemClick(UserItem data) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(EXTRA_USER, data);
        startActivity(intent);
    }
}
