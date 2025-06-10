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

public class EditUser extends AppCompatActivity implements View.OnClickListener {

    EditText etUserFnamec, etUserLnamec, etUserPhonec, etUserAddressc;
    TextView tvUserMailc;
    Button btnSave;

    String uid;
    User user=null;

    DatabaseService databaseService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    etUserFnamec = findViewById(R.id.etUserFnamec);
    etUserLnamec = findViewById(R.id.etUserLnamec);
    tvUserMailc = findViewById(R.id.tvUserMailc);
    etUserPhonec = findViewById(R.id.etUserPhonec);


    retrieveData();

    btnSave =  findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);








    }








public void retrieveData() {

    uid= AuthenticationService.getInstance().getCurrentUserId();
    databaseService=DatabaseService.getInstance();




    databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {

        @Override
        public void onCompleted(User object) {
            user=object;


            if(user!=null){

                etUserFnamec.setText( user.getFname());
                etUserLnamec.setText(  user.getLname());
                etUserPhonec.setText( user.getPhone());
                tvUserMailc.setText( user.getEmail());





            }


        }

        @Override
        public void onFailed(Exception e) {



        }
    });

}
@Override
public void onClick(View v) {
    if (v == btnSave) {



        user.setFname(etUserFnamec.getText().toString());
        user.setLname(etUserLnamec.getText().toString());
        user.setPhone(etUserPhonec.getText().toString());

        databaseService.updateUser(user, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {


            }

            @Override
            public void onFailed(Exception e) {

            }
        });





         Intent intent = new Intent(EditUser.this, MainActivity.class);
        startActivity(intent);
    }


    }


}





