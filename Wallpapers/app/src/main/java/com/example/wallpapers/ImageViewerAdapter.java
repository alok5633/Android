package com.example.wallpapers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wallpapers.ImageViewer;
import com.example.wallpapers.CategoryDetails;
import com.example.wallpapers.R;

import java.util.ArrayList;


public class ImageViewerAdapter extends PagerAdapter {

    private Context context;
    private String[] images;
    int id;


    public ImageViewerAdapter(Context context, String[] images, int id) {
        this.context = context;
        this.images = images;
        this.id = id;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setPrimaryItem(container, id, imageView);
        Glide
                .with(context)
                .load(images[position])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView);
        container.addView(imageView, position-images.length);


        if(position == 0){
            ((ImageViewer)context).downloadSave(images[position]);

        }else {
            ((ImageViewer)context).downloadSave(images[position-1]);

        }
        return imageView;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

