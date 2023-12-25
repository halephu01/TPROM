package com.example.tprom.notification;

import android.content.Context;
import android.icu.text.CaseMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;

import java.util.ArrayList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder>{
    private Context context;
    private ArrayList<NotificationItem> notificationItems;

    public NotificationAdapter(Context context, ArrayList<NotificationItem> notificationItems) {
        this.context = context;
        this.notificationItems = notificationItems;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        NotificationItem notificationItem= notificationItems.get(position);
        holder.title.setText(notificationItem.Title);
        holder.groupname.setText(notificationItem.GroupName);
        holder.description.setText(notificationItem.Description);
        holder.date.setText(notificationItem.getDate());
    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }

    static class NotificationHolder extends RecyclerView.ViewHolder{
        private TextView title, groupname, description, date;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.notificationitem_tv_title);
            groupname=itemView.findViewById(R.id.notificationitem_tv_groupname);
            description=itemView.findViewById(R.id.notificationitem_tv_description);
            date=itemView.findViewById(R.id.notificationitem_tv_time);
        }
    }
}
