package com.alexandros.mytwitterlogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter <Adapter.ExampleViewHolder>{
    private final ArrayList<CardViewItem> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_view);

        }
    }

    public Adapter(ArrayList<CardViewItem> exampleList) {
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

        CardViewItem currentItem = mExampleList.get(position);

        holder.mTextView.setText(currentItem.getmText());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
