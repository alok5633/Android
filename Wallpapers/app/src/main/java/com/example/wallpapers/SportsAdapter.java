package com.example.wallpapers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wallpapers.MainActivity;
import com.example.wallpapers.Datum;
import com.example.wallpapers.R;

import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.MyViewHolder> {

    private List<Datum> data;
    private Context context;


    public SportsAdapter(List<Datum> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_category,viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(data.get(i).getCatName());
        Glide
                .with(context)
                .load(data.get(i).getCatImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size()-11;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_sport_image);
            textView = itemView.findViewById(R.id.tv_sport_name);

           /* textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, data.get(getAdapterPosition()).getSportTitle(), Toast.LENGTH_SHORT).show();

                }
            });*/
        }
    }
}

