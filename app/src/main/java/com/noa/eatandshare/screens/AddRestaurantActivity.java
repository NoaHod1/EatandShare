package com.noa.eatandshare.screens;

import static android.opengl.ETC1.isValid;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.noa.eatandshare.R;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.services.DatabaseService;
import com.noa.eatandshare.utils.ImageUtil;

import java.io.IOException;


public class AddRestaurantActivity extends AppCompatActivity {

    private EditText etRestaurantName, etRestaurantstreet, etRestaurantDetails;
    private Spinner spResType, spCity;
    private Switch swIsKosher;
    private Button btnAddRestaurant, btnGallery, btnCamera;
    private ImageView ivRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        // אתחול כל אחד מהאלמנטים ב-UI
        etRestaurantName = findViewById(R.id.etRestaurantName);
        etRestaurantstreet = findViewById(R.id.etRestaurantstreet);
        etRestaurantDetails = findViewById(R.id.etRestaurantDetails);
        spResType = findViewById(R.id.spResType);
        spCity = findViewById(R.id.spCity);
        swIsKosher = findViewById(R.id.swIsKosher);
        btnAddRestaurant = findViewById(R.id.btnAddRestaurant);
        ivRes = findViewById(R.id.ivRes);
        btnGallery = findViewById(R.id.btnGallery);
        btnCamera = findViewById(R.id.btnCamera);

        // יצירת ArrayAdapter עבור סוגי המסעדות
        ArrayAdapter<CharSequence> resTypeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.restaurant_types,
                android.R.layout.simple_spinner_item
        );
        resTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spResType.setAdapter(resTypeAdapter);

        // יצירת ArrayAdapter עבור הערים
        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.cityArr,
                android.R.layout.simple_spinner_item
        );
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity.setAdapter(cityAdapter);

        // פעולה כשנבחר סוג המסעדה
        spResType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // פעולה כשנבחרת אפשרות חדשה בסוג המסעדה
                String selectedType = spResType.getSelectedItem().toString();
                Toast.makeText(AddRestaurantActivity.this, "בחרת סוג: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // פעולה כשאין בחירה
            }
        });

        // פעולה כשנבחרה עיר
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // פעולה כשנבחרת עיר
                String selectedCity = spCity.getSelectedItem().toString();
                Toast.makeText(AddRestaurantActivity.this, "בחרת עיר: " + selectedCity, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // פעולה כשאין בחירה
            }
        });

        // פעולה כשנלחץ כפתור "הוספת מסעדה"
        btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRestaurant();
            }
        });

        // פעולה כשנלחץ כפתור "גלריה"
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // פעולה כשנלחץ כפתור "צלם תמונה"
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    // פונקציה להוספת מסעדה
    private void addRestaurant() {
        // קריאת נתונים מהשדות
        String restaurantName = etRestaurantName.getText().toString().trim();
        String restaurantStreet = etRestaurantstreet.getText().toString().trim();
        String restaurantDetails = etRestaurantDetails.getText().toString().trim();

        // בחירת סוג המסעדה (Spinner)
        String resType = spResType.getSelectedItem().toString();

        // בחירת עיר (Spinner)
        String city = spCity.getSelectedItem().toString();

        // ערך של ה-Switch כשר
        boolean isKosher = swIsKosher.isChecked();

        // אם יש נתונים חסרים, הצג הודעת שגיאה
        if (restaurantName.isEmpty() || restaurantStreet.isEmpty() || restaurantDetails.isEmpty()) {
            Toast.makeText(this, "נא למלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        // הצגת ההודעה על המסך למשתמש
        Toast.makeText(this, "המסעדה נוספה בהצלחה!", Toast.LENGTH_SHORT).show();

        // הדפסת הערכים לקונסול (Log) לצורך דיבוג
        System.out.println("Restaurant Name: " + restaurantName);
        System.out.println("Restaurant Street: " + restaurantStreet);
        System.out.println("Restaurant Details: " + restaurantDetails);
        System.out.println("Restaurant Type: " + resType);
        System.out.println("City: " + city);
        System.out.println("Is Kosher: " + isKosher);

        // כאן אפשר להוסיף את הקוד לשמירה בבסיס נתונים או פעולה אחרת
    }

    // פונקציה לפתיחת הגלריה
    private void openGallery() {
        // יצירת Intent לבחירת תמונה מהגלריה
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    // פונקציה לפתיחת המצלמה
    private void openCamera() {
        // יצירת Intent לצילום תמונה
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }

    // טיפול בתוצאה של הגלריה או המצלמה
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // טיפול בתמונה מהגלריה
            Uri imageUri = data.getData();
            ivRes.setImageURI(imageUri);
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            // טיפול בתמונה מהמצלמה
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivRes.setImageBitmap(photo);
        }
    }
}









