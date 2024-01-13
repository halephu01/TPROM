package com.example.tprom.group.adapters;

import android.content.Context;
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

public class CompleteApdater extends RecyclerView.Adapter<CompleteApdater.ViewHolder>{
    Context context;
    ArrayList<Member> members;

    public CompleteApdater(Context context, ArrayList<Member> members) {
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public CompleteApdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complete_user, parent, false);
        return new CompleteApdater.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompleteApdater.ViewHolder holder, int position) {
        Member member = members.get(position);
        holder.tv_name_complete.setText(member.getName());
        holder.iv_avatar_complete.setImageResource(R.drawable.mem);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_complete,tv_complete,down_file;
        ImageView iv_avatar_complete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_complete = itemView.findViewById(R.id.tv_name_complete);
            tv_complete = itemView.findViewById(R.id.tv_complete);
            down_file = itemView.findViewById(R.id.down_file);
            iv_avatar_complete = itemView.findViewById(R.id.avatarUser_complete);
        }
    }
}
