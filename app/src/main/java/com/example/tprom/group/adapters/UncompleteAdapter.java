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

import java.util.ArrayList;

public class UncompleteAdapter extends RecyclerView.Adapter<UncompleteAdapter.ViewHolder>{
    Context context;
    ArrayList<Member> members;
    public UncompleteAdapter(Context context, ArrayList<Member> members) {
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public UncompleteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uncomplete_user, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UncompleteAdapter.ViewHolder holder, int position) {
        Member member = members.get(position);
        holder.tv_name_uncomplete.setText(member.getName());
        holder.avatarUser_uncomplete.setImageResource(R.drawable.mem);
        holder.tv_uncomplete.setText("Uncomplete");
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarUser_uncomplete;
        TextView tv_name_uncomplete,tv_uncomplete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarUser_uncomplete = itemView.findViewById(R.id.avatarUser_uncomplete);
            tv_name_uncomplete = itemView.findViewById(R.id.tv_name_uncomplete);
            tv_uncomplete = itemView.findViewById(R.id.tv_uncomplete);
        }
    }
}
