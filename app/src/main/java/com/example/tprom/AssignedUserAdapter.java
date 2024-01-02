package com.example.tprom;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssignedUserAdapter extends RecyclerView.Adapter<AssignedUserAdapter.AvatarViewHolder> {

    private List<Drawable> avatarList;

    public AssignedUserAdapter(List<Drawable> avatarList) {
        this.avatarList = avatarList;
    }

    @NonNull
    @Override
    public AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assigned, parent, false);
        return new AvatarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarViewHolder holder, int position) {
        Drawable avatar = avatarList.get(position);
        holder.avatarImageView.setImageDrawable(avatar);
    }

    @Override
    public int getItemCount() {
        return avatarList.size();
    }

    public static class AvatarViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;

        public AvatarViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
        }
    }
}