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
import com.noa.eatandshare.models.Review;

import java.util.List;

public class ReviewsAdapter <P> extends ArrayAdapter<Review> {
    Context context;
    List<Review> objects;

    public  ReviewsAdapter(Context context, List<Review> objects) {
        super(context, 0, 0, objects);

        this.context=context;
        this.objects=objects;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.onereview, parent, false);

        TextView tvNameRes = (TextView)view.findViewById(R.id.tvResNameReview);
        TextView tvDate = (TextView)view.findViewById(R.id.tvResDateReview);

        TextView tvDetails = (TextView)view.findViewById(R.id.tvDetailsReview);
        TextView tvRate = (TextView)view.findViewById(R.id.tvUserRateReview);
        TextView tvUserName = (TextView)view.findViewById(R.id.tvUserNameReview);


        Review temp = objects.get(position);
        tvNameRes.setText(temp.getRestaurant().getName()+"");
        tvDetails.setText(temp.getDetails()+"");
        tvDate.setText(temp.getDate()+"");
        tvRate.setText(temp.getRate()+"");
        tvUserName.setText(temp.getUser().getFname());


        return view;
    }
}

