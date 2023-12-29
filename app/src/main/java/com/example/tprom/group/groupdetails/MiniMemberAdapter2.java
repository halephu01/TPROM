package com.example.tprom.group.groupdetails;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;
import com.example.tprom.group.GroupAdapter;
import com.example.tprom.properties.User;

import java.util.ArrayList;

public class MiniMemberAdapter2 extends RecyclerView.Adapter<MiniMemberAdapter2.MiniMemberHolder> {
    private Context context;
    private ArrayList<User> users;

    public MiniMemberAdapter2(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MiniMemberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_mini_2,parent,false);
        return new MiniMemberAdapter2.MiniMemberHolder(view);
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
            avatar=itemView.findViewById(R.id.item_member_mini_2_avatar);
        }
    }
}
