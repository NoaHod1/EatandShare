package com.noa.eatandshare.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;

public class AdminPage extends AppCompatActivity implements View.OnClickListener{


    Button btnGoSearchPage, btnAddRes2, btnGoAfterLogin, btnGoAllOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnAddRes2 = findViewById(R.id.btnAddRestaurant2);
        btnGoAllOrders = findViewById(R.id.btnGoAllOrders);

        btnGoSearchPage.setOnClickListener((View.OnClickListener) this);

        btnGoAfterLogin.setOnClickListener((View.OnClickListener) this);
        btnGoAllOrders.setOnClickListener((View.OnClickListener) this);

        btnAddRes2.setOnClickListener((View.OnClickListener) this);
    }


    @Override
    public void onClick(View view) {
        if (view == btnGoSearchPage) {
            Intent go = new Intent(this, GoSearchPage.class);
            startActivity(go);
        }
        //  if(view==btnGoAddDonationPlace){
        //     Intent go=new Intent(this, AddDonationPlace.class);
        //      startActivity(go);
        // }
        if (view == btnGoAfterLogin) {
            Intent go = new Intent(this, AfterLogIn.class);
            startActivity(go);
        }

        if (view == btnAddRes2) {
            Intent go = new Intent(this, AddRestaurantActivity.class);
            startActivity(go);
        }
        if (view == btnGoAllOrders) {
            Intent go = new Intent(this, GoAllOrders.class);
            startActivity(go);
        }
    }






}