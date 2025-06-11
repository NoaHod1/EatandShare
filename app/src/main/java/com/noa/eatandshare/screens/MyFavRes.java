package com.noa.eatandshare.screens;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noa.eatandshare.R;
import com.noa.eatandshare.adapters.RestaurantsAdapter;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.services.AuthenticationService;
import com.noa.eatandshare.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

// דף זה מציג למשתמש את רשימת המסעדות האהובות עליו
//המסעדות מוצגות ברשימה גלילה (RecyclerView), כאשר הנתונים מגיעים מה־Database לפי המשתמש שמחובר.

public class MyFavRes extends BaseActivity {

    private RecyclerView recyclerView;
    private RestaurantsAdapter restaurantsAdapter;
    // רשימת המסעדות שמוצגת
    private List<Restaurant> restaurantList = new ArrayList<>();
    private DatabaseService databaseService;
    String uid ;

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

      uid= AuthenticationService.getInstance().getCurrentUserId();
        // אתחול של רכיב הרשימה במסך
        recyclerView = findViewById(R.id.rcFavRestaurant);
        // קביעת סידור של הרשימה – שורות אחת אחרי השנייה (רשימה אנכית)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantsAdapter = new RestaurantsAdapter(restaurantList,MyFavRes.this);
        recyclerView.setAdapter(restaurantsAdapter);

        databaseService = DatabaseService.getInstance();

        // אם יש משתמש מחובר – נביא את רשימת המועדפים שלו

        if(uid!=null) {
            fetchRestaurants(uid);
        }

    }

    // פעולה שמביאה את המסעדות המועדפות של המשתמש מהמסד

    private void fetchRestaurants(String  uid2) {
        // קריאה למסד נתונים – מביא את המסעדות לפי מזהה המשתמש
        databaseService.getUserFavorite(uid2,new DatabaseService.DatabaseCallback<List<Restaurant>>() {
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