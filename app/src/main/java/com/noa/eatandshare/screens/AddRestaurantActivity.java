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
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.services.DatabaseService;
import com.noa.eatandshare.utils.ImageUtil;


public class AddRestaurantActivity extends BaseActivity {


    // הגדרת שדות הקלט, תפריטים נפתחים, כפתורים ותמונה במסך
    private EditText etRestaurantName, etRestaurantstreet, etRestaurantDetails,etDomain;
    private Spinner spResType, spCity;
    private Switch swIsKosher;
    private Button btnAddRestaurant, btnGallery, btnCamera;
    private ImageView ivRes;

    // משתנה קבוע עבור לוגים (מעקב בקונסול)
    private static final String TAG = "AddRestaurantActivity";

    // מופע של שירות מסד הנתונים
    private DatabaseService databaseService;

    // כלים שמאפשרים להפעיל פעולות ולקבל תוצאה חזרה - בחירת תמונה מהגלריה או צילום מהמצלמה
    private ActivityResultLauncher<Intent> selectImageLauncher;
    private ActivityResultLauncher<Intent> captureImageLauncher;


    // מזהה קבוע להשוואה כשבוחרים תמונה מהגלריה
    int SELECT_PICTURE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // מזהה קבוע להשוואה כשבוחרים תמונה מהגלריה
        setContentView(R.layout.activity_add_restaurant);

        // קישור של כל רכיב במסך למשתנה בקוד//אתחול
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


        // בקשת הרשאות לגישה למצלמה ולקבצים במכשיר
        ImageUtil.requestPermission(this);

        // קבלת מופע של שירות בסיס הנתונים
        databaseService = DatabaseService.getInstance();




        // הגדרת הפעולה כשנבחרת תמונה מהגלריה
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        ivRes.setImageURI(selectedImage);  // הצגת התמונה במסך
                    }
                });

        // הגדרת הפעולה כשמצלמים תמונה עם המצלמה
        captureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        ivRes.setImageBitmap(bitmap);  // הצגת התמונה במסך
                    }
                });





        // בניית רשימת סוגי מסעדות לספינר
        // יצירת ArrayAdapter עבור סוגי המסעדות
        ArrayAdapter<CharSequence> resTypeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.restaurant_types,
                android.R.layout.simple_spinner_item
        );
        resTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spResType.setAdapter(resTypeAdapter);

        // בניית רשימת ערים לספינר
        // יצירת ArrayAdapter עבור הערים
        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.cityArr,
                android.R.layout.simple_spinner_item
        );
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity.setAdapter(cityAdapter);

        // האזנה לבחירת סוג מסעדה
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
                // לא קרה כלום אם לא נבחר
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
                // לא קרה כלום אם לא נבחר
            }
        });

        //בעת לחיצה על "הוסף מסעדה " – לקרוא לפונקציה addRestaurant
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

    // פונקציה שמוסיפה מסעדה למסד הנתונים
    // פונקציה להוספת מסעדה
    private void addRestaurant() {
        // שליפת הנתונים מהשדות
        String restaurantName = etRestaurantName.getText().toString().trim()+"";
        String restaurantStreet = etRestaurantstreet.getText().toString().trim()+"";
        String restaurantDetails = etRestaurantDetails.getText().toString().trim()+"";

        String domain=etDomain.getText().toString()+"";

        // בחירת סוג המסעדה (Spinner)
        String resType = spResType.getSelectedItem().toString()+"";

        // בחירת עיר (Spinner)
        String city = spCity.getSelectedItem().toString()+"";

        // ערך של ה-Switch כשר
        boolean isKosher = swIsKosher.isChecked();


        // אם יש נתונים חסרים, הצג הודעת שגיאה
        if (restaurantName.isEmpty() || restaurantStreet.isEmpty() || restaurantDetails.isEmpty()) {
            Toast.makeText(this, "נא למלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        // המרת התמונה לבסיס64 לשמירה במסד הנתונים
        String imageBase64 = ImageUtil.convertTo64Base(ivRes);




        // יצירת מזהה ייחודי למסעדה
        String id = databaseService.generateRestaurantId();



        // יצירת אובייקט של מסעדה עם כל המידע
        Restaurant restaurant = new Restaurant(id, restaurantName, city, restaurantStreet, resType,restaurantDetails, 0,isKosher, 0.0F, 0.0F,domain,imageBase64,"0521234567");

        // שליחה למסד הנתונים
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

                finish(); // סיום הפעילות – חזרה למסך הקודם

            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to add food", e);
                Toast.makeText(AddRestaurantActivity.this, "Failed to add food", Toast.LENGTH_SHORT).show();
            }
        });

    }



    // פתיחת בורר תמונה
    /// select image from gallery
    private void selectImageFromGallery() {


        imageChooser(); // פונקציה שפותחת את הגלריה
    }

    // פתיחת מצלמה
    private void captureImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageLauncher.launch(takePictureIntent);
    }



// פונקציה לפתיחת בורר התמונות (הגלריה)

    void imageChooser() {

//יצירת אינטנט לצורך בחירת תמונה מהגלרי ה
        Intent i = new Intent();

        // הגדרת סוג התוכן שהאינטנט יתמוך בו- תמונות
        i.setType("image/*");

        // פעולה שמאפשרת לבחור תוכן (תמונה) מהמכשיר
        i.setAction(Intent.ACTION_GET_CONTENT);

        // הפעלת הפעולה ובקשה שהמשתמש יבחר תמונה, עם טקסט שמוצג לו לבחירה
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        // SELECT_PICTURE הוא מספר מזהה שנשתמש בו כדי לדעת איזה פעולה חזרה אלינו בתוצאה

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // קריאה לגרסת האב של הפונקציה כדי לשמור על פונקציונליות בסיסית

        super.onActivityResult(requestCode, resultCode, data);

        // בדיקה שהתוצאה הייתה תקינה (שהמשתמש באמת בחר משהו ולא ביטל)
        if (resultCode == RESULT_OK) {

            // בדיקה אם הקוד שחזר הוא זה של בחירת תמונה מהגלריה
            if (requestCode == SELECT_PICTURE) {
                // שליפת כתובת ה-URI של התמונה מתוך הנתונים שהתקבלו
                Uri selectedImageUri = data.getData();

                // אם יש URI תקף (כלומר המשתמש באמת בחר תמונה)
                if (null != selectedImageUri) {
                    // הצגת התמונה בתיבת התמונה במסך (ivRes)
                    ivRes.setImageURI(selectedImageUri);
                }
            }
        }
    }

}










