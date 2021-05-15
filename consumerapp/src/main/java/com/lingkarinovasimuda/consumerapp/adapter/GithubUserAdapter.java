package com.lingkarinovasimuda.consumerapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lingkarinovasimuda.consumerapp.R;
import com.lingkarinovasimuda.consumerapp.model.UserItem;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserAdapter.ViewHolder> {

    private final List<UserItem> arrGithubUser;
    private ClickListenerRecycler clickListener;
    private Context context;

    // data is passed into the constructor
    public GithubUserAdapter(List<UserItem> data) {
        this.arrGithubUser = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.inflate(R.layout.recycleview_githubuser, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserItem user = arrGithubUser.get(position);
        holder.bind(user);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return arrGithubUser.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_username;
        CircleImageView img_ava;

        ViewHolder(View itemView) {
            super(itemView);
            img_ava = itemView.findViewById(R.id.img_ava);
            tv_username = itemView.findViewById(R.id.tv_username);
            itemView.setOnClickListener(this);
        }

        public void bind(UserItem data) {
            Log.d("TESTING", "bind: "+data.getAvatarUrl());
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(data.getAvatarUrl())
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(img_ava);

            tv_username.setText(data.getLogin());
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(arrGithubUser.get(getAdapterPosition()));
        }
    }

    // convenience method for getting data at click position
    UserItem getItem(int id) {
        return arrGithubUser.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ClickListenerRecycler item_clickListener) {
        this.clickListener = item_clickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ClickListenerRecycler {
        void onItemClick(UserItem data);
    }
}

