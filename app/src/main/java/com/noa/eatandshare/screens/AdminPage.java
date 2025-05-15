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

public class AdminPage extends BaseActivity implements View.OnClickListener {


    Button btnAddRestaurant,btnGoSearchPage,btnGoAfterLoginM,btnSearchUsers;

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

        btnAddRestaurant = findViewById(R.id.btnAddRestaurant2);
        btnAddRestaurant.setOnClickListener(this);

        btnGoSearchPage = findViewById(R.id.btnGoSearchPage);
        btnGoSearchPage.setOnClickListener(this);

        btnGoAfterLoginM = findViewById(R.id.btnGoAfterLoginM);
        btnGoAfterLoginM.setOnClickListener(this);

        btnSearchUsers = findViewById(R.id.btnSearchUsers);
        btnSearchUsers.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnAddRestaurant) {
            Intent go = new Intent(getApplicationContext(), AddRestaurantActivity.class);
            startActivity(go);
        }
        if (v == btnGoSearchPage) {
            Intent Search = new Intent(getApplicationContext(), SearchRestaurant.class);
            startActivity(Search);
        }
        if (v == btnGoAfterLoginM) {
            Intent Search = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Search);

        }
        if (v == btnSearchUsers) {
            Intent Search = new Intent(getApplicationContext(), SearchUsersActivity.class);
            startActivity(Search);
        }
    }

}