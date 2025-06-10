package com.noa.eatandshare.screens;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

public class SearchRestaurant extends BaseActivity {

    private static final String TAG = "ShowRestaurants";

    private RecyclerView recyclerView;
    private RestaurantsAdapter restaurantsAdapter;
    private List<Restaurant> restaurantList = new ArrayList<>();
    private DatabaseService databaseService;

    private EditText etCitySearch;
    private Button btnSearchByCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_restaurant);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        etCitySearch = findViewById(R.id.etCitySearch);
        btnSearchByCity = findViewById(R.id.btnSearchByCity);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantsAdapter = new RestaurantsAdapter(restaurantList,SearchRestaurant.this);
        recyclerView.setAdapter(restaurantsAdapter);

        // Initialize DatabaseService
        databaseService = DatabaseService.getInstance();

        // Fetch all restaurants from the database initially
        fetchRestaurants(null);

        // Set button click listener to search by city
        btnSearchByCity.setOnClickListener(v -> {
            String city = etCitySearch.getText().toString().trim();
            if (!city.isEmpty()) {
                // Fetch restaurants based on the city entered
                fetchRestaurants(city);
            } else {
                // Show message if no city is entered
                Toast.makeText(SearchRestaurant.this, "הזן עיר לחיפוש", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchRestaurants(String city) {
        // Show a loading message if needed

        // Fetch restaurants from the database
        databaseService.getRestaurants(new DatabaseService.DatabaseCallback<List<Restaurant>>() {
            @Override
            public void onCompleted(List<Restaurant> object) {
                Log.d(TAG, "onCompleted: " + object);
                restaurantList.clear();

                if (city != null && !city.isEmpty()) {
                    // Filter restaurants by city
                    for (Restaurant restaurant : object) {
                        if (restaurant.getCity().equalsIgnoreCase(city)) {
                            restaurantList.add(restaurant);
                        }
                    }
                } else {
                    // Add all restaurants if no city filter is provided
                    restaurantList.addAll(object);
                }

                restaurantsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: ", e);
            }
        });
    }
}
