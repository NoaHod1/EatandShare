package com.noa.eatandshare.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnReg,btnLog,btnOdot1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        initviews();
    }

    private void initviews() {
        btnReg=findViewById(R.id.btnreg1);
        btnReg.setOnClickListener(this);
        btnLog=findViewById(R.id.btnLogin1);
        btnLog.setOnClickListener(this);
        btnOdot1=findViewById(R.id.Odot1);
        btnOdot1.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view ==btnReg){
            Intent intent = new Intent(MainActivity.this,register.class);
            startActivity(intent);

        }
        if (view ==btnLog){
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);

        }
        if (view ==btnOdot1){
            Intent intent = new Intent(MainActivity.this, OdotPage.class);
            startActivity(intent);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // תגובה ללחיצה על פריט בתפריט
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (id == R.id.menu_profile) {
            startActivity(new Intent(this, RestaurantProfile.class)); // שנה למחלקת פרופיל שלך
            return true;
        } else if (id == R.id.menu_settings) {
            Toast.makeText(this, "הגדרות יגיעו בקרוב", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}