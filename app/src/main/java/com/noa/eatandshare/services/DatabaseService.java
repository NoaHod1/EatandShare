package com.noa.eatandshare.services;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.models.Review;
import com.noa.eatandshare.models.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//מחלקת שירות (singleton) שמנהלת פעולות מול Firebase Realtime Database.
// דרכה מבצעים קריאה, כתיבה, עדכון ומחיקה של משתמשים, מסעדות, ביקורות, ומועדפים.
public class DatabaseService {

    /// tag for logging
    // תגית ללוגים
    private static final String TAG = "DatabaseService";

    /// callback interface for database operations

    public interface DatabaseCallback<T> {
        /// called when the operation is completed successfully

        void onCompleted(T object);

        /// called when the operation fails with an exception
        void onFailed(Exception e);
    }

    /// the instance of this class
    private static DatabaseService instance;

    /// the reference to the database
    private final DatabaseReference databaseReference;

    /// use getInstance() to get an instance of this class
    private DatabaseService() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /// get an instance of this class
    /// return an instance of this class
    //  מחזיר מופע יחיד של המחלקה
    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }


    /// write data to the database at a specific path
    private void writeData(@NotNull final String path, @NotNull final Object data, final @NotNull DatabaseCallback<Void> callback) {
        databaseReference.child(path).setValue(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (callback == null) return;
                callback.onCompleted(task.getResult());
            } else {
                if (callback == null) return;
                callback.onFailed(task.getException());
            }
        });
    }

    /// read data from the database at a specific path


    private DatabaseReference readData(@NotNull final String path) {
        return databaseReference.child(path);
    }


    /// get data from the database at a specific path
    // מבצע שליפה של נתונים במסלול נתון וממפה אותם לסוג מסוים
    private <T> void getData(@NotNull final String path, @NotNull final Class<T> clazz, @NotNull final DatabaseCallback<T> callback) {
        readData(path).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            T data = task.getResult().getValue(clazz);
            callback.onCompleted(data);
        });
    }

    //מוחק נתונים במסלול נתון במסד הנתונים
    private void removeData(@NotNull final String path, final @NotNull DatabaseCallback<Void> callback) {
        databaseReference.child(path).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (callback == null) return;
                callback.onCompleted(task.getResult());
            } else {
                if (callback == null) return;
                callback.onFailed(task.getException());
            }
        });
    }

    /// generate a new id for a new object in the database
    /// מייצר מזהה חדש וייחודי במסלול נתון במסד הנתונים


    private String generateNewId(@NotNull final String path) {
        return databaseReference.child(path).push().getKey();
    }

    // end of private methods for reading and writing data

    // public methods to interact with the database

    /// create a new user in the database

    public void createNewUser(@NotNull final User user, @Nullable final DatabaseCallback<Void> callback) {
        writeData("users/" + user.getId(), user, callback);
    }


    public void updateUser(@NotNull final User user, @Nullable final DatabaseCallback<Void> callback) {
        writeData("users/" + user.getId(), user, callback);
    }

  //  מייצר מזהה חדש לביקורת במסד הנתונים ב "reviews"
    public String generateReviewId() {
        return generateNewId("reviews");
    }

  // שומר ביקורת חדשה או מעדכן ביקורת קיימת ב "reviews/{reviewId}".

    public void saveReview(@NotNull final Review review, @Nullable final DatabaseCallback<Void> callback) {
        writeData("reviews/" + review.getId(), review, callback);
    }

    //* מביא את כל הביקורות של משתמש מסוים לפי מזהה המשתמש.
    //     * הנתונים נשמרים תחת "usersReviews/{uid}".
    public void getUserReviews(@NotNull final String uid, @NotNull final DatabaseCallback<List<Review>> callback) {
        readData("usersReviews").child(uid).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            List<Review> reviewList = new ArrayList<>();
            task.getResult().getChildren().forEach(dataSnapshot -> {
                Review review = dataSnapshot.getValue(Review.class);
                Log.d(TAG, "Got restaurant: " + review);
                reviewList.add(review);
            });

            callback.onCompleted(reviewList);
        });
    }

    //     * מביא את כל הביקורות של מסעדה לפי מזהה המסעדה.
    public void getRestReviews(@NotNull final String resid, @NotNull final DatabaseCallback<List<Review>> callback) {
        readData("reviews/")
                .get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "Error getting data", task.getException());
                        callback.onFailed(task.getException());
                        return;
                    }
                    List<Review> reviewList = new ArrayList<>();


                    task.getResult().getChildren().forEach(dataSnapshot -> {
                        Review review = dataSnapshot.getValue(Review.class);
                        Log.d(TAG, "Got restaurant: " + review);

                        if (resid.equals(review.getRestaurantId()))
                            reviewList.add(review);
                    });

                    callback.onCompleted(reviewList);
                });
    }

    //מוסיף מסעדה לרשימת המועדפים של משתמש
    public void saveFavoriteRes(@NotNull final Restaurant restaurant, @NotNull final String uid, @Nullable final DatabaseCallback<Void> callback) {
        writeData("usersFavorite/" + uid + "/" + restaurant.getId() + "/", restaurant, callback);
    }


    public void getUserFavorite(@NotNull final String uid, @NotNull final DatabaseCallback<List<Restaurant>> callback) {


        readData("usersFavorite").child(uid).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            List<Restaurant> restaurants = new ArrayList<>();
            task.getResult().getChildren().forEach(dataSnapshot -> {
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                Log.d(TAG, "Got restaurant: " + restaurant);
                restaurants.add(restaurant);
            });

            callback.onCompleted(restaurants);
        });
    }

