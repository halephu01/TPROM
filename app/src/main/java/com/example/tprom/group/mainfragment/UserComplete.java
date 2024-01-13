package com.example.tprom.group.mainfragment;

import android.annotation.SuppressLint;
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
import com.example.tprom.group.adapters.CompleteApdater;
import com.example.tprom.group.adapters.UncompleteAdapter;
import com.example.tprom.properties.Member;
import com.example.tprom.properties.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserComplete extends Fragment {
    RecyclerView rv_complete, rv_uncomplete;
    ArrayList<Member> members;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_complete, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv_complete = view.findViewById(R.id.rc_complete);
        rv_uncomplete = view.findViewById(R.id.rc_uncomplete);

        members = new ArrayList<>();

        LinearLayoutManager complete = new LinearLayoutManager(getContext());
        LinearLayoutManager uncomplete = new LinearLayoutManager(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            String groupName = bundle.getString("groupName");
            String taskName = bundle.getString("taskName");
            String taskDescription = bundle.getString("taskDescription");
            String taskDueTime = bundle.getString("taskDueTime");
            String taskStartTime = bundle.getString("taskStartTime");
            String taskProgress = bundle.getString("taskProgress");
            String groupDescription = bundle.getString("groupDescription");

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tasks");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                ArrayList<Member> membersComplete = new ArrayList<>();
                ArrayList<Member> membersUncomplete = new ArrayList<>();

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    membersComplete.clear();
                    membersUncomplete.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Task task = dataSnapshot.getValue(Task.class);

                        if (task.getGroupName().equals(groupName) && task.getTaskName().equals(taskName)) {
                            ArrayList<Member> taskMembers = task.getAssignedUsers();
                            for (Member mem : taskMembers) {
                                if (mem.getComplete() == 1) {
                                    membersComplete.add(mem);
                                } else {
                                    membersUncomplete.add(mem);
                                }
                            }

                            rv_complete.setLayoutManager(complete);
                            rv_uncomplete.setLayoutManager(uncomplete);

                            CompleteApdater completeAdapter = new CompleteApdater(getContext(), membersComplete);
                            rv_complete.setAdapter(completeAdapter);
                            completeAdapter.notifyDataSetChanged();

                            UncompleteAdapter uncompleteAdapter = new UncompleteAdapter(getContext(), membersUncomplete);
                            rv_uncomplete.setAdapter(uncompleteAdapter);
                            uncompleteAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("UserComplete", "DatabaseError: " + error.getMessage());
                }
            });

            if (getActivity() != null) {
                MainActivity mainActivity = (MainActivity) getActivity();

                TextView tv_onTop  = mainActivity.findViewById(R.id.tv_TopMainText);
                TextView tv_back = mainActivity.findViewById(R.id.btn_back);
                ImageView img_avatar = mainActivity.findViewById(R.id.iv_TopMainAvatar);

                tv_onTop.setText("Users");
                tv_back.setVisibility(View.VISIBLE);
                img_avatar.setVisibility(View.GONE);
                tv_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("groupName", groupName);
                        bundle.putString("taskName", taskName);
                        bundle.putString("taskDescription", taskDescription);
                        bundle.putString("taskDueTime", taskDueTime);
                        bundle.putString("taskStartTime", taskStartTime);
                        bundle.putDouble("taskProgress", Double.parseDouble(taskProgress));
                        bundle.putString("groupDescription", groupDescription);

                        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
                        taskDetailFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.complete_user_fragment, taskDetailFragment)
                                .commit();
                        tv_onTop.setText("Task");
                        tv_back.setVisibility(View.GONE);
                        img_avatar.setVisibility(View.VISIBLE);


                    }
                });
            }
        }
    }

}