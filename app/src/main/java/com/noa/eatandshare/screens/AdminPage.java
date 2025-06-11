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


// הגדרת מסך בשם AdminPage שיורש מ־BaseActivity (ולא ישירות מ־AppCompatActivity)
// ומיישם את הממשק View.OnClickListener (כלומר יודע לטפל בלחיצות על רכיבים)

public class AdminPage extends BaseActivity implements View.OnClickListener {

    // משתנים עבור כל אחד מהכפתורים במסך
    Button btnAddRestaurant,btnGoSearchPage,btnGoAfterLoginM,btnSearchUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // מפעיל את הפונקציה onCreate שב־BaseActivity

        EdgeToEdge.enable(this); // מאפשר לעיצוב להתפרס עד שולי המסך
        setContentView(R.layout.activity_admin_page);  // קובע שהעיצוב של המסך יהיה לפי הקובץ activity_admin_page.xml

        // מאזין שמוודא שכל הרכיבים על המסך יסתדרו יפה לפי השוליים של המכשיר
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // מחפש את הכפתור במסך ומחבר אותו למשתנה בקוד
        btnAddRestaurant = findViewById(R.id.btnAddRestaurant2); // כשילחצו עליו – יעבור ל־onClick
        btnAddRestaurant.setOnClickListener(this);

        btnGoSearchPage = findViewById(R.id.btnGoSearchPage);
        btnGoSearchPage.setOnClickListener(this);



        btnSearchUsers = findViewById(R.id.btnSearchUsers);
        btnSearchUsers.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnAddRestaurant) {          // אם לחצו על כפתור הוספת מסעדה

            Intent go = new Intent(getApplicationContext(), AddRestaurantActivity.class);  // מעבר למסך AddRestaurantActivity
            startActivity(go); // מפעיל את המסך
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


    // הקוד הזה מגדיר מסך מנהל (AdminPage) שיש בו 4 כפתורים.
    //כל כפתור מוביל למסך אחר: הוספת מסעדה, חיפוש מסעדות, דף ראשי, או חיפוש משתמשים.
}