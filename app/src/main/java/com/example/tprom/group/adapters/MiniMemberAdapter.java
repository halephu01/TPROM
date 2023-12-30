package com.example.tprom.group.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;
import com.example.tprom.properties.User;

import java.util.ArrayList;

public class MiniMemberAdapter extends RecyclerView.Adapter<MiniMemberAdapter.MiniMemberHolder> {
    private Context context;
    private ArrayList<User> users;

    public MiniMemberAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MiniMemberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_mini,parent,false);
        return new MiniMemberAdapter.MiniMemberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniMemberHolder holder, int position) {
        User user=users.get(position);
        if(user.getAvatarUser()!=-1){
            holder.avatar.setImageResource(users.get(position).getAvatarUser());
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class MiniMemberHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        public MiniMemberHolder(@NonNull View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.item_member_mini_avatar);
        }
    }
}
