package com.noa.eatandshare.screens;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;

public class AddRestaurantActivity extends AppCompatActivity implements View.OnClickListener {


    EditText etRestaurantName1,etRestaurantstreet1,etRestaurantDetails1;
    Button btnGallery1,btnCamera1,btnAddRestaurant1;
    ImageView ivRes1;
    Spinner spResType1,spCity1;
    Switch swIsKosher;
    String RestaurantName, Restaurantstreet, etRestaurantDetails;
    String imageRef;
    String dedc;



    Bitmap bitmap;



    Button btnGallery,btnCamera, btnAddRestaurant;
    ImageView iv;
    public static final int GALLERY_INTENT=2;


    /// Activity result launcher for capturing image from camera
    private ActivityResultLauncher<Intent> captureImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_restaurant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
    private void initViews() {




        btnAddRestaurant1=findViewById(R.id.btnAddRestaurant);
        btnAddRestaurant.setOnClickListener(this);

        btnGallery1=findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(this);

        btnCamera1=findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(this);


        etRestaurantstreet1=findViewById(R.id.etRestaurantstreet);

        etRestaurantDetails1=findViewById(R.id.etRestaurantDetails);

        etRestaurantName1=findViewById(R.id.etRestaurantName);


        ivRes1=findViewById(R.id.ivRes);



        spResType1=findViewById(R.id.spResType);
        spCity1=findViewById(R.id.spCity);


        /// register the activity result launcher for capturing image from camera
        //captureImageLauncher = registerForActivityResult(
           //     new ActivityResultContracts.StartActivityForResult(),
              //  result -> {
               //     if (result.getResultCode() == RESULT_OK && result.getData() != null) {
               //         Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                 //       ImageView.setImageBitmap(bitmap);
               //     }
              //  });


    }


    @Override
    public void onClick(View view) {

        if(view==btnCamera1)
        {


        }
       if(view==btnGallery1) {

            //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //selectImageLauncher.launch(intent);
        }
        if(view==btnAddRestaurant1)
        {

//
//
//          //  String stStreet = etRestaurantstreet1.getText().toString();
//
//
//
//           // if (bitmap != null) {
//
//                //  uid ="thisisUid"; //FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//                String itemid=myRef.getKey().toString();
//                imageRef="gs://macroorder-508b4.appspot.com/"+itemid;
//
//                Item newItem= new Item(itemid,itemName,type,imageRef);
//
//
//
//                //  item1.setImageRef("gs://who-needed.appspot.com\n"+item1.getItemKey()+"");
//                myRef.setValue(newItem);
//
//               // HandleImage.LoadImageFile(bitmap, AddRestaurantActivity.this, Itemid);
//                //startActivity(intent2);
//
//                Intent go=new Intent(this,AdminPage.class);
//                startActivity(go);

        }
        else {
            Toast.makeText(AddRestaurantActivity.this, "Please take pic!", Toast.LENGTH_SHORT).show();
        }


        if(view==btnCamera1)
        {


        }
        if(view==btnGallery1) {

            //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //selectImageLauncher.launch(intent);
        }
        if(view==btnAddRestaurant1)
        {



            //  String stStreet = etRestaurantstreet1.getText().toString();



            // if (bitmap != null) {

            //  uid ="thisisUid"; //FirebaseAuth.getInstance().getCurrentUser().getUid();

//            String itemid=myRef.getKey().toString();
//            imageRef="gs://macroorder-508b4.appspot.com/"+itemid;

//            Item newItem= new Item(itemid,itemName,type,imageRef);



            //  item1.setImageRef("gs://who-needed.appspot.com\n"+item1.getItemKey()+"");
//            myRef.setValue(newItem);

            // HandleImage.LoadImageFile(bitmap, AddRestaurantActivity.this, Itemid);
            //startActivity(intent2);

//            Intent go=new Intent(this,AdminPage.class);
//            startActivity(go);

 //       } else {
//            Toast.makeText(AddRestaurantActivity.this, "Please take pic!", Toast.LENGTH_SHORT).show();
        }
    }


}









