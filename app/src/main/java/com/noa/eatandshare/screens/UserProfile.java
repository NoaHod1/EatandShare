package com.noa.eatandshare.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.User;
import com.noa.eatandshare.services.AuthenticationService;
import com.noa.eatandshare.services.DatabaseService;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    private TextView tvFirstName, tvLastName, tvPhone, tvEmail;

    String uid;
    User user=null;

    DatabaseService databaseService;



    Button btnGoEditUser,btnClosePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tvFirstName = findViewById(R.id.tvFName);
        tvLastName = findViewById(R.id.tvLName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEml);

        btnGoEditUser=findViewById(R.id.btnGoToEditUser);
        btnGoEditUser.setOnClickListener(this);

        btnClosePage=findViewById(R.id.btnClose);
        btnClosePage.setOnClickListener(this);


        uid= AuthenticationService.getInstance().getCurrentUserId();
        databaseService=DatabaseService.getInstance();




    databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {

        @Override
     public void onCompleted(User object) {
            user=object;


            if(user!=null){

                tvFirstName.setText("שם פרטי: " + user.getFname());
                tvLastName.setText("שם משפחה: " + user.getLname());
                tvPhone.setText("טלפון: " + user.getPhone());
                tvEmail.setText("אימייל: " + user.getEmail());





            }


        }

        @Override
       public void onFailed(Exception e) {

            tvFirstName.setText("");
            tvLastName.setText("שם משפחה: " );
            tvPhone.setText("טלפון: " );
            tvEmail.setText("אימייל: ");


        }
    });





    }

    @Override
    public void onClick(View v) {


        if(v==btnGoEditUser){
            Intent go=new Intent(UserProfile.this,EditUser.class);
            startActivity(go);
        }

        if(v==btnClosePage){
           finish();
        }

    }
}
