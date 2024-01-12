package com.example.tprom.group.mainfragment;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.example.tprom.RecyclerViewClickListener;
import com.example.tprom.group.GroupItem;
import com.example.tprom.group.adapters.TaskAdapter;
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
import java.util.Date;
import java.util.List;

public class TaskFragment extends Fragment implements RecyclerViewClickListener{
    RecyclerView recyclerView;
    ArrayList<Task> tasks;

    TextView tv_groupName,tv_description;

    boolean isAdmin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_description = view.findViewById(R.id.tv_description);
        tv_groupName = view.findViewById(R.id.tv_groupName);

        recyclerView = view.findViewById(R.id.task_rv);
        isAdmin = true;
        tasks = new ArrayList<>();

        TaskAdapter taskAdapter = new TaskAdapter(getContext(), tasks, false, (RecyclerViewClickListener) TaskFragment.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();

        if (getActivity() != null) {
            MainActivity mainActivity = (MainActivity) getActivity();

            TextView tv_onTop = mainActivity.findViewById(R.id.tv_TopMainText);
            TextView tv_back = mainActivity.findViewById(R.id.btn_back);
            ImageView img_avatar = mainActivity.findViewById(R.id.iv_TopMainAvatar);

            tv_onTop.setText("Task");
            tv_back.setVisibility(View.VISIBLE);
            img_avatar.setVisibility(View.GONE);
            tv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupDetailsFragment groupDetailsFragment = new GroupDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("groupName", tv_groupName.getText().toString());
                    bundle.putString("groupDescription", tv_description.getText().toString());
                    groupDetailsFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentDetailGroup, groupDetailsFragment)
                            .commit();
                    tv_onTop.setText("Details");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            String groupName = bundle.getString("groupName");
            String description = bundle.getString("groupDescription");
            tv_groupName.setText(groupName);
            tv_description.setText(description);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference getUser = FirebaseDatabase.getInstance().getReference("users").child(uid).child("username");
            getUser.addListenerForSingleValueEvent(new ValueEventListener() {
                String username;
                boolean isAdmin = false;

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
                                if (memberName.groupName.toString().equals(tv_groupName.getText().toString())) {
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
                            }

                            boolean finalIsAdmin = isAdmin;
                            DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks");
                            taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        // Xóa danh sách tasks trước khi thêm mới
                                        tasks.clear();
                                        for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                                            Task task = taskSnapshot.getValue(Task.class);
                                            if (task != null && task.getGroupName().equals(tv_groupName.getText().toString())) {
                                                String nametask = task.getTaskName();
                                                String description = task.getTaskDescription();
                                                members = task.getAssignedUsers();
                                                int numberOfFiles = task.getFiles().size();
                                                String dueTime = task.getTaskDueTime();
                                                String startTime = task.getTaskStartTime();
                                                int statusTask = task.getStatus();
                                                double progressPercent = task.getProgressPercent();

                                                // Kiểm tra quyền isAdmin mỗi khi thêm công việc vào danh sách
                                                if (!finalIsAdmin) {
                                                    tasks.add(new Task("1", nametask, description, statusTask, numberOfFiles,startTime, dueTime));
                                                } else {
                                                    tasks.add(new Task("1", nametask, description, progressPercent,startTime, dueTime, numberOfFiles, members));
                                                }
                                            }
                                        }
                                        TaskAdapter taskAdapter = new TaskAdapter(getContext(), tasks, finalIsAdmin, (RecyclerViewClickListener) TaskFragment.this);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                                        recyclerView.setAdapter(taskAdapter);
                                        taskAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Xử lý khi có lỗi
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("taskID", tasks.get(position).getTaskId());
        bundle.putString("taskName", tasks.get(position).getTaskName());
        bundle.putString("taskDescription", tasks.get(position).getTaskDescription());
        bundle.putString("taskStart", tasks.get(position).getTaskStartTime());
        bundle.putString("taskDueTime", tasks.get(position).getTaskDueTime());
        bundle.putInt("taskStatus", tasks.get(position).getStatus());
        bundle.putDouble("taskProgress", tasks.get(position).getProgressPercent());

        bundle.putString("groupName", tv_groupName.getText().toString());
        bundle.putString("groupDescription", tv_description.getText().toString());
        taskDetailFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_task, taskDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}