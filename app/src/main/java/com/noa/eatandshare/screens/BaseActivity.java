package com.noa.eatandshare.screens;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.noa.eatandshare.R;

public class BaseActivity extends AppCompatActivity {

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
