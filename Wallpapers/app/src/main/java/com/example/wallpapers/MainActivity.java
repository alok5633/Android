package com.example.wallpapers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wallpapers.SportsAdapter;
import com.example.wallpapers.CategoryResponse;
import com.example.wallpapers.Datum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<Datum> data = new ArrayList<>();
    ProgressBar progressBar;
    ImageView sportImage;
    int count = 0;

    @Override
    public void onBackPressed() {
        if(count == 0){
            Toast.makeText(this, "Press Again to exit", Toast.LENGTH_SHORT).show();
        }else {
            finish();
        }
        count ++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        sportImage  = findViewById(R.id.img_sport_image);

        getWallpaperCategories();

    }


    private void getWallpaperCategories(){

        Call<CategoryResponse> call = ApiClient.getNetworkService().wallpaperOneCategoryList();

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                progressBar.setVisibility(View.GONE);

                data = new ArrayList<>();

                if (response.body() != null) {
                    data.addAll(response.body().getData());
                }




                for (int i = 0; i<response.body().getData().size(); i++){
                    // SportModel sportModel = new SportModel(response.body().getData().get(i).getCatName());
                    Datum datum = new Datum(response.body().getData().get(i).getId(), response.body().getData().get(i).getCatName(), response.body().getData().get(i).getCatImage());

                    if (sportImage != null){
                        Glide
                                .with(MainActivity.this)
                                .load(response.body().getData().get(i).getCatImage())
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_foreground)
                                .into(sportImage);
                    }

                    data.add(datum);
                }

                SportsAdapter adapter = new SportsAdapter(data, MainActivity.this);
                RecyclerView recyclerView = findViewById(R.id.rv_category);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("id", data.get(position).getId());
                        intent.putExtra("title", data.get(position).getCatName());
                        startActivity(intent);
                    }
                });
            }


            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });

    }

}
