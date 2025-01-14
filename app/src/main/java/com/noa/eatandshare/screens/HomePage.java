package com.noa.eatandshare.screens;

import android.content.DialogInterface;
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

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}





//
//public class AfterLogIn extends AppCompatActivity implements View.OnClickListener {
//
//    TextView tvHello;
//    Button btnGoStore2, btnGoAddRes2, btnGoWishList, btnGoPersonal, btnGoDonation;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_after_log_in);
//
//
//        btnGoStore2 = findViewById(R.id.btnGoStore2);
//        // btnGoAddItem2=findViewById(R.id.btnGoAddItem2);
//        btnGoWishList = findViewById(R.id.btnGoMyCart);
//        btnGoPersonal = findViewById(R.id.btnGoPersonalArea);
//
//        //   if(Login.theUser != null)
//        //     tvHello.setText(" שלום "+Login.theUser.getfName());
//
//        btnGoStore2.setOnClickListener((View.OnClickListener) this);
//
//        btnGoWishList.setOnClickListener((View.OnClickListener) this);
//        btnGoPersonal.setOnClickListener((View.OnClickListener) this);
//
//        //     startService(new Intent(this, MyService.class));
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
////    @Override
////    public void onClick(View view) {
////        if(view==btnGoStore2){
////            stopService(new Intent(this, MyService.class));
//
////   /   Intent goStore=new Intent(this, GoSearchPage.class);
////   /   startActivity(goStore);
////        }
//    //  if(view==btnGoAddItem2){
//    //      Intent goAddItem=new Intent(this, AddItem.class);
//    //     startActivity(goAddItem);
//    //  }
////        if(view==btnGoWishList){
//    //   stopService(new Intent(this, MyService.class));
//
//    //    Intent goWishList=new Intent(this, Mycart.class);
//    //    startActivity(goWishList);
//    //     }
//    //  if(view==btnGoPersonal){
//    //      Intent goProfile=new Intent(this, UserProfile.class);
//    //     startActivity(goProfile);
////        }
//    //    if(view==btnGoDonation){
//    //       Intent goDonation=new Intent(this, DonationPage.class);
//    //        startActivity(goDonation);
//    //   }
//
//    //  }
//
////    public boolean onOptionsItemSelected(MenuItem menuitem) {
////        int itemid = menuitem.getItemId();
////        if (itemid == R.id.menuGoStore) {
////            Intent goadmin = new Intent(AfterLogIn.this, SearchRes.class);
////            startActivity(goadmin);
////        }
////           if (itemid == R.id.menuGoMyItems) {
////               Intent goadmin = new Intent(AfterLogin.this, MyItems.class);
////              startActivity(goadmin);
//    //         }
////        if (itemid == R.id.menuGoMyCart) {
////            Intent goadmin = new Intent(AfterLogIn.this, Mycart.class);
////            startActivity(goadmin);
////        }
////        if (itemid == R.id.menuGoPersonal) {
////            Intent goadmin = new Intent(AfterLogIn.this, UserProfile.class);
////            startActivity(goadmin);
////        }
////        if (itemid == R.id.menuGoAfterLogIn) {
////            Intent goadmin = new Intent(AfterLogIn.this, AfterLogIn.class);
////            startActivity(goadmin);
////        }
////
////        if (itemid == R.id.menuGoAdminPage) {
////            if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("golanaf@gmail.com")){
////                Intent go = new Intent(AfterLogIn.this, AdminPage.class);
////                startActivity(go);
////            }
////            else{
////                Toast.makeText(AfterLogIn.this,"עמוד זה למנהלים בלבד!", Toast.LENGTH_LONG).show();
////            }
////
////            if (itemid == R.id.menuGoAllOrders) {
////                if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("golanaf@gmail.com")){
////                    Intent go = new Intent(AfterLogInn.this, AllOrders.class);
////                    startActivity(go);
////                }
////                else{
////                    Toast.makeText(AfterLogIn.this,"עמוד זה למנהלים בלבד!", Toast.LENGTH_LONG).show();
////                }
////            }
////        }
//
//
////        if (itemid == R.id.menuGoLogOut) {
////            AlertDialog.Builder builder = new AlertDialog.Builder(AfterLogIn.this);
////            builder.setTitle("התנתקות");
////            builder.setMessage("אתה בטוח שאתה רוצה להתנתק?");
////            builder.setCancelable(true);
////            builder.setPositiveButton("כן, ברצוני להתנתק", new HandleAlertDialogListener2());
////            builder.setNegativeButton("לא, ברצוני להישאר מחובר", new HandleAlertDialogListener2());
////            AlertDialog dialog = builder.create();
////            dialog.show();
////            return true;
////        }
////
////        if (itemid == R.id.menuGoAfterLogin) {
////            Intent go = new Intent(AfterLogIn.this, AfterLogIn.class);
////            startActivity(go);
////        }
////
////        return true;
////
////    }
//
//
////    @SuppressLint("RestrictedApi")
////    public boolean onCreateOptionsMenu (Menu menu){
////        getMenuInflater().inflate(R.menu.main_menu, menu);
////        if (menu instanceof MenuBuilder) {
////            MenuBuilder m = (MenuBuilder) menu;
////            m.setOptionalIconsVisible(true);
////        }
////        return true;
////    }
//
//
//    public class HandleAlertDialogListener2 implements DialogInterface.OnClickListener, com.noa.eatandshare.screens.HandleAlertDialogListener2 {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            if (which == -1) //user wants to log out
//            {
//                FirebaseAuth.getInstance().signOut();
//                //     Intent go = new Intent(AfterLogIn.this, MainActivity.class);
//                //   startActivity(go);
//            }
//        }
//    }
//}

