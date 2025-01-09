package com.noa.eatandshare.screens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;

public class AdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    btnAddItem=findViewById(R.id.btnAddItem);
    btnGoAllOrders=findViewById(R.id.btnGoAllOrders);

        btnGoSearchPage.setOnClickListener(this);

        btnGoAfterLoginM.setOnClickListener(this);
        btnGoAllOrders.setOnClickListener(this);

        btnAddItem.setOnClickListener(this);
}

    @Override
    public void onClick(View view) {
        if(view==btnGoSearchPage){
            Intent go=new Intent(this, SearchPage.class);
            startActivity(go);
        }
        //  if(view==btnGoAddDonationPlace){
        //     Intent go=new Intent(this, AddDonationPlace.class);
        //      startActivity(go);
        // }
        if(view==btnGoAfterLoginM){
            Intent go=new Intent(this, AfterLogin.class);
            startActivity(go);
        }

        if(view==btnAddItem){
            Intent go=new Intent(this,AddItem.class);
            startActivity(go);
        }
        if(view==btnGoAllOrders){
            Intent go=new Intent(this,AllOrders.class);
            startActivity(go);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuitem) {
        int itemid = menuitem.getItemId();
        if (itemid == R.id.menuGoStore) {
            Intent goadmin = new Intent(AdminPage.this, SearchItem.class);
            startActivity(goadmin);
        }
        if (itemid == R.id.menuGoPersonal) {
            Intent goadmin = new Intent(AdminPage.this, UserProfile.class);
            startActivity(goadmin);
        }
        if (itemid == R.id.menuGoMyCart) {
            Intent goadmin = new Intent(AdminPage.this, Mycart.class);
            startActivity(goadmin);
        }
        if (itemid == R.id.menuGoAboutUs) {
            Intent goadmin = new Intent(AdminPage.this, AboutUs.class);
            startActivity(goadmin);
        }
        if (itemid == R.id.menuGoAllOrders) {
            Intent goadmin = new Intent(AdminPage.this, AllOrders.class);
            startActivity(goadmin);
        }
        if (itemid == R.id.menuGoAfterLogin) {
            Intent goadmin = new Intent(AdminPage.this, AfterLogin.class);
            startActivity(goadmin);
        }



        return super.onOptionsItemSelected(menuitem);
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