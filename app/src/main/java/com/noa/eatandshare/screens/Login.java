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

// מחלקת Login, יורשת מ-BaseActivity ויודעת להגיב ללחיצות על כפתורים


public class Login extends BaseActivity implements View.OnClickListener {

    // הגדרות של רכיבי ממשק

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
        // קובע את העיצוב של המסך לפי קובץ XML
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // מקבל מופע של השירותים
        databaseService=DatabaseService.getInstance();
        authenticationService=AuthenticationService.getInstance();


        initViews();          // מאתחל את הכפתורים והשדות

        // בודק אם המשתמש שמור בזיכרון וממלא אוטומטית
        user= SharedPreferencesUtil.getUser(Login.this);
        if(user!=null) {

            email = user.getEmail();
            pass = user.getPassword();
            etEmail.setText(email);
            etPassword.setText(pass);
        }
    }


    // פעולה שמקשרת בין הרכיבים שב-XML לבין משתנים בג'אווה

    private void initViews() {
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLog = findViewById(R.id.btnLogin);
        // כשנלחץ על הכפתור – תקרה הפעולה onClick
        btnLog.setOnClickListener(this);



    }
    // פעולה שמתבצעת כשנלחץ על הכפתור
    @Override
    public void onClick(View v) {
        // שומר את מה שהמשתמש הקליד
        email = etEmail.getText().toString();
        pass = etPassword.getText().toString();

        // מבצע התחברות דרך Firebase
        authenticationService.signIn(email, pass, new AuthenticationService.AuthCallback<String>() {


            // הצלחנו להתחבר
            @Override
            public void onCompleted(String id) {
                Log.d("TAG", "signInWithEmail:success");

                // מביא את המשתמש מהמסד נתונים לפי ה-ID
                databaseService.getUser(id, new DatabaseService.DatabaseCallback<User>() {
                    @Override
                   public void onCompleted(User user2) {
                        // שומר את המשתמש בזיכרון של המכשיר
                        SharedPreferencesUtil.saveUser(Login.this,user2);

                        // אם המשתמש הוא אדמין - מעביר למסך ניהול
                        if (email.equals(admin) && pass.equals(passadmin)) {
                            Intent golog = new Intent(getApplicationContext(), AdminPage.class);
                            isAdmin = true;
                            startActivity(golog);
                        } else {                             // אחרת - מעביר למסך הבית הרגיל

                            Intent go = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(go);
                        }


                    }
                    // אם לא הצלחנו לקבל את המשתמש מהמסד
                    @Override
                 public    void onFailed(Exception e) {

                    }
                });




            }

            // אם ההתחברות נכשלה (מייל או סיסמה שגויים למשל)
            @Override
            public void onFailed(Exception e) {
                Log.w("TAG", "signInWithEmail:failure", e);
                // מציג הודעה למשתמש שההתחברות נכשלה
                Toast.makeText(getApplicationContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }



}