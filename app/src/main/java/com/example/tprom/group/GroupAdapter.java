package com.example.tprom.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;
import com.example.tprom.notification.NotificationAdapter;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupHolder> {
    Context context;
    ArrayList<GroupItem> groupItems;

    public GroupAdapter(Context context, ArrayList<GroupItem> groupItems) {
        this.context = context;
        this.groupItems = groupItems;
    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item,parent,false);
        return new GroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        GroupItem g=groupItems.get(position);
        holder.GroupName.setText(g.GroupName);
        holder.GroupOwner.setText(g.GroupOwner);
        holder.Description.setText(g.Description);
        if(g.getNumberOfDeadlines()!=0){
            holder.Deadlines.setVisibility(View.VISIBLE);
            holder.Deadlines.setText(g.getNumberOfDeadlines() + " Deadlines");
        }
    }

    @Override
    public int getItemCount() {
        return groupItems.size();
    }

    static class GroupHolder extends RecyclerView.ViewHolder{
        TextView GroupName, GroupOwner, Description, Deadlines;
        ImageView GroupAvt, GroupOwnerAvt;
        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            GroupName = itemView.findViewById(R.id.groupitem_groupname);
            GroupOwner = itemView.findViewById(R.id.groupitem_ownername);
            Description = itemView.findViewById(R.id.groupitem_description);
            Deadlines = itemView.findViewById(R.id.groupitem_deadline);
            GroupAvt = itemView.findViewById(R.id.groupitem_groupavt);
            GroupOwnerAvt = itemView.findViewById(R.id.groupitem_owneravt);

        }
    }
}
