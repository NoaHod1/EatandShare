package com.noa.eatandshare.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.models.Review;
import com.noa.eatandshare.models.User;

import java.util.List;

public class ReviewsAdapter <P> extends ArrayAdapter<Review> {
    Context context;
    List<Review> reviews;
    List<Restaurant> restaurants;
    List<User> users;

    public  ReviewsAdapter(Context context, List<Review> reviews, List<Restaurant> restaurants, List<User> users) {
        super(context, 0, 0, reviews);

        this.context=context;
        this.reviews=reviews;
        this.restaurants = restaurants;
        this.users = users;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.onereview, parent, false);

        TextView tvNameRes = view.findViewById(R.id.tvResNameReview);
        TextView tvDate = view.findViewById(R.id.tvResDateReview);

        TextView tvDetails = view.findViewById(R.id.tvDetailsReview);
        TextView tvRate = view.findViewById(R.id.tvUserRateReview);
        TextView tvUserName = view.findViewById(R.id.tvUserNameReview);

        Review temp = reviews.get(position);

        Restaurant restaurant = null;
        for (Restaurant restaurant1 : restaurants) {
            if (restaurant1.getId() == temp.getRestaurantId()) {
                restaurant = restaurant1;
                break;
            }
        }
        User user = null;
        for (User user1 : this.users) {
            if (user1.getId() == temp.getUserID()) {
                user = user1;
                break;
            }
        }
        if (user == null || restaurant == null) {
            return view;
        }




        tvNameRes.setText(restaurant.getName()+"");
        tvDetails.setText(temp.getDetails()+"");
        tvDate.setText(temp.getDate()+"");
        tvRate.setText(temp.getRate()+"");
        tvUserName.setText(user.getFname());

        return view;
    }
}

