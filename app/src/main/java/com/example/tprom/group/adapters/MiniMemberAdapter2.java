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

public class MiniMemberAdapter2 extends RecyclerView.Adapter<MiniMemberAdapter2.MiniMemberHolder> {
    private Context context;
    private ArrayList<Member> users;

    public MiniMemberAdapter2(Context context, ArrayList<Member> users) {
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
        Member user = users.get(position);
        if (user != null) {
            holder.avatar.setImageResource(R.drawable.icon_user02);
            // Thực hiện các thao tác khác với đối tượng user
        }
    }

    @Override
    public int getItemCount() {
        if (users != null) {
            return users.size();
        } else {
            return 0; // hoặc một giá trị mặc định nếu danh sách là null
        }
    }

    static class MiniMemberHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        public MiniMemberHolder(@NonNull View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.item_member_mini_2_avatar);
        }
    }
}
