package com.vignesh.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RetrofitOneAdapter extends RecyclerView.Adapter<RetrofitOneAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<ModelOne> dataModelArrayList;

    RetrofitOneAdapter(Context ctx, ArrayList<ModelOne> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public RetrofitOneAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.retro_item_one, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RetrofitOneAdapter.MyViewHolder holder, int position) {

        Picasso.get().load(dataModelArrayList.get(position).getImageHref()).into(holder.iv);
        holder.title.setText(dataModelArrayList.get(position).getTitle());
        holder.description.setText(dataModelArrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title)
        FontTextView title;

        @BindView(R.id.description)
        TextView description;

        @BindView(R.id.iv)
        ImageView iv;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
