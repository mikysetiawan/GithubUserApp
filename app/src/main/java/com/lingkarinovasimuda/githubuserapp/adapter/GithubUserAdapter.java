package com.lingkarinovasimuda.githubuserapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lingkarinovasimuda.githubuserapp.R;
import com.lingkarinovasimuda.githubuserapp.model.GithubUser;

import java.util.List;

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserAdapter.ViewHolder> {

    private List<GithubUser> arr_githubuser;
    private ClickListenerRecycler click_listener;

    // data is passed into the constructor
    public GithubUserAdapter(List<GithubUser> data) {
        this.arr_githubuser = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.inflate(R.layout.recycleview_githubuser, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GithubUser user = arr_githubuser.get(position);
        holder.bind(user);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return arr_githubuser.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
        }

        public void bind(GithubUser data)
        {
            myTextView.setText(data.getName());
        }

        @Override
        public void onClick(View view) {
            if (click_listener != null) click_listener.onItemClick(arr_githubuser.get(getAdapterPosition()));
        }
    }

    // convenience method for getting data at click position
    GithubUser getItem(int id) {
        return arr_githubuser.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ClickListenerRecycler item_click_listener) {
        this.click_listener = item_click_listener;
    }

    // parent activity will implement this method to respond to click events
    public interface ClickListenerRecycler {
        void onItemClick(GithubUser data);
    }
}