//מביא משתמש לפי מזהה
    public void getUser(@NotNull final String uid, @NotNull final DatabaseCallback<User> callback) {
        getData("users/" + uid, User.class, callback);
    }


    public void delUser(User user, @NotNull final DatabaseCallback<Void> callback) {

        writeData("oldUsers/" + user.getId() + "/", user, callback);

        removeData("users/" + user.getId(), callback);
    }

    /// create a new restaurant in the database

    public void createNewRestaurant(@NotNull final Restaurant restaurant, @Nullable final DatabaseCallback<Void> callback) {
        writeData("restaurants/" + restaurant.getId(), restaurant, callback);
    }


    //מעדכן מסעדה קיימת במסד הנתונים
    public void updateRestaurant(@NotNull final Restaurant restaurant, @Nullable final DatabaseCallback<Void> callback) {
        writeData("restaurants/" + restaurant.getId(), restaurant, callback);
    }

    /// get a restaurant from the database

    public void getRestaurant(@NotNull final String restaurantId, @NotNull final DatabaseCallback<Restaurant> callback) {
        getData("restaurants/" + restaurantId, Restaurant.class, callback);
    }

    /// generate a new id for a new restaurant in the database
//מייצר מזהה חדש למסעדה
    public String generateRestaurantId() {
        return generateNewId("restaurants");
    }


    /// get all the restaurants from the database
// מביא את כל המסעדות
    public void getRestaurants(@NotNull final DatabaseCallback<List<Restaurant>> callback) {
        readData("restaurants").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            List<Restaurant> restaurants = new ArrayList<>();
            task.getResult().getChildren().forEach(dataSnapshot -> {
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                Log.d(TAG, "Got restaurant: " + restaurant);
                restaurants.add(restaurant);
            });

            callback.onCompleted(restaurants);
        });
    }

    /// get all the users from the database

    public void getUsers(@NotNull final DatabaseCallback<List<User>> callback) {
        readData("users").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            List<User> users = new ArrayList<>();
            task.getResult().getChildren().forEach(dataSnapshot -> {
                User user = dataSnapshot.getValue(User.class);

                user = new User(user);
                Log.d(TAG, "Got user: " + user);
                users.add(user);
            });

            callback.onCompleted(users);
        });
    }


}



