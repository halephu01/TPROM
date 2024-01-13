package com.example.tprom.group.mainfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.example.tprom.RecyclerViewClickListener;
import com.example.tprom.group.GroupItem;
import com.example.tprom.group.adapters.FileTaskInfoAdapter;
import com.example.tprom.group.adapters.TaskAdapter;
import com.example.tprom.group.adapters.UploadFileAdapter;
import com.example.tprom.properties.Member;
import com.example.tprom.properties.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDetailFragment extends Fragment {
    TextView taskName, taskStart, taskEnd, taskReadmore, tv_groupName, tv_description, tv_edit,
            tv_edit_ad, tv_complete, tv_percent, tv_infoFile, tv_progressbar;
    RecyclerView rc_file,rc_upload;
    RelativeLayout rl_progress, rl_upload;
    ProgressBar progressBar;

    List<String> fileList;

    UploadFileAdapter adapter;

    private static final int REQUEST_CODE = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_infor, container, false);
    }

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskName = view.findViewById(R.id.tv_designing_a);
        taskStart = view.findViewById(R.id.startdate);
        taskEnd = view.findViewById(R.id.enddate);
        taskReadmore= view.findViewById(R.id.tv_readmore);
        tv_infoFile = view.findViewById(R.id.file_2);
        rc_file = view.findViewById(R.id.recyclerview_file_taskinfo);

        tv_edit = view.findViewById(R.id.tv_complete_ad);
        tv_edit_ad = view.findViewById(R.id.tv_edit_ad);
        rl_progress = view.findViewById(R.id.layout_progress);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setMax(100);
        tv_percent = view.findViewById(R.id.percent);
        tv_progressbar = view.findViewById(R.id.tv_progressbar);

        tv_complete = view.findViewById(R.id.tv_complete);
        rc_upload = view.findViewById(R.id.recyclerview_upload_taskinfo);
        rl_upload = view.findViewById(R.id.layout_upload);

        tv_groupName = view.findViewById(R.id.tv_groupName);
        tv_description = view.findViewById(R.id.tv_description);

        RecyclerView recyclerUpload = view.findViewById(R.id.recyclerview_upload_taskinfo);
        recyclerUpload.setLayoutManager(new GridLayoutManager(getContext(), 2));


        if (getActivity() != null) {
            MainActivity mainActivity = (MainActivity) getActivity();

            TextView tv_onTop  = mainActivity.findViewById(R.id.tv_TopMainText);
            TextView tv_back = mainActivity.findViewById(R.id.btn_back);
            ImageView img_avatar = mainActivity.findViewById(R.id.iv_TopMainAvatar);

            tv_onTop.setText("Task");
            tv_back.setVisibility(View.VISIBLE);
            img_avatar.setVisibility(View.GONE);
            tv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentDetailGroup, new GroupFragment())
                            .commit();
                    tv_onTop.setText("Details");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }

        Bundle bundle = getArguments();
        if (bundle != null){
            String taskname = bundle.getString("taskName");
            String taskstart = bundle.getString("taskStart");
            String taskend = bundle.getString("taskDueTime");
            String taskdescription = bundle.getString("taskDescription");
            Double progress = bundle.getDouble("taskProgress");

            taskName.setText(taskname);
            taskStart.setText(taskstart);
            taskEnd.setText(taskend);
            taskReadmore.setText(taskdescription);
            progress= progress*100;
            tv_percent.setText(Integer.toString(progress.intValue()) +"%");
            int percent = Integer.parseInt(Integer.toString(progress.intValue()));
            progressBar.setProgress(percent);

            tv_groupName.setText(bundle.getString("groupName"));
            tv_description.setText(bundle.getString("groupDescription"));
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

                            if(isAdmin){
                                rl_upload.setVisibility(View.GONE);
                                tv_complete.setVisibility(View.GONE);

                                canCompleteOrEditTask();
                            }else {
                                rl_progress.setVisibility(View.GONE);
                                tv_edit.setVisibility(View.GONE);
                                tv_edit_ad.setVisibility(View.GONE);

                                canUploadTask();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks");
        taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
            List<String>fileList;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                        Task task = taskSnapshot.getValue(Task.class);
                        if (task != null && task.getTaskName().equals(taskName.getText().toString())) {
                            String groupName = task.getGroupName();
                            String groupname = tv_groupName.getText().toString();

                            if(groupName.equals(groupname)){
                                fileList = task.getFiles();
                                if (fileList.size() > 0) {

                                    FileTaskInfoAdapter fileTaskInfoAdapter = new FileTaskInfoAdapter(getContext(), fileList);
                                    rc_file.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                                    rc_file.setAdapter(fileTaskInfoAdapter);
                                    fileTaskInfoAdapter.notifyDataSetChanged();
                                } else {
                                    rc_file.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void canCompleteOrEditTask(){
        tv_progressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserComplete userComplete = new UserComplete();
                Bundle bundle = new Bundle();
                bundle.putString("groupName", tv_groupName.getText().toString());
                bundle.putString("taskName", taskName.getText().toString());
                bundle.putString("taskDescription", taskReadmore.getText().toString());
                bundle.putString("groupDescription", tv_description.getText().toString());
                bundle.putString("taskStartTime", taskStart.getText().toString());
                bundle.putString("tastDueTime", taskEnd.getText().toString());
                bundle.putDouble("taskProgress", progressBar.getProgress());

                userComplete.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_task_info, userComplete)
                        .commit();
            }
        });

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTaskFragment editTaskFragment = new EditTaskFragment();

                Bundle bundle = new Bundle();
                bundle.putString("groupName", tv_groupName.getText().toString());
                bundle.putString("groupDescription", tv_description.getText().toString());
                bundle.putString("taskName", taskName.getText().toString());
                bundle.putString("taskDescription", taskReadmore.getText().toString());
                editTaskFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_task_info, editTaskFragment)
                        .commit();

                Log.d("taskName", taskName.getText().toString());
            }
        });

        tv_edit_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks");
                taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                            Task task = taskSnapshot.getValue(Task.class);
                            if (task != null && task.getGroupName().equals(tv_groupName.getText().toString())
                                    && task.getTaskName().equals(taskName.getText().toString())) {

                                DatabaseReference updateTaskRef = FirebaseDatabase.getInstance().getReference("tasks").child(taskSnapshot.getKey());
                                int status;
                                double progress = task.getProgressPercent();
                                if(progress==1){
                                    status = 1;
                                }
                                else {
                                    status = -1;
                                }
                                updateTaskRef.child("status").setValue(status);

                                DataSnapshot assignedUsersSnapshot = taskSnapshot.child("assignedUsers");
                                for (DataSnapshot memberSnapshot : assignedUsersSnapshot.getChildren()) {
                                    Member member = memberSnapshot.getValue(Member.class);
                                    String memberKey = memberSnapshot.getKey();

                                    if (member != null) {
                                        if (member.getComplete() != 1) {
                                            member.setComplete(0);
                                            DatabaseReference update = taskRef.child(taskSnapshot.getKey()).child("assignedUsers").child(memberKey);
                                            update.setValue(member);
                                        }
                                    }
                                }

                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Xử lý onCancelled
                    }
                });

                Bundle bundle = new Bundle();
                bundle.putString("groupName", tv_groupName.getText().toString());
                bundle.putString("groupDescription", tv_description.getText().toString());
                GroupDetailsFragment groupDetailsFragment = new GroupDetailsFragment();
                groupDetailsFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_task_info, groupDetailsFragment)
                        .commit();
            }
        });
    }

    public void canUploadTask(){
        fileList =new ArrayList<>();
        adapter = new UploadFileAdapter(fileList, getContext());
        rc_upload.setAdapter(adapter);

        adapter.setItemClickListener(new RecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    openFilePicker();
                } else {
                    String fileName = fileList.get(position - 1);
                }
            }
        });

        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                                DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks");
                                taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                                            Task task = taskSnapshot.getValue(Task.class);
                                            ArrayList<Member> members = new ArrayList<>();
                                            if (task != null && task.getGroupName().equals(tv_groupName.getText().toString())
                                                    && task.getTaskName().equals(taskName.getText().toString())) {

                                                DataSnapshot assignedUsersSnapshot = taskSnapshot.child("assignedUsers");
                                                for (DataSnapshot memberSnapshot : assignedUsersSnapshot.getChildren()) {
                                                    Member member = memberSnapshot.getValue(Member.class);

                                                    if (member != null && member.getName().equals(username)) {
                                                        String memberKey = memberSnapshot.getKey();
                                                        Log.d("memberKey", memberKey);

                                                        member.setComplete(1);
                                                        member.setFile(fileList);

                                                        String taskKey = taskSnapshot.getKey();

                                                        DatabaseReference updateTaskRef = taskRef.child(taskKey).child("assignedUsers").child(memberKey);
                                                        updateTaskRef.setValue(member);

                                                        break;
                                                    }
                                                }
                                                members = task.getAssignedUsers();
                                                DatabaseReference updateTaskRef = taskRef.child(taskSnapshot.getKey());
                                                double progress = (double) 1/members.size();
                                                updateTaskRef.child("progressPercent").setValue(progress);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        // Xử lý onCancelled
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Bundle bundle = new Bundle();
                    bundle.putString("groupName", tv_groupName.getText().toString());
                    bundle.putString("groupDescription", tv_description.getText().toString());
                    GroupDetailsFragment groupDetailsFragment = new GroupDetailsFragment();
                    groupDetailsFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_task_info, groupDetailsFragment)
                            .commit();
                }
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();
            if (selectedFileUri != null) {
                // Lấy tên file từ Uri
                String fileName = getFileNameFromUri(selectedFileUri);
                // Thêm vào danh sách
                fileList.add(fileName);
                // Cập nhật RecyclerView
                adapter.notifyDataSetChanged();
            }
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return uri.getPath();
    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        try (Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                fileName = cursor.getString(displayNameIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
