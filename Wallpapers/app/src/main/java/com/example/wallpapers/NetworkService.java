package com.example.wallpapers;

import com.example.wallpapers.CategoryDetails;
import com.example.wallpapers.CategoryResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkService {

    @GET("wallpaper_one_category_list")
    Call<CategoryResponse> wallpaperOneCategoryList();

    @GET("wallpaper_one_post_list")
    Call<List<CategoryDetails>> wallpaperOnePostList(@Query("category_id") String cat_id);

}
