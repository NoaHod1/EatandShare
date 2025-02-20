package com.noa.eatandshare.adapters;

import android.graphics.Bitmap;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.noa.eatandshare.R;
import com.noa.eatandshare.models.Restaurant;
import com.noa.eatandshare.utils.ImageUtil;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;

    // אתחול עם רשימת מסעדות
    public RestaurantsAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }


    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // מניחים שמדובר ב-XML בשם item_restaurant.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onerest, parent, false);


        view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.bind(restaurant);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    // עדכון רשימת המסעדות
    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        notifyDataSetChanged();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView restaurantName;
        private TextView restaurantCuisine;
        private TextView restaurantAddress;
        private TextView restaurantPhoneNumber;
        private TextView restaurantDomain;
        private TextView glutenFreeItems;
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


            glutenFreeItems = itemView.findViewById(R.id.txtGlutenFreeItems);
            viewDetailsButton = itemView.findViewById(R.id.btnViewDetails);
            ivD = itemView.findViewById(R.id.ivRes);
        }

        public void bind(Restaurant restaurant) {
            // קביעת המידע לכל אחד מה-TextViewים
            restaurantName.setText(restaurant.getName());
            restaurantCuisine.setText(restaurant.getType());
            restaurantAddress.setText(restaurant.getStreet() + " " + restaurant.getCity());
            restaurantPhoneNumber.setText(restaurant.getDomain());

            restaurantDomain.setText(restaurant.getDomain());


            restaurantDomain.setMovementMethod(LinkMovementMethod.getInstance());

            // אם יש תמונה, המר את ה-Base64 ל-Bitmap ושים ב-ImageView
            if (restaurant.getPic() != null) {
                Bitmap bitmap = ImageUtil.convertFrom64base(restaurant.getPic());
                ivD.setImageBitmap(bitmap);
            }
        }
    }
}