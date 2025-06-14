package com.noa.eatandshare.screens;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.User;
import com.noa.eatandshare.services.AuthenticationService;
import com.noa.eatandshare.services.DatabaseService;
import com.noa.eatandshare.utils.SharedPreferencesUtil;


//דף זה אחראי לרישום משתמש חדש לאפליקציה.
// המשתמש ממלא פרטים (שם, טלפון, אימייל, סיסמה), ולאחר אימות תקינות – נשלחת בקשת רישום לחשבון ולמסד הנתונים.


public class register extends BaseActivity implements View.OnClickListener {

  EditText etFname, etLname, etPhone, etEmail, etPassword;
   String fname,lname,phone,email,password;
   Button btnReg;


    private static final String TAG = "RegisterActivity";


    private AuthenticationService authenticationService;
    private DatabaseService databaseService;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        /// get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();

        initViews();




    }

    private void initViews() {
        etFname=findViewById(R.id.etFname);

        etLname=findViewById(R.id.etLname);

        etPhone=findViewById(R.id.etPhone);

        etEmail=findViewById(R.id.etEmail);

        etPassword=findViewById(R.id.etPassword);

        btnReg=findViewById(R.id.btnReg);
        btnReg.setOnClickListener(this);




    }


    @Override
    public void onClick(View v) {
        fname=etFname.getText().toString();
        lname=etLname.getText().toString();
        phone=etPhone.getText().toString();
        email=etEmail.getText().toString();
        password=etPassword.getText().toString();

        // בדיקות תקינות בסיסיות לפני רישום
        Boolean isValid=true;
        if (fname.length()<2){
            Toast.makeText(register.this,"שם פרטי קצר מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (lname.length()<2){
            Toast.makeText(register.this,"שם משפחה קצר מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (phone.length()<9||phone.length()>10){
            Toast.makeText(register.this,"מספר הטלפון לא תקין", Toast.LENGTH_LONG).show();
            isValid = false;
        }

        if (!email.contains("@")){
            Toast.makeText(register.this,"כתובת האימייל לא תקינה", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(password.length()<6){
            Toast.makeText(register.this,"הסיסמה קצרה מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(password.length()>20){
            Toast.makeText(register.this,"הסיסמה ארוכה מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }


        // אם כל הבדיקות תקינות - ממשיכים לרישום\
        if (isValid==true){
            registerUser(email, password, fname, lname, phone);



        }



    }


    // פונקציה לרישום המשתמש בפועל
    private void registerUser(String email, String password, String fName, String lName, String phone) {
        Log.d(TAG, "registerUser: Registering user...");

        // שלב 1: יצירת חשבון משתמש (ב-Firebase Auth)
        authenticationService.signUp(email, password, new AuthenticationService.AuthCallback<String>() {


            @Override
            public void onCompleted(String uid) {
                Log.d(TAG, "onCompleted: User registered successfully");
                // שלב 2: יצירת אובייקט משתמש
                User user = new User();
                user.setId(uid);
                user.setEmail(email);
                user.setPassword(password);
                user.setFname(fName);
                user.setLname(lName);
                user.setPhone(phone);

                // שלב 3: שמירת המשתמש במסד הנתונים
                databaseService.createNewUser(user, new DatabaseService.DatabaseCallback<Void>() {

                    @Override
                    public void onCompleted(Void object) {
                        Log.d(TAG, "onCompleted: User registered successfully");
                        // שמירה באחסון מקומי (SharedPreferences)
                        SharedPreferencesUtil.saveUser(register.this, user);
                        Log.d(TAG, "onCompleted: Redirecting to MainActivity");
                        /// Redirect to MainActivity and clear back stack to prevent user from going back to register screen
                        Intent mainIntent = new Intent(register.this, HomePage.class);
                        /// clear the back stack (clear history) and start the MainActivity
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);

                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.e(TAG, "onFailed: Failed to register user", e);
                        /// show error message to user
                        Toast.makeText(register.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                        /// sign out the user if failed to register
                        /// this is to prevent the user from being logged in again
                        authenticationService.signOut();
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: Failed to register user", e);
                /// show error message to user
                Toast.makeText(register.this, "Failed to register user", Toast.LENGTH_SHORT).show();
            }
        });


    }



}