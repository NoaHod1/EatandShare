package com.noa.eatandshare.screens;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import com.noa.eatandshare.R;
import com.noa.eatandshare.adapters.ReviewsAdapter;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.models.Review;
import com.noa.eatandshare.services.AuthenticationService;
import com.noa.eatandshare.services.DatabaseService;
import com.noa.eatandshare.utils.ImageUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RestaurantProfile extends AppCompatActivity {
    private static final int REQUEST_PHONE_CALL = 1;


    TextView tvResName, tvType, tvKosher, tvCity, tvDomain, tvDetails;
    ImageView ivRes;
    RatingBar rb;
    Button btnAdd, btnSave, btnBack,btnCallToRes;
    ListView lvReviews;
    ReviewsAdapter reviewsAdapter;
    ArrayList<Review> reviewArratyList=new ArrayList();

    private AuthenticationService authenticationService;
    private DatabaseService databaseService;

    String uid;

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




        /// get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();

        uid=authenticationService.getCurrentUserId();

        initViews();




        takeit=getIntent();
        restaurant= (Restaurant) takeit.getSerializableExtra("Rest");

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseService.saveFavoriteRes(restaurant, uid, new DatabaseService.DatabaseCallback<Void>() {
                   public @Override
                    void onCompleted(Void object) {
                       //Toast.

                    }

                 public    @Override
                    void onFailed(Exception e) {

                    }
                });


            }
        });
        btnBack=findViewById(R.id.btnBackResProfile);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantProfile.this, SearchRestaurant.class);
            startActivity(intent);
        });

        tvDetails=findViewById(R.id.tvDetailsProfile);

        btnCallToRes=findViewById(R.id.btnCallToRes);
        btnCallToRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The phone number you want to call
                String phoneNumber = "tel:1234567890"; // replace with the actual number




                // Create an intent to open the dialer
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse(phoneNumber));

                // Start the dialer activity
                startActivity(dialIntent);

                // Check if the permission is granted

            }

        });

}




@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_PHONE_CALL) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, make the call
            String phoneNumber = "tel:1234567890"; // replace with the actual number
            makeCall(phoneNumber);
        } else {
            // Permission denied, show a message
            Toast.makeText(this, "Permission denied to make phone calls", Toast.LENGTH_SHORT).show();
        }
    }
}

private void makeCall(String phoneNumber) {
    Intent callIntent = new Intent(Intent.ACTION_CALL);
    callIntent.setData(Uri.parse(phoneNumber));
    startActivity(callIntent);
}


}
