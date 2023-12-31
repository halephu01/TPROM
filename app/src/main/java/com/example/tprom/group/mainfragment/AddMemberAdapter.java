package com.example.tprom.group.mainfragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tprom.R;

import java.util.List;

public class AddMemberAdapter extends BaseAdapter {
    private Context context;
    private String[] members;

    public AddMemberAdapter(Context context, List<String> members) {
        this.context = context;
        this.members = members.toArray(new String[0]);
    }

    @Override
    public int getCount() {
        return members.length;
    }

    @Override
    public Object getItem(int position) {
        return members[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.item_addmem, null);
        } else {
            gridView = convertView;
        }

        TextView usernameTextView = gridView.findViewById(R.id.tv_member_name);
        usernameTextView.setText(members[position]);

        return gridView;
    }
}
