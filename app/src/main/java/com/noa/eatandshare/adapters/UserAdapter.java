package com.noa.eatandshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.noa.eatandshare.R;
import com.noa.eatandshare.models.User;
import com.noa.eatandshare.services.DatabaseService;

import java.util.List;

//UserAdapter הוא Adapter ל-RecyclerView להצגת רשימת משתמשים.

//כל שורה מציגה את הפרטים הבסיסיים של המשתמש (שם פרטי, שם משפחה, טלפון).
//כאשר לוחצים ארוך  על השורה זה מחיקת משתמש.
//ViewHolder הוא מחלקה פנימית שמחזיקה הפניות ל־TextView כדי לשפר ביצועים במיחזור התצוגות.

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {


    private final List<User> userList;  // רשימת המשתמשים להצגה
    private final Context context;

    public UserAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = userList.get(position);
        if (user == null) return;

        // מציג את שם הפרטי, שם המשפחה והטלפון בשדות המתאימים
        holder.tvFname.setText(user.getFname());
        holder.tvLname.setText(user.getLname());
        holder.tvPhone.setText(user.getPhone());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                    .setTitle("מחיקת משתמש")
                    .setMessage("אתה בטוח שאתה רוצה למחוק את המשתמש הזה?")
                    .setPositiveButton("אישור", (dialog, which) -> {
                        // פעולה כשלוחצים על אישור
                        dialog.dismiss();


                    removeUser(user);
                    notifyDataSetChanged();



                    })
                    .setNegativeButton("ביטול", (dialog, which) -> {
                        // פעולה כשלוחצים על ביטול
                        dialog.dismiss();
                    })
                    .show();
                return false;
            }
        });

    }

    private void removeUser(User user) {
        DatabaseService databaseService;
        databaseService=DatabaseService.getInstance();


        databaseService.delUser(user, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                notifyDataSetChanged();

            }

            @Override
            public void onFailed(Exception e) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

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
