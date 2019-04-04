package com.example.wallpapers;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wallpapers.ImageViewerAdapter;
import com.example.wallpapers.CategoryDetails;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageViewer extends AppCompatActivity {

    ArrayList<CategoryDetails> data = new ArrayList<>();
    String TAG="ImageViewerClass";
    Button btnSave, btnDownload;
    String imageURL;
    DisplayMetrics displayMetrics;
    int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        Log.d(TAG,"On create Method");

        btnSave = findViewById(R.id.btn_save_wallpaper);
        btnDownload = findViewById(R.id.btn_download);
        final WallpaperManager manager = WallpaperManager.getInstance(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetScreenWidthHeight();
                Glide
                        .with(ImageViewer.this)
                        .asBitmap()
                        .load(imageURL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                try {
                                    Bitmap bitmap = Bitmap.createScaledBitmap(resource, width, height, true);

                                    manager.suggestDesiredDimensions(bitmap.getWidth(), bitmap.getHeight());
                                    manager.setBitmap(bitmap);
                                    Toast.makeText(ImageViewer.this, "Wallpaper is Successfully updated", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });


        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide
                        .with(ImageViewer.this)
                        .asBitmap()
                        .load(imageURL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                saveImage(resource);
                            }
                        });
            }
        });

        Intent intent = getIntent();
        data = (ArrayList<CategoryDetails>) intent.getSerializableExtra("data");
        Integer id = intent.getIntExtra("id", 0);

        getSupportActionBar().hide();

        String[] images = new String[data.size()];

        for (int i = 0; i < data.size(); i++) {
            images[i] = data.get(i).getImages();
        }

        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageViewerAdapter adapter = new ImageViewerAdapter(this, images, id);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(id);
    }

    private String saveImage(Bitmap image) {
        String savedImagePath = null;
        String imgFileName = new Date().toString() + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Wallpapers");

        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }

        if (success) {
            File imageFile = new File(storageDir, imgFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            galleryAddPic(savedImagePath);
            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
        }
        return savedImagePath;
    }


    private void galleryAddPic(String imagePath) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri uri = Uri.fromFile(f);
        intent.setData(uri);
        sendBroadcast(intent);
    }

    public void GetScreenWidthHeight() {
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
    }

    public void downloadSave(String image) {
        imageURL = image;
    }
}

