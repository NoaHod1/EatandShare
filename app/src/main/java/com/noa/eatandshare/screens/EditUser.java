package com.noa.eatandshare.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.User;
import com.noa.eatandshare.services.AuthenticationService;
import com.noa.eatandshare.services.DatabaseService;


//הגדרת המחלקה
//מסך לעריכת פרטי משתמש.
//המחלקה "מקשיבה" ללחיצות כפתורים – בזכות implements View.OnClickListener.
public class EditUser extends AppCompatActivity implements View.OnClickListener {

    EditText etUserFnamec, etUserLnamec, etUserPhonec, etUserAddressc;
    TextView tvUserMailc;
    Button btnSave;

    String uid;
    User user=null;

    DatabaseService databaseService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // חובה כדי להפעיל את מה שמובנה באמא של המסך(מאיפה שהוא יורש)
        EdgeToEdge.enable(this); // שהמסך יתפרס עד הקצה
        setContentView(R.layout.activity_edit_user); // מחבר את המסך הזה לקובץ העיצוב שלו (XML)
        // דואג שכל הרכיבים במסך יתיישרו בהתאם למסך המכשיר
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    etUserFnamec = findViewById(R.id.etUserFnamec);
    etUserLnamec = findViewById(R.id.etUserLnamec);
    tvUserMailc = findViewById(R.id.tvUserMailc);
    etUserPhonec = findViewById(R.id.etUserPhonec);


    retrieveData();  // קוראת לפונקציה שמביאה את הפרטים של המשתמש מהמסד

    btnSave =  findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);

    }




public void retrieveData() {

    uid= AuthenticationService.getInstance().getCurrentUserId();   // מקבלים את מזהה המשתמש המחובר כרגע
    databaseService=DatabaseService.getInstance();




    databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {

        @Override
        public void onCompleted(User object) {
            user=object;  // שומרים את האובייקט שקיבלנו


            if(user!=null){ // אם המשתמש באמת קיים

                etUserFnamec.setText(user.getFname()); // מציגים את השם הפרטי
                etUserLnamec.setText(user.getLname()); // שם משפחה
                etUserPhonec.setText(user.getPhone()); // טלפון
                tvUserMailc.setText(user.getEmail());  // מייל (אי אפשר לערוך)


            }


        }

        @Override
        public void onFailed(Exception e) {

        }
    });

}
@Override
public void onClick(View v) { //מה קורה כשלוחצים על כפתור שמירה
    if (v == btnSave) {

        user.setFname(etUserFnamec.getText().toString()); // עדכון שם פרטי
        user.setLname(etUserLnamec.getText().toString()); // עדכון שם משפחה
        user.setPhone(etUserPhonec.getText().toString()); // עדכון טלפון

        databaseService.updateUser(user, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {

            }

            @Override
            public void onFailed(Exception e) {
            }
        });


         Intent intent = new Intent(EditUser.this, MainActivity.class); // הכנה למעבר למסך הראשי
        startActivity(intent);
    }


    }


}





