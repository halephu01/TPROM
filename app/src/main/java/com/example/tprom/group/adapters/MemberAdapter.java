package com.example.tprom.group.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;
import com.example.tprom.properties.Member;
import com.example.tprom.properties.User;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    private List<Member> members;
    Context context;

    public MemberAdapter(Context context, List<Member> members) {
        this.members = members;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Member member = members.get(position);

        holder.tv_name.setText(member.getName());
        holder.tv_role.setText(member.getRole());
        if (member.getAvatar() != -1) {
            holder.img_avatar.setImageResource(member.getAvatar());
        }
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView img_avatar;
        TextView tv_role;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.avatarImageView);
            tv_name = itemView.findViewById(R.id.nameTextView);
            tv_role = itemView.findViewById(R.id.roleTextView);
        }
    }
}
