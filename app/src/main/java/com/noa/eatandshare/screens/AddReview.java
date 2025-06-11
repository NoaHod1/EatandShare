package com.noa.eatandshare.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.models.Review;
import com.noa.eatandshare.models.User;
import com.noa.eatandshare.services.AuthenticationService;
import com.noa.eatandshare.services.DatabaseService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddReview extends BaseActivity {

    EditText etReview;
    String stReview;
    RatingBar rtBar;
    double rate;
    Button btnAddReview, btnBack;
    private Intent takeit;
    private Restaurant restaurant;
    private DatabaseService databaseService;
    String uid; // מזהה של המשתמש המחובר
    User user = null; // ניצור פה אובייקט של המשתמש
    private String formattedDateTime; // משתנה שישמור את התאריך והשעה בפורמט קריא


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // קריאה לפונקציה אב – חובה

        // "מחלקת אב" או "Superclass" בתכנות מונחה עצמים (Object-Oriented Programming).
        //להוריש תכונות והתנהגויות ממחלקה אחרת. המחלקה שמקבלת את התכונות נקראת "מחלקת בת", והמחלקה שהיא ירשה ממנה נקראת "מחלקת אב" או "הורה".

        EdgeToEdge.enable(this);  // מאפשר שהמסך ימלא את כל השטח כולל הקצוות
        setContentView(R.layout.activity_add_review);  // מציב את עיצוב המסך מהקובץ activity_add_review.xml

        // דואג שהרכיבים יסתדרו יפה לפי השוליים של המכשיר
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        // מקבלים את התאריך והשעה הנוכחיים
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         formattedDateTime = now.format(formatter);  // ממירים את התאריך לפורמט קריא
        Log.d("CurrentDate", "תאריך ושעה: " + formattedDateTime);  // מדפיסים לקונסול לצורך בדיקה


        // מקבלים מופע (instance) של שירות הנתונים
        databaseService=DatabaseService.getInstance();

        // מקבלים את המזהה של המשתמש המחובר כרגע
        uid= AuthenticationService.getInstance().getCurrentUserId();

        // מבקשים את פרטי המשתמש לפי המזהה
        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User object) {
                user=object;          // מבקשים את פרטי המשתמש לפי המזהה

                user=new User(user);  // יוצרים עותק חדש שלו


            }

            @Override
            public void onFailed(Exception e) {
                return;  // אם יש שגיאה – לא עושים כלום

            }
        });


        initViews();          // קוראים לפונקציה שמאתחלת את כל הרכיבים של המסך

        // מקבלים את הכוונה שהגיעה ממסך אחר
        takeit=getIntent();
        restaurant= (Restaurant) takeit.getSerializableExtra("Rest");  // מקבלים את המסעדה שנשלחה אל המסך הזה



    }

    // פונקציה שמאתחלת את כל הרכיבים במסך
    private void initViews() {

        // מחברים את המשתנים לרכיבים בקובץ העיצוב XML
        etReview=findViewById(R.id.etAddReview);
        rtBar=findViewById(R.id.rtAddResRev);
        btnAddReview=findViewById(R.id.btnAddREV);
        btnBack=findViewById(R.id.btnBack);


        // מאזין לכפתור חזור – כשנלחץ, חוזרים למסך חיפוש מסעדות
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddReview.this, SearchRestaurant.class);
                startActivity(intent);
            }
        });


        // מאזין לכפתור הוספת ביקורת – כשנלחץ, שומרים ביקורת חדשה
        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stReview=etReview.getText().toString();  // מקבלים את מה שהמשתמש כתב בביקורת
                rate=rtBar.getRating(); // מקבלים את הדירוג שהמשתמש נתן


                String reviewId = databaseService.generateReviewId(); // יוצרים מזהה ייחודי לביקורת
                LocalDateTime now = LocalDateTime.now(); // מקבלים את הזמן הנוכחי
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);  // מעצבים את התאריך לפורמט המתאים

                // יוצרים אובייקט חדש של ביקורת
                Review newReview=new Review(reviewId, restaurant.getId(), user.getId(), formattedDateTime , rate, stReview);


                // שומרים את הביקורת במסד הנתונים
                databaseService.saveReview(newReview, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {
                        // אם הצלחנו – חוזרים למסך חיפוש מסעדות
                        Intent intent = new Intent(AddReview.this, SearchRestaurant.class);
                        startActivity(intent);


                    }

                    @Override
                    public void onFailed(Exception e) {
                        // אם הייתה שגיאה – לא עושים כלום

                    }
                });

                // מעדכנים את המסעדה עם הנתונים החדשים של הדירוג

                restaurant.setNumberOfRating(); // מוסיף עוד ביקורת למספר הביקורות
                restaurant.setSumRating2(rate); // מוסיף את הציון שנתן המשתמש לסכום הכולל

                restaurant.setRate(restaurant.getRate()); // מחשב מחדש את הציון הממוצע

                // שומרים את העדכון של המסעדה במסד הנתונים
                databaseService.updateRestaurant(restaurant, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                   public void onCompleted(Void object) {
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }


                });

            }
        });



    }
}