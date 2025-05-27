package com.noa.eatandshare.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.models.Review;
import com.noa.eatandshare.models.User;
import com.noa.eatandshare.services.AuthenticationService;
import com.noa.eatandshare.services.DatabaseService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddReview extends BaseActivity {

    EditText etReview;
    String stReview;
    RatingBar rtBar;
    double rate;
    Button btnAddReview, btnBack;
    private Intent takeit;
    private Restaurant restaurant;
    private DatabaseService databaseService;
    String uid ;
    User user=null;
    private String formattedDateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_review);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         formattedDateTime = now.format(formatter);
        Log.d("CurrentDate", "תאריך ושעה: " + formattedDateTime);



        databaseService=DatabaseService.getInstance();
        uid= AuthenticationService.getInstance().getCurrentUserId();

        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User object) {
                user=object;
                user=new User(user);

            }

            @Override
            public void onFailed(Exception e) {
                return;

            }
        });


        initViews();
        takeit=getIntent();
        restaurant= (Restaurant) takeit.getSerializableExtra("Rest");



    }

    private void initViews() {

        etReview=findViewById(R.id.etAddReview);
        rtBar=findViewById(R.id.rtAddResRev);
        btnAddReview=findViewById(R.id.btnAddREV);
        btnBack=findViewById(R.id.btnBack);

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stReview=etReview.getText().toString();
                rate=rtBar.getRating();


                String reviewId = databaseService.generateReviewId();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);

                Review newReview=new Review(reviewId, restaurant.getId(), user.getId(), formattedDateTime , rate, stReview);

                databaseService.saveReview(newReview, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {

                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                });

            }
        });



    }
}