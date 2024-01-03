package com.example.tprom.group.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;

import java.util.List;

public class AddMemberAdapter extends RecyclerView.Adapter<AddMemberAdapter.MemberViewHolder> {

    private List<String> members;
    private Context context;

    public AddMemberAdapter(Context context, List<String> members) {
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addmem, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        String memberName = members.get(position);
        holder.usernameTextView.setText(memberName);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.tv_member_name);
        }
    }

    public void updateData(List<String> newMembers) {
        this.members = newMembers;
        notifyDataSetChanged();
    }
}
