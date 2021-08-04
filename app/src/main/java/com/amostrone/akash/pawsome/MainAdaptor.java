package com.amostrone.akash.pawsome;

import static com.amostrone.akash.pawsome.ScrollingActivity.EXTRA_MESSAGE;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class MainAdaptor extends RecyclerView.Adapter<MainAdaptor.ViewHolder> {

    //Initialize Variables
    private ArrayList<MainData> dataArrayList;
    private Activity activity;

    //Create Constructor
    public MainAdaptor(Activity activity, ArrayList<MainData> dataArrayList){
        this.activity = activity;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initialize main data
        MainData data = dataArrayList.get(position);

        //Set image on imageview
        Glide.with(activity).load(data.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        //Set name on text view
        holder.textView.setText(data.getName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(activity.getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(activity.getApplicationContext(), DisplayActivity.class);

                intent.putExtra("Name", data.getName());
                intent.putExtra("Bred", data.getBred());
                intent.putExtra("Temperament", data.getTemperament());
                intent.putExtra("Life", data.getLife());
                intent.putExtra("Height", data.getHeight());
                intent.putExtra("Weight", data.getWeight());
                intent.putExtra("Image", data.getImage());

                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Initialize Variables
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Assign Variables
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
            linearLayout = itemView.findViewById(R.id.ll_card);
        }
    }
}
