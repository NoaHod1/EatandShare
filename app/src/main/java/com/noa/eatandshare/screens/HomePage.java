package com.noa.eatandshare.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.noa.eatandshare.R;

public class HomePage extends BaseActivity {

    private View btnMyFav;
    private View btnGoToSearchRes;
    Button btnGoPersonalArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         btnMyFav = findViewById(R.id.btnGoMyFav);

         btnMyFav.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(HomePage.this, MyFavRes.class);
                 startActivity(intent);
             }
         });
        btnGoPersonalArea=findViewById(R.id.btnGoPersonalArea);
        btnGoPersonalArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, UserProfile.class);
                startActivity(intent);
            }
        });

         btnGoToSearchRes = findViewById(R.id.btnGoToSearchRes);
          btnGoToSearchRes.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, SearchRestaurant.class);
            startActivity(intent);
        });
    }
}








