package com.example.bulksms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulksms.R;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    Context context;
ArrayList<String> list;
ArrayList<Integer> images;

    public ItemAdapter(Context context, ArrayList<String> list, ArrayList<Integer> images) {
        this.context = context;
        this.list = list;
        this.images = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_template,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.textView.setText(list.get(position));
      holder.imageView.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             textView=itemView.findViewById(R.id.myTest);
             imageView=itemView.findViewById(R.id.myImage);
        }
    }
}
