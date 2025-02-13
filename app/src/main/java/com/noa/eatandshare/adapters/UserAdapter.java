package com.noa.eatandshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.noa.eatandshare.R;
import com.noa.eatandshare.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    /// list of users
    /// @see User

    private final List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    /// create a view holder for the adapter
    /// @param parent the parent view group
    /// @param viewType the type of the view
    /// @return the view holder
    /// @see ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /// inflate the item_selected_user layout
        /// @see R.layout.item_selected_user
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_user, parent, false);
        return new ViewHolder(view);
    }


    /// bind the view holder with the data
    /// @param holder the view holder
    /// @param position the position of the item in the list
    /// @see ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        User user = userList.get(position);
        if (user == null) return;

        holder.tvFname.setText(user.getFname());
        holder.tvLname.setText(user.getLname());
        holder.tvPhone.setText(user.getPhone());

    }

    /// get the number of items in the list
    /// @return the number of items in the list
    @Override
    public int getItemCount() {
        return userList.size();
    }

    /// View holder for the users adapter
    /// @see RecyclerView.ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvFname;
        public final TextView tvLname;
        public final TextView tvPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFname = itemView.findViewById(R.id.tvFname);
            tvLname = itemView.findViewById(R.id.tvLname);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }
}
