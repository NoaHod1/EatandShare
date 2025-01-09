package com.noa.eatandshare.screens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;

public class AfterLogIn extends AppCompatActivity {

    TextView tvHello;
    Button btnGoStore2, btnGoAddItem2, btnGoWishList, btnGoPersonal, btnGoDonation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_after_log_in);


        btnGoStore2=findViewById(R.id.btnGoStore2);
        // btnGoAddItem2=findViewById(R.id.btnGoAddItem2);
        btnGoWishList=findViewById(R.id.btnGoMyCart);
        btnGoPersonal=findViewById(R.id.btnGoPersonalArea);

        //   if(Login.theUser != null)
        //     tvHello.setText(" שלום "+Login.theUser.getfName());

        btnGoStore2.setOnClickListener(this);

        btnGoWishList.setOnClickListener(this);
        btnGoPersonal.setOnClickListener(this);

        startService(new Intent(this, MyService.class));

    }

    @Override
    public void onClick(View view) {
        if(view==btnGoStore2){
            stopService(new Intent(this, MyService.class));

            Intent goStore=new Intent(this, SearchItem.class);
            startActivity(goStore);
        }
        //  if(view==btnGoAddItem2){
        //      Intent goAddItem=new Intent(this, AddItem.class);
        //     startActivity(goAddItem);
        //  }
        if(view==btnGoWishList){
            stopService(new Intent(this, MyService.class));

            Intent goWishList=new Intent(this, Mycart.class);
            startActivity(goWishList);
        }
        if(view==btnGoPersonal){
            Intent goProfile=new Intent(this, UserProfile.class);
            startActivity(goProfile);
        }
        //    if(view==btnGoDonation){
        //       Intent goDonation=new Intent(this, DonationPage.class);
        //        startActivity(goDonation);
        //   }

    }

    public boolean onOptionsItemSelected(MenuItem menuitem) {
        int itemid = menuitem.getItemId();
        if (itemid == R.id.menuGoStore) {
            Intent goadmin = new Intent(AfterLogin.this, SearchItem.class);
            startActivity(goadmin);
        }
        //   if (itemid == R.id.menuGoMyItems) {
        //       Intent goadmin = new Intent(AfterLogin.this, MyItems.class);
        //      startActivity(goadmin);
        //  }
        if (itemid == R.id.menuGoMyCart) {
            Intent goadmin = new Intent(AfterLogin.this, Mycart.class);
            startActivity(goadmin);
        }
        if (itemid == R.id.menuGoPersonal) {
            Intent goadmin = new Intent(AfterLogin.this, UserProfile.class);
            startActivity(goadmin);
        }
        if (itemid == R.id.menuGoAfterLogin) {
            Intent goadmin = new Intent(AfterLogin.this, AfterLogin.class);
            startActivity(goadmin);
        }

        if (itemid == R.id.menuGoAdminPage) {
            if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("golanaf@gmail.com")){
                Intent go = new Intent(AfterLogin.this, AdminPage.class);
                startActivity(go);
            }
            else{
                Toast.makeText(AfterLogin.this,"עמוד זה למנהלים בלבד!", Toast.LENGTH_LONG).show();
            }

            if (itemid == R.id.menuGoAllOrders) {
                if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("golanaf@gmail.com")){
                    Intent go = new Intent(AfterLogin.this, AllOrders.class);
                    startActivity(go);
                }
                else{
                    Toast.makeText(AfterLogin.this,"עמוד זה למנהלים בלבד!", Toast.LENGTH_LONG).show();
                }
            }
        }




        if (itemid == R.id.menuGoLogOut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AfterLogin.this);
            builder.setTitle("התנתקות");
            builder.setMessage("אתה בטוח שאתה רוצה להתנתק?");
            builder.setCancelable(true);
            builder.setPositiveButton("כן, ברצוני להתנתק", new HandleAlertDialogListener2());
            builder.setNegativeButton("לא, ברצוני להישאר מחובר", new HandleAlertDialogListener2());
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        if (itemid == R.id.menuGoAfterLogin) {
            Intent go = new Intent(AfterLogin.this, AfterLogin.class);
            startActivity(go);
        }

        return true;

    }
    private class HandleAlertDialogListener2 implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            if(which==-1) //user wants to log out
            {
                FirebaseAuth.getInstance().signOut();
                Intent go = new Intent(AfterLogin.this, MainActivity.class);
                startActivity(go);
            }
        }
    }


    @SuppressLint("RestrictedApi")
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }



}