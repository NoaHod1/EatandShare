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

    //מאפשר למשתמש לצפות בכל המסעדות הקיימות באפליקציה או לסנן אותן לפי עיר.
    //הנתונים נשלפים ממסד הנתונים ומוצגים בתוך RecyclerView.
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

        etCitySearch = findViewById(R.id.etCitySearch);
        btnSearchByCity = findViewById(R.id.btnSearchByCity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantsAdapter = new RestaurantsAdapter(restaurantList,SearchRestaurant.this);
        recyclerView.setAdapter(restaurantsAdapter);

        databaseService = DatabaseService.getInstance();

        fetchRestaurants(null);

        // האזנה ללחיצה על כפתור חיפוש לפי עיר
        btnSearchByCity.setOnClickListener(v -> {
            String city = etCitySearch.getText().toString().trim();
            if (!city.isEmpty()) {
                // חיפוש מסעדות לפי עיר
                fetchRestaurants(city);
            } else {
                Toast.makeText(SearchRestaurant.this, "הזן עיר לחיפוש", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchRestaurants(String city) {
        databaseService.getRestaurants(new DatabaseService.DatabaseCallback<List<Restaurant>>() {

            @Override
            public void onCompleted(List<Restaurant> object) {
                Log.d(TAG, "onCompleted: " + object);
                restaurantList.clear();

                if (city != null && !city.isEmpty()) {
                    // סינון מסעדות לפי עיר
                    for (Restaurant restaurant : object) {
                        if (restaurant.getCity().equalsIgnoreCase(city)) {
                            restaurantList.add(restaurant);
                        }
                    }
                } else {
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
