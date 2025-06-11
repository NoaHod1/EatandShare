package com.noa.eatandshare.screens;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.noa.eatandshare.R;
import com.noa.eatandshare.services.AuthenticationService;

// מחלקת BaseActivity יורשת מ-AppCompatActivity
// זו מחלקת בסיס שכל שאר המסכים יורשים ממנה – כדי לשתף תכונות משותפות
public class BaseActivity extends AppCompatActivity {

    // יוצרת את התפריט מתוך הקובץ main_menu.xml ומכניסה אותו למסך
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;  // מחזירה true כדי להראות את התפריט
    }

  //  כשמסך כלשהו שירש מ־BaseActivity נפתח, ייטען תפריט אוטומטית.
  ///  התפריט הזה מוגדר בקובץ res/menu/main_menu.xml.
  //  כל פעם כשלוחצים על כפתור תפריט – זה יטופל בפונקציה למטה

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();   // מזהה את הפריט שבתפריט שנלחץ

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
        else if (id == R.id.menu_logOut) {
            AuthenticationService.getInstance().signOut();   // ביצוע התנתקות מהמשתמש
            startActivity(new Intent(this, MainActivity.class));

            return true;
        }
        else if (id == R.id.menu_MyFavRes) {

            startActivity(new Intent(this, MyFavRes.class));

            return true;
        }




        return super.onOptionsItemSelected(item);  // הפנייה להתנהגות ברירת המחדל
    }


    //BaseActivity היא מחלקת בסיס משותפת שכל המסכים שלך יורשים ממנה
    // היא דואגת שיהיה תפריט אחיד למשתמש בכל מסך, עם קישורים נוחים לבית, פרופיל, חיפוש, התנתקות, ועוד.
   // כשנלחץ פריט בתפריט – הקוד מפעיל את המסך המתאים באמצעות Intent.

}

