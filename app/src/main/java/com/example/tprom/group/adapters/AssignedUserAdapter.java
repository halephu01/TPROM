package com.example.tprom.group.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.OnCheckedChangeListener;
import com.example.tprom.R;
import com.example.tprom.properties.Member;

import java.util.ArrayList;
import java.util.List;

public class AssignedUserAdapter extends RecyclerView.Adapter<AssignedUserAdapter.AvatarViewHolder> {

    private List<Member> members;
    Context context;

    private OnCheckedChangeListener onCheckedChangeListener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    public AssignedUserAdapter(Context context, List<Member> members) {
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_user, parent, false);
        return new AvatarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Example code, make sure to adapt it to your actual code
        TextView textView = holder.itemView.findViewById(R.id.nameTextView);

        // Check if textView is null before calling setText
        if (textView != null) {
            textView.setText(members.get(position).getName());
        } else {
            // Log a message or handle the case where textView is null
        }

        CheckBox chb_selectUser = holder.itemView.findViewById(R.id.checkbox_select_user);

        chb_selectUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                members.get(position).setSelected(isChecked);
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChange(position, isChecked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return members != null ? members.size() : 0;
    }

    public List<String> getSelectedUsers() {
        List<String> selectedUsers = new ArrayList<>();

        // Kiểm tra xem members có được khởi tạo hay không
        if (members != null) {
            for (Member member : members) {
                if (member.isSelected()) {
                    selectedUsers.add(member.getName());
                }
            }
        }
        return selectedUsers;
    }

    public static class AvatarViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView tv_name;
        CheckBox chb_selectUser;

        public AvatarViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            tv_name = itemView.findViewById(R.id.nameTextView);
            chb_selectUser = itemView.findViewById(R.id.checkbox_select_user);
        }
    }
}
