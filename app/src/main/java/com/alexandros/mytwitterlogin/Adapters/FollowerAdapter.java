package com.alexandros.mytwitterlogin.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexandros.mytwitterlogin.Database.Entities.Follower;
import com.alexandros.mytwitterlogin.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FollowerAdapter extends RecyclerView.Adapter <FollowerAdapter.ExampleViewHolder>{
    private final List<Follower> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_view);

        }
    }

    public FollowerAdapter(List<Follower> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        Follower currentFollower = mExampleList.get(position);

        holder.mTextView.setText(currentFollower.getName());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
