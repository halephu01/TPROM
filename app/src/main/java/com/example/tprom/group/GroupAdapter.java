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
import com.example.tprom.RecyclerViewClickListener;
import com.example.tprom.group.mainfragment.GroupFragment;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupHolder> {
    private Context context;
    private ArrayList<GroupItem> groupItems;
    private RecyclerViewClickListener mListener;


    public GroupAdapter(Context context, ArrayList<GroupItem> groupItems, GroupFragment listener) {
        this.context = context;
        this.groupItems = groupItems;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        return new GroupHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        GroupItem g = groupItems.get(position);
        holder.GroupName.setText(g.GroupName);
        holder.GroupOwner.setText(g.GroupOwner);
        holder.GroupDescription.setText(g.GroupDescription);
        if (g.getNumberOfDeadlines() != 0) {
            holder.Deadlines.setVisibility(View.VISIBLE);
            holder.Deadlines.setText(g.getNumberOfDeadlines() + " Deadlines");
        }
    }

    @Override
    public int getItemCount() {
        return groupItems.size();
    }

    public interface RecyclerViewClickListener {
        void onItemClick(View view, int adapterPosition);
    }

    static class GroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView GroupName, GroupOwner, GroupDescription, Deadlines;
        ImageView GroupAvt, GroupOwnerAvt;
        RecyclerViewClickListener mListener;

        public GroupHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            GroupName = itemView.findViewById(R.id.groupitem_groupname);
            GroupOwner = itemView.findViewById(R.id.groupitem_ownername);
            GroupDescription = itemView.findViewById(R.id.groupitem_description);
            Deadlines = itemView.findViewById(R.id.groupitem_deadline);
            GroupAvt = itemView.findViewById(R.id.groupitem_groupavt);
            GroupOwnerAvt = itemView.findViewById(R.id.groupitem_owneravt);
            mListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}
