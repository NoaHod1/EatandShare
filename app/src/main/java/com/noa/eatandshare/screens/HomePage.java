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

//הוא יורש מה־BaseActivity, מה שאומר שהוא מקבל גם את התפריט העליון עם האופציות (כמו פרופיל, התנתקות וכו').

public class HomePage extends BaseActivity {

    private View btnMyFav;
    private View btnGoToSearchRes;
    Button btnGoPersonalArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // מפעיל את מה שצריך בבסיס (BaseActivity)
        EdgeToEdge.enable(this);  // תצוגה עד קצה המסך - תומך במכשירים עם מסך "חתוך"
        setContentView(R.layout.activity_home_page);  // קובע איזה עיצוב יוצג במסך הזה

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnMyFav = findViewById(R.id.btnGoMyFav);  // מחפש את הכפתור במסך לפי ה-ID

        btnMyFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MyFavRes.class);  // מעבר למסך המועדפים
                startActivity(intent);
            }
        });
        btnGoPersonalArea = findViewById(R.id.btnGoPersonalArea);
        btnGoPersonalArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, UserProfile.class);  // מעבר לפרופיל משתמש
                startActivity(intent);
            }
        });

        btnGoToSearchRes = findViewById(R.id.btnGoToSearchRes);
        btnGoToSearchRes.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, SearchRestaurant.class);  // למסך החיפוש מסעדה
            startActivity(intent);
        });
    }
}








