package com.amostrone.akash.pawsome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        // Capture the layout's TextView and set the string as its text
        ((TextView)findViewById(R.id.name)).setText("Name : "+intent.getStringExtra("Name"));
        ((TextView)findViewById(R.id.bred)).setText("Bred For : "+intent.getStringExtra("Bred"));
        ((TextView)findViewById(R.id.temperament)).setText("Temperament : "+intent.getStringExtra("Temperament"));
        ((TextView)findViewById(R.id.life)).setText("Life Span : "+intent.getStringExtra("Life"));
        ((TextView)findViewById(R.id.weight)).setText("Weight : "+intent.getStringExtra("Weight")+" kg");
        ((TextView)findViewById(R.id.height)).setText("Height : "+intent.getStringExtra("Height")+" cm");

        ImageView dog = findViewById(R.id.imageView);
        Glide.with(this).load(intent.getStringExtra("Image"))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(dog);

        /*Glide.with(this).load(intent.getStringExtra("Image"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(dog);*/

    }
}