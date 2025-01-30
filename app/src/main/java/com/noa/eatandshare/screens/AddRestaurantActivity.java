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

    private EditText etRestaurantName, etRestaurantstreet, etRestaurantDetails,etDomain;
    private Spinner spResType, spCity;
    private Switch swIsKosher;
    private Button btnAddRestaurant, btnGallery, btnCamera;
    private ImageView ivRes;


    private static final String TAG = "AddRestaurantActivity";


    private DatabaseService databaseService;

    /// Activity result launcher for selecting image from gallery
    private ActivityResultLauncher<Intent> selectImageLauncher;
    /// Activity result launcher for capturing image from camera
    private ActivityResultLauncher<Intent> captureImageLauncher;


    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;


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
        etDomain=findViewById(R.id.etDomain);


        /// request permission for the camera and storage
        ImageUtil.requestPermission(this);

        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();




        /// register the activity result launcher for selecting image from gallery
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        ivRes.setImageURI(selectedImage);
                    }
                });

        /// register the activity result launcher for capturing image from camera
        captureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        ivRes.setImageBitmap(bitmap);
                    }
                });






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
                selectImageFromGallery();
                return;

            }
        });

        // פעולה כשנלחץ כפתור "צלם תמונה"
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // capture image from camera
                Log.d(TAG, "Capture image button clicked");
                captureImageFromCamera();
                return;

            }
        });
    }

    // פונקציה להוספת מסעדה
    private void addRestaurant() {
        // קריאת נתונים מהשדות
        String restaurantName = etRestaurantName.getText().toString().trim();
        String restaurantStreet = etRestaurantstreet.getText().toString().trim();
        String restaurantDetails = etRestaurantDetails.getText().toString().trim();

        String domain=etDomain.getText().toString();

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

        String imageBase64 = ImageUtil.convertTo64Base(ivRes);




        /// generate a new id for the food
        String id = databaseService.generateRestaurantId();





            /// create a new food object
        Restaurant restaurant = new Restaurant(id, restaurantName, city, restaurantStreet, resType,restaurantDetails, 0,isKosher,0.0,0.0,domain,imageBase64);

        /// save the food to the database and get the result in the callback
        databaseService.createNewRestaurant(restaurant, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                Log.d(TAG, "Food added successfully");
                Toast.makeText(AddRestaurantActivity.this, "Food added successfully", Toast.LENGTH_SHORT).show();
                /// clear the input fields after adding the food for the next food
                Log.d(TAG, "Clearing input fields");

                // הצגת ההודעה על המסך למשתמש
               // Toast.makeText(this, "המסעדה נוספה בהצלחה!", Toast.LENGTH_SHORT).show();

                // הדפסת הערכים לקונסול (Log) לצורך דיבוג
                System.out.println("Restaurant Name: " + restaurantName);
                System.out.println("Restaurant Street: " + restaurantStreet);
                System.out.println("Restaurant Details: " + restaurantDetails);
                System.out.println("Restaurant Type: " + resType);
                System.out.println("City: " + city);
                System.out.println("Is Kosher: " + isKosher);

                finish();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to add food", e);
                Toast.makeText(AddRestaurantActivity.this, "Failed to add food", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // טיפול בתוצאה של הגלריה או המצלמה


    /// select image from gallery
    private void selectImageFromGallery() {
        //   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //  selectImageLauncher.launch(intent);

        imageChooser();
    }

    /// capture image from camera
    private void captureImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageLauncher.launch(takePictureIntent);
    }




    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    ivRes.setImageURI(selectedImageUri);
                }
            }
        }
    }

}










