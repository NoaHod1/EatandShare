package com.noa.eatandshare.screens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;
import com.noa.eatandshare.adapters.ReviewsAdapter;
import com.noa.eatandshare.models.Review;

import java.util.ArrayList;

public class RestaurantProfile extends AppCompatActivity {


    TextView tvResName, tvType, tvKosher, tvCity, tvDomain;
    ImageView ivRes;
    RatingBar rb;
    Button btnAdd, btnSave, btnBack;
    ListView lvReviews;
    ReviewsAdapter reviewsAdapter;
    ArrayList<Review> reviewArratyList=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurant_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

    }

    private void initViews()

    {
        tvResName=findViewById(R.id.tvNameResProfile);
        tvType=findViewById(R.id.tvTypeResProfile);
        tvKosher=findViewById(R.id.tvKosherResProfile);
        tvCity=findViewById(R.id.tvCityResProfile);
        tvDomain=findViewById(R.id.tvDomainResProfile);
        ivRes=findViewById(R.id.ivResProfile);
        rb=findViewById(R.id.ratingBarResProfile);
        btnSave=findViewById(R.id.btnSaveResProfile);
        btnBack=findViewById(R.id.btnBackResProfile);
        lvReviews=findViewById(R.id.);



    }
}