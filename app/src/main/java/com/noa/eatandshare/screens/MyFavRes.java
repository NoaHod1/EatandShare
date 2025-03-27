package com.noa.eatandshare.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noa.eatandshare.R;
import com.noa.eatandshare.adapters.RestaurantsAdapter;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class MyFavRes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantsAdapter restaurantsAdapter;
    private List<Restaurant> restaurantList = new ArrayList<>();
    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_fav_res);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Set up RecyclerView
        recyclerView = findViewById(R.id.rcFavRestaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantsAdapter = new RestaurantsAdapter(restaurantList,MyFavRes.this);
        recyclerView.setAdapter(restaurantsAdapter);

        // Initialize DatabaseService
        databaseService = DatabaseService.getInstance();

        // Fetch all restaurants from the database initially
        fetchRestaurants();

        // Set button click listener to search by city

    }

    private void fetchRestaurants() {
        // Show a loading message if needed

        // Fetch restaurants from the database
        databaseService.getRestaurants(new DatabaseService.DatabaseCallback<List<Restaurant>>() {
            @Override
            public void onCompleted(List<Restaurant> object) {
                Log.d("TAG", "onCompleted: " + object);
                restaurantList.clear();


                    // Add all restaurants if no city filter is provided
                    restaurantList.addAll(object);


                restaurantsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e("TAG", "onFailed: ", e);
            }
        });
    }
}