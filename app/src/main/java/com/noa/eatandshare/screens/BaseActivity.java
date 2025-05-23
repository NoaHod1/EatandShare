package com.noa.eatandshare.screens;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

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
        } else if (id == R.id.menu_userProfile) {
            startActivity(new Intent(this, UserProfile.class));
            return true;
        } else if (id == R.id.menu_search) {
            startActivity(new Intent(this, SearchRestaurant.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
