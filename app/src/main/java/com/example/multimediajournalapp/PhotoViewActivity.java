package com.example.multimediajournalapp;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class PhotoViewActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        imageView = findViewById(R.id.imageView);
        String photoPath = getIntent().getStringExtra("photoPath");
        Glide.with(this).load(photoPath).into(imageView);
    }
}