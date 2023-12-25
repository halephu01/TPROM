package com.example.tprom.notification;

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

import java.util.ArrayList;
import java.util.Date;

public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<NotificationItem> notificationItems;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.notification_rc);
        notificationItems=new ArrayList<>();
        initValue();
        NotificationAdapter notificationAdapter=new NotificationAdapter(getContext(),notificationItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();
    }
    //Init sample value
    void initValue(){
        notificationItems.add(new NotificationItem("Thong bao 1","Nhom 1","Day la noi dung cua thong bao 1",new Date(123,12,23,10,30)));
        notificationItems.add(new NotificationItem("Thong bao 2","Nhom 2","Day la noi dung cua thong bao 2",new Date(123,12,24,10,30)));
        notificationItems.add(new NotificationItem("Thong bao 3","Nhom 3","Day la noi dung cua thong bao 3",new Date(123,12,25,10,30)));
        notificationItems.add(new NotificationItem("Thong bao 4","Nhom 4","Day la noi dung cua thong bao 4",new Date(123,12,26,10,30)));
    }
}