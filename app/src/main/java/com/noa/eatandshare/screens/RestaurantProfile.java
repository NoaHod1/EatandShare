package com.noa.eatandshare.screens;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.models.Review;
import com.noa.eatandshare.utils.ImageUtil;

import java.util.ArrayList;

public class RestaurantProfile extends AppCompatActivity {


    TextView tvResName, tvType, tvKosher, tvCity, tvDomain, tvDetails;
    ImageView ivRes;
    RatingBar rb;
    Button btnAdd, btnSave, btnBack;
    ListView lvReviews;
    ReviewsAdapter reviewsAdapter;
    ArrayList<Review> reviewArratyList=new ArrayList();

Intent takeit;
Restaurant restaurant=null;
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


        takeit=getIntent();
        restaurant= (Restaurant) takeit.getSerializableExtra("Rest");
        initViews();

        setDataFields();

    }

    private void setDataFields() {

        if(restaurant!=null){

            tvCity.setText(restaurant.getCity());
            tvDomain.setText(restaurant.getDomain());
           // tvKosher.setText(restaurant.get());
            tvType.setText(restaurant.getType());
            tvResName.setText(restaurant.getName());

            tvDetails.setText(restaurant.getDetails());

            rb.setRating(Float.parseFloat(restaurant.getRate()+""));
            if(restaurant.isKosher())
                tvKosher.setText("כשרה");
            else tvKosher.setText("לא כשרה");

            if (restaurant.getPic() != null) {
                Bitmap bitmap = ImageUtil.convertFrom64base(restaurant.getPic());
                ivRes.setImageBitmap(bitmap);
            }

        }
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
        tvDetails=findViewById(R.id.tvDetailsProfile);


    }
}