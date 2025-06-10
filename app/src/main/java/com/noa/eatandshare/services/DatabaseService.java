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


/// a service to interact with the Firebase Realtime Database.
/// this class is a singleton, use getInstance() to get an instance of this class
/// @see #getInstance()
/// @see FirebaseDatabase
public class DatabaseService {

    /// tag for logging
    /// @see Log
    private static final String TAG = "DatabaseService";

    /// callback interface for database operations
    /// @param <T> the type of the object to return
    /// @see DatabaseCallback#onCompleted(Object)
    /// @see DatabaseCallback#onFailed(Exception)
    public interface DatabaseCallback<T> {
        /// called when the operation is completed successfully
        ///
        /// @return
        void onCompleted(T object);

        /// called when the operation fails with an exception
        void onFailed(Exception e);
    }

    /// the instance of this class
    /// @see #getInstance()
    private static DatabaseService instance;

    /// the reference to the database
    /// @see DatabaseReference
    /// @see FirebaseDatabase#getReference()
    private final DatabaseReference databaseReference;

    /// use getInstance() to get an instance of this class
    /// @see DatabaseService#getInstance()
    private DatabaseService() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /// get an instance of this class
    /// @return an instance of this class
    /// @see DatabaseService
    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }


    // private generic methods to write and read data from the database

    /// write data to the database at a specific path
    /// @param path the path to write the data to
    /// @param data the data to write (can be any object, but must be serializable, i.e. must have a default constructor and all fields must have getters and setters)
    /// @param callback the callback to call when the operation is completed
    /// @return void
    /// @see DatabaseCallback
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
    /// @param path the path to read the data from
    /// @return a DatabaseReference object to read the data from
    /// @see DatabaseReference

    private DatabaseReference readData(@NotNull final String path) {
        return databaseReference.child(path);
    }


    /// get data from the database at a specific path
    /// @param path the path to get the data from
    /// @param clazz the class of the object to return
    /// @param callback the callback to call when the operation is completed
    /// @return void
    /// @see DatabaseCallback
    /// @see Class
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

    /// generate a new id for a new object in the database
    /// @param path the path to generate the id for
    /// @return a new id for the object
    /// @see String
    /// @see DatabaseReference#push()

    private String generateNewId(@NotNull final String path) {
        return databaseReference.child(path).push().getKey();
    }

    // end of private methods for reading and writing data

    // public methods to interact with the database

    /// create a new user in the database
    /// @param user the user object to create
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive void
    ///            if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see User
    public void createNewUser(@NotNull final User user, @Nullable final DatabaseCallback<Void> callback) {
        writeData("users/" + user.getId(), user, callback);
    }


    public void updateUser(@NotNull final User user, @Nullable final DatabaseCallback<Void> callback) {
        writeData("users/" + user.getId(), user, callback);
    }

    public String generateReviewId() {
        return generateNewId("reviews");
    }

    public void saveReview(@NotNull final Review review, @Nullable final DatabaseCallback<Void> callback) {
        writeData("reviews/" + review.getId(), review,callback) ;
    }

    public void getUserReviews( @NotNull final String uid , @NotNull final DatabaseCallback<List<Review>> callback) {
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

    public void getRestReviews( @NotNull final String resid , @NotNull final DatabaseCallback<List<Review>> callback) {
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

                if(resid.equals(review.getRestaurantId()))
                             reviewList.add(review);
            });

            callback.onCompleted(reviewList);
        });
    }








    public void saveFavoriteRes(@NotNull final Restaurant restaurant,@NotNull final String uid , @Nullable final DatabaseCallback<Void> callback) {
        writeData("usersFavorite/" + uid+"/"+restaurant.getId()+"/", restaurant, callback);
    }


    public void getUserFavorite( @NotNull final String uid , @NotNull final DatabaseCallback<List<Restaurant>> callback) {


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





    /// get a user from the database
    /// @param uid the id of the user to get
    /// @param callback the callback to call when the operation is completed
    ///               the callback will receive the user object
    ///             if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see User
    public void getUser(@NotNull final String uid, @NotNull final DatabaseCallback<User> callback) {
        getData("users/" + uid, User.class, callback);
    }


    /// create a new restaurant in the database
    /// @param restaurant the restaurant object to create
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive void
    ///             if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Restaurant
    public void createNewRestaurant(@NotNull final Restaurant restaurant, @Nullable final DatabaseCallback<Void> callback) {
        writeData("restaurants/" + restaurant.getId(), restaurant, callback);
    }


    public void updateNewRestaurant(@NotNull final Restaurant restaurant, @Nullable final DatabaseCallback<Void> callback) {
        writeData("restaurants/" + restaurant.getId(), restaurant, callback);
    }



    /// get a user from the database
    /// @param uid the id of the user to get
    /// @param callback the callback to call when the operation is completed
    ///               the callback will receive the user object
    ///             if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see User
  


    /// get a restaurant from the database
    /// @param restaurantId the id of the restaurant to get
    /// @param callback the callback to call when the operation is completed
    ///               the callback will receive the restaurant object
    ///              if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Restaurant
    public void getRestaurant(@NotNull final String restaurantId, @NotNull final DatabaseCallback<Restaurant> callback) {
        getData("restaurants/" + restaurantId, Restaurant.class, callback);
    }

    /// generate a new id for a new restaurant in the database
    /// @return a new id for the restaurant
    /// @see #generateNewId(String)
    /// @see Restaurant
    public String generateRestaurantId() {
        return generateNewId("restaurants");
    }



    /// get all the restaurants from the database
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive a list of restaurant objects
    ///            if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see List
    /// @see Restaurant
    /// @see #getData(String, Class, DatabaseCallback)
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
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive a list of restaurant objects
    ///            if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see List
    /// @see Restaurant
    /// @see #getData(String, Class, DatabaseCallback)
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

                user=new User(user);
                Log.d(TAG, "Got user: " + user);
                users.add(user);
            });

            callback.onCompleted(users);
        });
    }


}



