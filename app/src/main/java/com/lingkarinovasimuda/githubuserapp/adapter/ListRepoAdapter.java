package com.lingkarinovasimuda.githubuserapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lingkarinovasimuda.githubuserapp.R;
import com.lingkarinovasimuda.githubuserapp.model.UserItem;
import com.lingkarinovasimuda.githubuserapp.model.UserRepo;

import java.util.List;

public class ListRepoAdapter extends RecyclerView.Adapter<ListRepoAdapter.ViewHolder> {

    private List<UserRepo> arrListRepo;
    private ClickListenerRecycler clickListener;
    private Context context;

    // data is passed into the constructor
    public ListRepoAdapter(List<UserRepo> data) {
        this.arrListRepo = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.inflate(R.layout.recycleview_listrepo, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserRepo user = arrListRepo.get(position);
        holder.bind(user);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return arrListRepo.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_repo_name, tv_url;

        ViewHolder(View itemView) {
            super(itemView);
            tv_repo_name = itemView.findViewById(R.id.tv_repo_name);
            tv_url = itemView.findViewById(R.id.tv_url);
            itemView.setOnClickListener(this);
        }

        public void bind(UserRepo data) {
            tv_repo_name.setText(data.getName());
            tv_url.setText(data.getHtmlUrl());
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(arrListRepo.get(getAdapterPosition()));
        }
    }

    // convenience method for getting data at click position
    UserRepo getItem(int id) {
        return arrListRepo.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ClickListenerRecycler item_clickListener) {
        this.clickListener = item_clickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ClickListenerRecycler {
        void onItemClick(UserRepo data);
    }
}

