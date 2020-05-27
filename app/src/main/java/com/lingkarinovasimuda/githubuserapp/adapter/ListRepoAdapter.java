package com.lingkarinovasimuda.githubuserapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lingkarinovasimuda.githubuserapp.R;
import com.lingkarinovasimuda.githubuserapp.model.ListRepo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.getImageId;

public class ListRepoAdapter extends RecyclerView.Adapter<ListRepoAdapter.ViewHolder> {

    private List<ListRepo> arr_list_repo;
    private ClickListenerRecycler click_listener;
    private Context context;

    // data is passed into the constructor
    public ListRepoAdapter(List<ListRepo> data) {
        this.arr_list_repo = data;
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
        ListRepo user = arr_list_repo.get(position);
        holder.bind(user);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return arr_list_repo.size();
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

        public void bind(ListRepo data)
        {
            tv_repo_name.setText(data.getRepo_name());
            tv_url.setText(data.getUrl());
        }

        @Override
        public void onClick(View view) {
            if (click_listener != null) click_listener.onItemClick(arr_list_repo.get(getAdapterPosition()));
        }
    }

    // convenience method for getting data at click position
    ListRepo getItem(int id) {
        return arr_list_repo.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ClickListenerRecycler item_click_listener) {
        this.click_listener = item_click_listener;
    }

    // parent activity will implement this method to respond to click events
    public interface ClickListenerRecycler {
        void onItemClick(ListRepo data);
    }
}

