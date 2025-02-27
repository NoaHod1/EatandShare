package com.noa.eatandshare.screens;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.stream.Collectors;
import android.text.TextWatcher;  // ה-import עבור TextWatcher
import android.text.Editable;     // ה-import עבור Editable
import android.widget.EditText;   // ה-import עבור EditText



public class SearchUsersActivity extends AppCompatActivity {

    private static final String TAG = "ReadUsers";
    DatabaseService databaseService;
    ArrayList<User> userList;
    ArrayList<User> filteredUserList;
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

        // קבלת רשימת המשתמשים מהמסד נתונים
        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
            @Override
            public void onCompleted(List<User> object) {
                Log.d(TAG, "onCompleted: " + object);
                userList.clear();
                userList.addAll(object);
                filteredUserList.clear();
                filteredUserList.addAll(object);
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
        filteredUserList = new ArrayList<>();

        adapter = new UserAdapter(filteredUserList);
        rcUsers.setAdapter(adapter);

        // הוספת TextWatcher לשדה החיפוש
        EditText editText = findViewById(R.id.searchEditText);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // לא צריך להוסיף משהו כאן
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // לא צריך להוסיף משהו כאן
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // קריאה לפונקציה filterUsers עם הערך החדש שהוקלד
                filterUsers(editable.toString());
            }
        });
    }

    // פונקציה לחיפוש משתמשים לפי שם פרטי
    private void filterUsers(String query) {
        if (query.isEmpty()) {
            // אם לא הוזן טקסט, הצג את כל המשתמשים
            filteredUserList.clear();
            filteredUserList.addAll(userList);
        } else {
            // אם יש טקסט, חפש לפי שם פרטי
            filteredUserList.clear();
            filteredUserList.addAll(userList.stream()
                    .filter(user -> user.getFname().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList()));
        }
        // עדכון ה-RecyclerView
        adapter.notifyDataSetChanged();
    }
}

