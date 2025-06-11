package com.noa.eatandshare.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.Review;
import com.noa.eatandshare.models.User;
import com.noa.eatandshare.services.DatabaseService;

import java.util.List;

public class ReviewsAdapter<P> extends ArrayAdapter<Review> {
    Context context;
    List<Review> reviews;
    User user;


    public ReviewsAdapter(Context context, List<Review> reviews) {
        super(context, 0, 0, reviews);

        this.context = context;
        this.reviews = reviews;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.onereview, parent, false);

        TextView tvDate = view.findViewById(R.id.tvResDateReview);

        TextView tvDetails = view.findViewById(R.id.tvDetailsReview);
        TextView tvRate = view.findViewById(R.id.tvUserRateReview);
        TextView tvUserName = view.findViewById(R.id.tvUserNameReview);

        // מקבלים את הביקורת המתאימה למיקום הנוכחי ברשימה

        Review temp = reviews.get(position);

        tvDetails.setText(temp.getDetails() + "");
        tvDate.setText(temp.getDate() + "");
        tvRate.setText(temp.getRate() + "");


        DatabaseService databaseService;

        databaseService = DatabaseService.getInstance();

        // טוענים את פרטי המשתמש לפי מזהה המשתמש בביקורת

        databaseService.getUser(temp.getUserID(), new DatabaseService.DatabaseCallback<User>() {

            @Override
            public void onCompleted(User user2) {

                user = user2;
                if (user != null) {

                    tvUserName.setText((user.getFname()));
                }

            }


            @Override
            public void onFailed(Exception e) {

            }


        });


        return view;
    }


}



