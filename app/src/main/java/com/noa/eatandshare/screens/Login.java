package com.noa.eatandshare.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.User;
import com.noa.eatandshare.services.AuthenticationService;
import com.noa.eatandshare.services.DatabaseService;
import com.noa.eatandshare.utils.SharedPreferencesUtil;

public class Login extends BaseActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button btnLog;
    String email, pass;
    AuthenticationService authenticationService;




    String admin = "noa272007@gmail.com";
    String passadmin = "272007";


    public static boolean isAdmin = false;

    User user=null;

    DatabaseService databaseService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseService=DatabaseService.getInstance();
        authenticationService=AuthenticationService.getInstance();


        initViews();
        user= SharedPreferencesUtil.getUser(Login.this);
        if(user!=null) {

            email = user.getEmail();
            pass = user.getPassword();
            etEmail.setText(email);
            etPassword.setText(pass);
        }
    }


    private void initViews() {
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLog = findViewById(R.id.btnLogin);
        btnLog.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        email = etEmail.getText().toString();
        pass = etPassword.getText().toString();

        //בדיקות תקינות


        authenticationService.signIn(email, pass, new AuthenticationService.AuthCallback<String>() {



            @Override
            public void onCompleted(String id) {
                Log.d("TAG", "signInWithEmail:success");


                databaseService.getUser(id, new DatabaseService.DatabaseCallback<User>() {
                    @Override
                   public void onCompleted(User user2) {

                        SharedPreferencesUtil.saveUser(Login.this,user2);


                        if (email.equals(admin) && pass.equals(passadmin)) {
                            Intent golog = new Intent(getApplicationContext(), AdminPage.class);
                            isAdmin = true;
                            startActivity(golog);
                        } else {
                            Intent go = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(go);
                        }

                    }

                    @Override
                 public    void onFailed(Exception e) {

                    }
                });




            }


            @Override
            public void onFailed(Exception e) {
                Log.w("TAG", "signInWithEmail:failure", e);
                Toast.makeText(getApplicationContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }



}