package com.example.tprom.notification;

import android.content.Context;
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
        holder.taskName.setText(notificationItem.taskName);
        holder.groupName.setText(notificationItem.groupName);
        holder.taskDescription.setText(notificationItem.taskDescription);
        holder.date.setText(notificationItem.getTaskDueTime());
    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }

    static class NotificationHolder extends RecyclerView.ViewHolder{
        private TextView taskName, groupName, taskDescription, date;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            taskName=itemView.findViewById(R.id.notificationitem_tv_title);
            groupName=itemView.findViewById(R.id.notificationitem_tv_groupname);
            taskDescription=itemView.findViewById(R.id.notificationitem_tv_description);
            date=itemView.findViewById(R.id.notificationitem_tv_time);
        }
    }
}
