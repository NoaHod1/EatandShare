package com.noa.eatandshare.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.noa.eatandshare.R;

public class AddRestaurantActivity extends AppCompatActivity {


    EditText etRestaurantName1,etRestaurantstreet1,etRestaurantDetails1;
    Button btnGallery1,btnCamera1,btnAddRestaurant1;
    ImageView ivRes1;
    Spinner spResType1,spCity1;
    Switch swIsKosher;
    String RestaurantName, Restaurantstreet, type;


    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

private static final int GALLERY_INTENT=2;


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
        btnGallery1=findViewById(R.id.btnGallery);
        btnCamera1=findViewById(R.id.btnCamera);

        etRestaurantstreet1=findViewById(R.id.etRestaurantstreet);

        etRestaurantDetails1=findViewById(R.id.etRestaurantDetails);

        etRestaurantName1=findViewById(R.id.etRestaurantName);


        ivRes1=findViewById(R.id.ivRes);


        spResType1=findViewById(R.id.spResType);
        spCity1=findViewById(R.id.spCity);


    }


    @Override


    public void onClick(View view) {

        if(view==btnCamera1)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);

        }
        if(view==btnGallery1) {

            Intent intent2=new Intent(Intent.ACTION_PICK);
            intent2.setType("image/*");
            startActivityForResult(intent2,GALLERY_INTENT);

        }
        if(view==btnAddRestaurant1)
        {

            type=spResType1.getSelectedItem().toString();


            dedc=etRestaurantDetails1.getText().toString();


            itemName=etRestaurantName1.getText()+"";

            stStreet=etRestaurantstreet1.getText().toString();



            if (bitmap != null) {

                //  uid ="thisisUid"; //FirebaseAuth.getInstance().getCurrentUser().getUid();

                String itemid=myRef.getKey().toString();
                imageRef="gs://macroorder-508b4.appspot.com/"+itemid;

                Item newItem= new Item(itemid,itemName,type,imageRef, dedc,price);



                //  item1.setImageRef("gs://who-needed.appspot.com\n"+item1.getItemKey()+"");
                myRef.setValue(newItem);

                HandleImage.LoadImageFile(bitmap, AddItem.this, itemid);
                //startActivity(intent2);

                Intent go=new Intent(this,AdminPage.class);
                startActivity(go);

            } else {
                Toast.makeText(AddItem.this, "Please take pic!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 0)//coming from camera
            {
                if (resultCode == RESULT_OK) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    iv.setImageBitmap(bitmap);
                }
            }

            if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK && data !=null) {
                Uri uri = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));
                    iv.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            int id=item.getItemId();
            if(id==R.id.menuGoStore){
                Intent go=new Intent(this,AfterLogin.class);
                startActivity(go);

            }

            if(id==R.id.menuGoPersonal){
                Intent go=new Intent(this,UserProfile.class);

                // go.putExtra("nameList",listNames);
                startActivity(go);

            }

            if(id==R.id.menuGoMyCart) {
                Intent go=new Intent(this,Mycart.class);

                startActivity(go);


            }
            if(id==R.id.menuGoAboutUs) {
                Intent go=new Intent(this,AboutUs.class);

                startActivity(go);


            }

            if(id==R.id.menuGoAfterLogin){
                Intent go=new Intent(this,AfterLogin.class);
                startActivity(go);

            }
            if(id==R.id.menuGoAllOrders){
                Intent go=new Intent(this,AllOrders.class);
                startActivity(go);

            }
            return super.onOptionsItemSelected(item);
        }

//    public boolean onOptionsItemSelected(MenuItem menuitem) {
//        int itemid = menuitem.getItemId();
//        if (itemid == R.id.menuGoStore) {
//            Intent goadmin = new Intent(AddItem.this, SearchItem.class);
//            startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoAddItem) {
//            Intent goadmin = new Intent(AddItem.this, AddItem.class);
//            startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoWishList) {
//            Intent goadmin = new Intent(AddItem.this, WishList.class);
//            startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoPersonal) {
//             Intent goadmin = new Intent(AddItem.this, UserProfile.class);
//             startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoAboutUs) {
//            Intent goadmin = new Intent(AddItem.this, AboutUs.class);
//            startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoAfterLogin) {
//            Intent goadmin = new Intent(AddItem.this, AfterLogin.class);
//            startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoAdminPage) {
//            String admin = "edenkario@gmail.com";
//            if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(admin)){
//                Intent go = new Intent(AddItem.this, AdminPage.class);
//                startActivity(go);
//            }
//            else{
//                Toast.makeText(AddItem.this,"עמוד זה למנהלים בלבד!", Toast.LENGTH_LONG).show();
//            }
//        }
//
//        return super.onOptionsItemSelected(menuitem);
//    }
//
//
//    @SuppressLint("RestrictedApi")
//    public boolean onCreateOptionsMenu (Menu menu){
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        if (menu instanceof MenuBuilder) {
//            MenuBuilder m = (MenuBuilder) menu;
//            m.setOptionalIconsVisible(true);
//        }
//        return true;
//    }








    }
}