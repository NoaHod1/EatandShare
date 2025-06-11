package com.noa.eatandshare.screens;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.services.DatabaseService;
import com.noa.eatandshare.utils.ImageUtil;

public class EditRes extends AppCompatActivity {


    private EditText etRestaurantName, etRestaurantstreet, etRestaurantDetails, etDomain;
    private Spinner spResType, spCity;
    private Switch swIsKosher;
    private Button btnEditRestaurant, btnGallery, btnCamera;
    private ImageView ivRes;

    TextView tvCity;


    private static final String TAG = "AddRestaurantActivity";


    private DatabaseService databaseService;

    /// Activity result launcher for selecting image from gallery
    private ActivityResultLauncher<Intent> selectImageLauncher;
    /// Activity result launcher for capturing image from camera
    private ActivityResultLauncher<Intent> captureImageLauncher;


    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    private Intent takeit;
    private Restaurant restaurant = null;
    private EditText etPhone;
    private TextView tvResType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_res2);
        // אתחול כל אחד מהאלמנטים ב-UI
        etRestaurantName = findViewById(R.id.etRestaurantNameE2);
        etRestaurantstreet = findViewById(R.id.etRestaurantstreetE2);
        etRestaurantDetails = findViewById(R.id.etRestaurantDetailsE2);
        spResType = findViewById(R.id.spResTypeE2);
        spCity = findViewById(R.id.spCityE2);
        swIsKosher = findViewById(R.id.swIsKosherE2);
        btnEditRestaurant = findViewById(R.id.btnEditRestaurantE2);
        ivRes = findViewById(R.id.ivResE2);
        btnGallery = findViewById(R.id.btnGalleryE2);
        btnCamera = findViewById(R.id.btnCameraE2);
        etDomain = findViewById(R.id.etDomainE2);
        etPhone = findViewById(R.id.etPhoneE2);
        tvCity=findViewById(R.id.tvCityE2);
        tvResType=findViewById(R.id.tvResTypeE2);


        /// request permission for the camera and storage
        ImageUtil.requestPermission(this);

        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();


        takeit = getIntent();
        restaurant = (Restaurant) takeit.getSerializableExtra("Rest");

        setDataFields();


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
                if( position>0)
                         tvResType.setText(selectedType);
                Toast.makeText(EditRes.this, "בחרת סוג: " + selectedType, Toast.LENGTH_SHORT).show();
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
                tvCity.setText(selectedCity);
                Toast.makeText(EditRes.this, "בחרת עיר: " + selectedCity, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // פעולה כשאין בחירה
            }
        });

        // פעולה כשנלחץ כפתור "הוספת מסעדה"
        btnEditRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRestaurant();
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


    private void setDataFields() {

        if (restaurant != null) {

        tvResType.setText(restaurant.getType());

            tvCity.setText(restaurant.getCity());
          etDomain.setText(restaurant.getDomain()+"");
            // tvKosher.setText(restaurant.get());
            //  et.setText(restaurant.getType());
            etRestaurantName.setText(restaurant.getName());

            etRestaurantDetails.setText(restaurant.getDetails());


        if (restaurant.isKosher()) swIsKosher.setText("כשרה");
        else swIsKosher.setText("לא כשרה");

            if (restaurant.getPic() != null) {
                Bitmap bitmap = ImageUtil.convertFrom64base(restaurant.getPic());
                ivRes.setImageBitmap(bitmap);
            }
        }
    }


    // פונקציה להוספת מסעדה
    private void updateRestaurant() {
        // קריאת נתונים מהשדות
        String restaurantName = etRestaurantName.getText().toString().trim();
        String restaurantStreet = etRestaurantstreet.getText().toString().trim();
        String restaurantDetails = etRestaurantDetails.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        String domain = etDomain.getText().toString();

        // בחירת סוג המסעדה (Spinner)
        String resType = tvResType.getText().toString();

        // בחירת עיר (Spinner)
        String city = tvCity.getText().toString();

        // ערך של ה-Switch כשר
        boolean isKosher = swIsKosher.isChecked();
        phone = etPhone.getText().toString();

        // אם יש נתונים חסרים, הצג הודעת שגיאה
        if (restaurantName.isEmpty() || restaurantStreet.isEmpty() || restaurantDetails.isEmpty()) {
            Toast.makeText(this, "נא למלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageBase64 = ImageUtil.convertTo64Base(ivRes);


        /// generate a new id for the food
        String id = databaseService.generateRestaurantId();


        restaurant.setCity(city);
        restaurant.setName(restaurantName);
        restaurant.setStreet(restaurantStreet);
        restaurant.setType(resType);
        restaurant.setDetails(restaurantDetails);
        restaurant.setKosher(isKosher);
        restaurant.setPic(imageBase64);
        restaurant.setPhone(phone);


        /// create a new food object
        restaurant = new Restaurant(id, restaurantName, city, restaurantStreet, resType, restaurantDetails, 0, isKosher, 0.0F, 0.0F, domain, imageBase64, "0521234567");

        /// save the food to the database and get the result in the callback
        databaseService.updateRestaurant(restaurant, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                Log.d(TAG, "Food added successfully");
                Toast.makeText(EditRes.this, "Food added successfully", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditRes.this, "Failed to add food", Toast.LENGTH_SHORT).show();
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
