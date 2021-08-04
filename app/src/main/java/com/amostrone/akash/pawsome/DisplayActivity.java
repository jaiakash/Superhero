package com.amostrone.akash.pawsome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
    }
}