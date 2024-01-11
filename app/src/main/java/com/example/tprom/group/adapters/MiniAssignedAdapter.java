package com.example.tprom.group.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;
import com.example.tprom.properties.Member;
import com.example.tprom.properties.User;

import java.util.ArrayList;

public class MiniAssignedAdapter extends RecyclerView.Adapter<MiniAssignedAdapter.MiniAssignedHolder>{
    private Context context;
    private ArrayList<Member> selectedUsers;

    public MiniAssignedAdapter(Context context, ArrayList<Member> selectedUsers) {
        this.context = context;
        this.selectedUsers = selectedUsers;
    }

    @NonNull
    @Override
    public MiniAssignedAdapter.MiniAssignedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assigned,parent,false);
        return new MiniAssignedAdapter.MiniAssignedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniAssignedAdapter.MiniAssignedHolder holder, int position) {
        Member member = selectedUsers.get(position);
        holder.avatar.setImageResource(R.drawable.mem);
    }

    @Override
    public int getItemCount() {
        return selectedUsers.size();
    }

    public class MiniAssignedHolder extends RecyclerView.ViewHolder {
        ImageView avatar;

        public MiniAssignedHolder(@NonNull View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.avatarImageView);
        }
    }
}
