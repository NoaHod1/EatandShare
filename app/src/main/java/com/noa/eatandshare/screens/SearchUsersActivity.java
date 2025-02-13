package com.noa.eatandshare.screens;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.noa.eatandshare.R;
import com.noa.eatandshare.adapters.UserAdapter;
import com.noa.eatandshare.models.User;
import com.noa.eatandshare.services.DatabaseService;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SearchUsersActivity extends AppCompatActivity {

    private static final String TAG="ReadUsers";
    DatabaseService databaseService;
    ArrayList<User> userList;
    RecyclerView rcUsers;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_users);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
            @Override
            public void onCompleted(List<User> object) {
                Log.d(TAG, "onCompleted: " + object);
                userList.clear();
                userList.addAll(object);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: ", e);
            }
        });
    }

    private void initViews() {
        databaseService = DatabaseService.getInstance();
        rcUsers = findViewById(R.id.rcUsers);

        rcUsers.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();

        adapter = new UserAdapter(userList);
        rcUsers.setAdapter(adapter);
    }
    }
