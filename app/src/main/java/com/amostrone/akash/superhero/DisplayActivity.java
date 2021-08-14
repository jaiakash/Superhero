package com.amostrone.akash.superhero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public void onShareItem(View v) {
        // Get access to bitmap image from view
        ImageView ivImage = (ImageView) findViewById(R.id.imageView);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(ivImage);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }
    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            bmpUri = FileProvider.getUriForFile(
                    DisplayActivity.this,
                    "com.amostrone.akash.pawsome.fileprovider", //(use your app signature + ".provider" )
                    file);

            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
            //bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}