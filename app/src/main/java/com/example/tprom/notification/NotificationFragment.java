package com.example.tprom.notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tprom.R;
import com.example.tprom.group.GroupItem;
import com.example.tprom.properties.Member;
import com.example.tprom.properties.Task;
import com.example.tprom.properties.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<NotificationItem> notificationItems;
    ArrayList<Task> tasks;

    TextView tv_groupName;

    boolean isAdmin;

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
        recyclerView = view.findViewById(R.id.notification_rc);
        notificationItems = new ArrayList<>();
        if (notificationItems != null) {
            NotificationAdapter notificationAdapter = new NotificationAdapter(getContext(), notificationItems);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(notificationAdapter);
            notificationAdapter.notifyDataSetChanged();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String uid = user.getUid();
                DatabaseReference getUser = FirebaseDatabase.getInstance().getReference("users").child(uid).child("username");
                getUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    String username;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            username = dataSnapshot.getValue(String.class);
                        }
                        DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("groups");
                        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            ArrayList<Member> members = new ArrayList<>();
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                                    GroupItem memberName = memberSnapshot.getValue(GroupItem.class);

                                    members = memberName.getMembers();
                                    for (Member member : members) {
                                        String name = member.getName();
                                        if (name.equals(username) && member.getRole().equals("leader")) {
                                            isAdmin = true;
                                            break;
                                        }
                                    }
                                    break;

                                }
                                DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference().child("tasks");
                                tasksRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        notificationItems.clear();
                                        for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                                            NotificationItem item = taskSnapshot.getValue(NotificationItem.class);
                                            if (item != null) {
                                                String title = item.getTaskName();
                                                String groupName = item.getGroupName();
                                                String taskDescription = item.getTaskDescription();
                                                String Date = item.getTaskDueTime();
                                                int progressPercent = item.getProgressPercent();
                                                ArrayList<Member> members = item.getAssignedUsers();
                                                boolean isCurrentUserAssigned = false;
                                                for (Member member : members) {
                                                    String name = member.getName();
                                                    if (name.equals(username)) {
                                                        isCurrentUserAssigned = true;
                                                        break;
                                                    }
                                                }
                                                if (isCurrentUserAssigned && progressPercent < 100) {
                                                    // Tạo một đối tượng NotificationItem từ dữ liệu Firebase
                                                    notificationItems.add(new NotificationItem(title, groupName, "Task của bạn chưa được hoàn thành", Date));
                                                }
                                            }
                                        }
                                        notificationAdapter.notifyDataSetChanged();
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Xử lý lỗi nếu có
                                        Log.e("FirebaseError", "Error fetching data from Firebase: " + databaseError.getMessage());
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }

        //Init sample value
//    void initValue(){
//        notificationItems.add(new NotificationItem("Thong bao 1","Nhom 1","Day la noi dung cua thong bao 1",new Date(123,12,23,10,30)));
//        notificationItems.add(new NotificationItem("Thong bao 2","Nhom 2","Day la noi dung cua thong bao 2",new Date(123,12,24,10,30)));
//        notificationItems.add(new NotificationItem("Thong bao 3","Nhom 3","Day la noi dung cua thong bao 3",new Date(123,12,25,10,30)));
//        notificationItems.add(new NotificationItem("Thong bao 4","Nhom 4","Day la noi dung cua thong bao 4",new Date(123,12,26,10,30)));
//    }
    }
}