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

import com.example.tprom.R;
import com.example.tprom.group.GroupItem;
import com.example.tprom.group.GroupRequestAdapter;

import java.util.ArrayList;

public class GroupRequestFragment extends Fragment {
    ArrayList<GroupItem> groupItems;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        groupItems=new ArrayList<>();
        recyclerView=view.findViewById(R.id.grouprequest_rv);
        GroupRequestAdapter groupRequestAdapter = new GroupRequestAdapter(this.getContext(),groupItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(groupRequestAdapter);
        groupRequestAdapter.notifyDataSetChanged();

    }
}