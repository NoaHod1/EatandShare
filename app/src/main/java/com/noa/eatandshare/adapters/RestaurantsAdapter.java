package com.noa.eatandshare.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.screens.AddReview;
import com.noa.eatandshare.screens.AdminPage;
import com.noa.eatandshare.screens.EditRes;
import com.noa.eatandshare.screens.Login;
import com.noa.eatandshare.screens.RestaurantProfile;
import com.noa.eatandshare.utils.ImageUtil;

import java.util.List;

//מחלקה זו אחראית להציג רשימת מסעדות בתוך RecyclerView.
// כל מסעדה מוצגת בפריט עיצובי מוגדר (layout) עם מידע כמו:
// שם, סוג אוכל, כתובת, טלפון, תחום, דירוג, תמונה, וכפתור לצפייה בפרטים.

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;

    private Context context;


    // אתחול עם רשימת מסעדות
    public RestaurantsAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    // יוצרת תצוגת פריט עבור כל מסעדה ומחזירה ViewHolder
    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // מניחים שמדובר ב-XML בשם item_restaurant.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onerest, parent, false);


        view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        return new RestaurantViewHolder(view);
    }

    // קושרת את המידע של המסעדה לתוך ViewHolder
    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.bind(restaurant);

        holder.viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Login.isAdmin) {      // אם המשתמש הוא אדמין – פותח מסך עריכת מסעדה


                    Intent goEdit = new Intent(context, EditRes.class);

                    goEdit.putExtra("Rest", restaurant); // שולח   המוצר
                    context.startActivity(goEdit);
                } else {                 // אם המשתמש רגיל – פותח את פרופיל המסעדה



                    Intent go = new Intent(context, RestaurantProfile.class);
                    go.putExtra("Rest", restaurant); // שולח   המוצר
                    context.startActivity(go);

                }


            }
        });
    }
    // מחזיר את מספר המסעדות להצגה
    @Override
    public int getItemCount() {
        return restaurantList.size();
    }


    // מחלקה פנימית שמייצגת את תצוגת פריט מסעדה בתוך הרשימה

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView restaurantName;
        private TextView restaurantCuisine;
        private TextView restaurantAddress;
        private TextView restaurantPhoneNumber;
        private TextView restaurantDomain;

        private RatingBar rBar;

        private Button viewDetailsButton;

        private ImageView ivD;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            // אתחול של כל רכיב
            restaurantName = itemView.findViewById(R.id.txtRestaurantName);
            restaurantCuisine = itemView.findViewById(R.id.txtRestaurantCuisine);
            restaurantAddress = itemView.findViewById(R.id.txtRestaurantAddress);
            restaurantPhoneNumber = itemView.findViewById(R.id.txtRestaurantPhoneNumber);
            restaurantDomain = itemView.findViewById(R.id.txtRestaurantDomain);

            rBar = itemView.findViewById(R.id.ratingBar);
            rBar.setEnabled(false);

            viewDetailsButton = itemView.findViewById(R.id.btnViewDetails);
            ivD = itemView.findViewById(R.id.ivRes);


            // הוספת לחיצה על כל מוצר כדי להוביל לדף המידע של המוצר


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent go = new Intent(context, AddReview.class);
                    Restaurant restaurant = restaurantList.get(getAdapterPosition());

                    go.putExtra("Rest", restaurant); // שולח את ה-ID של המוצר
                    context.startActivity(go);

                }
            });
        }

        // פונקציה שמצמידה את נתוני המסעדה לשדות התצוגה
        public void bind(Restaurant restaurant) {


            // קביעת המידע לכל אחד מה-TextViewים
            restaurantName.setText(restaurant.getName());
            restaurantCuisine.setText(restaurant.getType());
            restaurantAddress.setText(restaurant.getStreet() + " " + restaurant.getCity());
            restaurantPhoneNumber.setText(restaurant.getPhone());

            restaurantDomain.setText(restaurant.getDomain());
            // rBar.setNumStars(5);
            rBar.setRating(Float.parseFloat(restaurant.getRate() + ""));


            restaurantDomain.setMovementMethod(LinkMovementMethod.getInstance());

            // אם יש תמונה, המר את ה-Base64 ל-Bitmap ושים ב-ImageView
            if (restaurant.getPic() != null) {
                Bitmap bitmap = ImageUtil.convertFrom64base(restaurant.getPic());
                ivD.setImageBitmap(bitmap);
            }
        }
    }
}