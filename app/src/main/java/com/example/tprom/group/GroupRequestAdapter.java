package com.example.tprom.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;
import com.example.tprom.notification.NotificationAdapter;

import java.util.ArrayList;

public class GroupRequestAdapter extends RecyclerView.Adapter<GroupRequestAdapter.GroupHolder> {
    Context context;
    ArrayList<GroupItem> groupItems;

    public GroupRequestAdapter(Context context, ArrayList<GroupItem> groupItems) {
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
        holder.GroupName.setText(g.groupName);
        holder.GroupOwner.setText(g.GroupOwner);
        holder.GroupDescription.setText(g.groupDescription);
        holder.RequestLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return groupItems.size();
    }

    static class GroupHolder extends RecyclerView.ViewHolder{
        TextView GroupName, GroupOwner, GroupDescription;
        ImageView GroupAvt, GroupOwnerAvt;
        LinearLayout RequestLayout;
        TextView AcceptButton,DeclineButton;
        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            GroupName = itemView.findViewById(R.id.groupitem_groupname);
            GroupOwner = itemView.findViewById(R.id.groupitem_ownername);
            GroupDescription = itemView.findViewById(R.id.groupitem_description);
            GroupAvt = itemView.findViewById(R.id.groupitem_groupavt);
            GroupOwnerAvt = itemView.findViewById(R.id.groupitem_owneravt);
            RequestLayout=itemView.findViewById(R.id.groupitem_ll_request);
            AcceptButton=itemView.findViewById(R.id.groupitem_accept);
            DeclineButton=itemView.findViewById(R.id.groupitem_decline);
        }
    }
}
