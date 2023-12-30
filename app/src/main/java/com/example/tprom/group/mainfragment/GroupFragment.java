package com.example.tprom.group.mainfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tprom.R;
import com.example.tprom.group.GroupAdapter;
import com.example.tprom.group.GroupItem;

import java.util.ArrayList;

public class GroupFragment extends Fragment {
    EditText ed_findteam;
    TextView tv_request,tv_create,tv_find;
    RecyclerView recyclerView;
    ArrayList<GroupItem> groupItems;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_findteam = view.findViewById(R.id.group_et_findteam);
        tv_create=view.findViewById(R.id.group_btn_create);
        tv_find=view.findViewById(R.id.group_btn_find);
        recyclerView=view.findViewById(R.id.group_rv);
        groupItems=new ArrayList<>();
        InitSample();
        GroupAdapter groupAdapter= new GroupAdapter(getContext(),groupItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();
    }
    //sample data
    void InitSample(){
        groupItems.add(new GroupItem("Group 1","Lan dau tien trai thanh long co trong mi tom","That's me",3));
        groupItems.add(new GroupItem("Group 2","Lan dau tien trai thanh long co trong mi tom","That's me",4));
        groupItems.add(new GroupItem("Group 3","Lan dau tien trai thanh long co trong mi tom","That's me",2));
        groupItems.add(new GroupItem("Group 4","Lan dau tien trai thanh long co trong mi tom","That's me",0));
        groupItems.add(new GroupItem("Group 4","Lan dau tien trai thanh long co trong mi tom","That's me",4));
    }
}