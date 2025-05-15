package com.noa.eatandshare.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;


public class OdotPage extends BaseActivity implements View.OnClickListener {

    Button BackToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_odot_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initviews();
    }


    private void initviews() {
        BackToHome=findViewById(R.id.BackToHome);
        BackToHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view ==BackToHome){
            Intent intent = new Intent(OdotPage.this,MainActivity.class);
            startActivity(intent);

        }

    }
}