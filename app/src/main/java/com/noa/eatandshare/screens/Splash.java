package com.noa.eatandshare.screens;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;

public class Splash extends AppCompatActivity {

    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

iv=findViewById(R.id.imageView4);



        Thread myThread= new Thread(){

            @Override
            public void run() {

                try { synchronized (this){


                    Animation myAnim= AnimationUtils.loadAnimation(Splash.this, R.anim.myanim) ;

                    iv.startAnimation(myAnim);

                    wait(5000);
                }

                }
                catch (InterruptedException e){

                }
                finish();

                Intent go =new Intent(Splash.this, MainActivity.class);
                startActivity(go);

            }
        };

        myThread.start();

    }
}