package com.example.wallpapers;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.wallpapers.ImageViewerAdapter;
import com.example.wallpapers.WallpaperAdapter;
import com.example.wallpapers.CategoryDetails;
import com.example.wallpapers.Datum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    ImageView imgwallpaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        imgwallpaper = findViewById(R.id.img_wallpaper);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");

        getWallpaperDetails(id);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }


    }

    private void getWallpaperDetails(final String cat_id){

        Call<List<CategoryDetails>> call = ApiClient.getNetworkService().wallpaperOnePostList(cat_id);

        call.enqueue(new Callback<List<CategoryDetails>>() {
            @Override
            public void onResponse(Call<List<CategoryDetails>> call, Response<List<CategoryDetails>> response) {

                final List<CategoryDetails> data = response.body();



//                CircularProgressDrawable progressDrawable = new CircularProgressDrawable(Main2Activity.this);
//                progressDrawable.getStrokeWidth()
                if (imgwallpaper != null){
                    final ProgressBar progressBar = findViewById(R.id.progressBar);
                    Glide
                            .with(Main2Activity.this)
                            .load(data.get(Integer.parseInt(cat_id)).getImages())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .override(200, 200)
                            .error(R.drawable.ic_launcher_foreground)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(imgwallpaper);
                }


                WallpaperAdapter adapter = new WallpaperAdapter(data, Main2Activity.this);
                RecyclerView recyclerView = findViewById(R.id.rv_wallpapers);
                GridLayoutManager layoutManager = new GridLayoutManager(Main2Activity.this, 2);
                layoutManager.setOrientation(GridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);


                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(Main2Activity.this, ImageViewer.class);
                        intent.putExtra("id", position);
                        intent.putExtra("data", (Serializable) data);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onFailure(Call<List<CategoryDetails>> call, Throwable t) {
                Toast.makeText(Main2Activity.this, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
