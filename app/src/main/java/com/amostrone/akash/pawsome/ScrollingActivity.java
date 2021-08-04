package com.amostrone.akash.pawsome;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amostrone.akash.pawsome.databinding.ActivityScrollingBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;

    //Initialize variables
    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    ArrayList<MainData> dataArrayList = new ArrayList<MainData>();
    MainAdaptor adaptor;
    int page=1,limit=10;

    public static final String EXTRA_MESSAGE = "com.amostrone.akash.pawsome.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        //Assign Variables
        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        //Initialize adaptor
        adaptor = new MainAdaptor(ScrollingActivity.this,dataArrayList);

        //Set Layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Set adaptor
        recyclerView.setAdapter(adaptor);

        //Create get data method
        getData(page,limit);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY==(v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())){
                    page++;
                    progressBar.setVisibility(View.VISIBLE);

                    //Call get data method
                    getData(page,limit);
                }
            }
        });
    }

    private void getData(int page,int limit){
        //Initialise retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();


        //Create main interface
        MainInterface mainInterface = retrofit.create(MainInterface.class);
        //Initialize call
        Call<String> call = mainInterface.STRING_CALL(page, limit);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Check Condition
                if(response.isSuccessful() && response.body() != null){
                    // When successful fetch
                    progressBar.setVisibility(View.GONE);
                    try {
                        // Initialize json array
                        JSONArray jsonArray = new JSONArray(response.body());
                        // Parse json array
                        parseResult(jsonArray);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void parseResult(JSONArray jsonArray) {

        for(int i=0;i<jsonArray.length();i++){
            try {
                //initialize json object
                JSONObject object = jsonArray.getJSONObject(i);

                //Initialize main data
                MainData data = new MainData();

                //Set image
                //data.setImage("https://cdn2.thedogapi.com/images/"+object.getString("reference_image_id")+".jpg");

                //Toast.makeText(getApplicationContext(), object.getJSONObject("image").getString("url"), Toast.LENGTH_SHORT).show();
                data.setImage(object.getJSONObject("image").getString("url"));

                //Set properties
                data.setName(object.getString("name"));
                data.setBred(object.getString("bred_for"));
                data.setTemperament(object.getString("temperament"));
                data.setLife(object.getString("life_span"));

                //Add data in array list
                dataArrayList.add(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Initialize adaptor
            adaptor = new MainAdaptor(ScrollingActivity.this,dataArrayList);
            //Set adapter
            recyclerView.setAdapter(adaptor);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_upload) {
            Toast.makeText(getApplicationContext(), "App is in development", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}