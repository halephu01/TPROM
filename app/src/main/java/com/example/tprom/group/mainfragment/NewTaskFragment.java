package com.example.tprom.group.mainfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.example.tprom.RecyclerViewClickListener;
import com.example.tprom.group.GroupItem;
import com.example.tprom.group.adapters.AssignedUserAdapter;
import com.example.tprom.group.adapters.MiniAssignedAdapter;
import com.example.tprom.group.adapters.MiniMemberAdapter2;
import com.example.tprom.group.adapters.UploadFileAdapter;
import com.example.tprom.properties.Member;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewTaskFragment extends Fragment {
    private RecyclerView upload, assigned;

    private UploadFileAdapter adapter;
    private List<String> fileList;
    private TextView startdate, duedate;
    private Calendar calendar;
    private TextView createTextView;

    private TextView ed_taskName, ed_description;

    private ArrayList<Member> selectedUsers, selectedUsers2;

    private TextView tv_addAssignee;

    private static final int REQUEST_CODE = 1;

    TextView tv_groupName,tv_description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_task, container, false);
    }
    @SuppressLint({"ResourceType", "CutPasteId", "UseCompatLoadingForDrawables"})
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_addAssignee = view.findViewById(R.id.tv_add_assgined);
        createTextView = view.findViewById(R.id.create_new_task);

        ed_taskName = view.findViewById(R.id.ed_taskname);
        ed_description = view.findViewById(R.id.ed_description);

        tv_description = view.findViewById(R.id.tv_description);
        tv_groupName = view.findViewById(R.id.tv_groupName);


        Bundle bundle = getArguments();
        if (bundle != null) {
            String groupName = bundle.getString("groupName");
            String description = bundle.getString("groupDescription");

            tv_groupName.setText(groupName);
            tv_description.setText(description);
        }

        selectedUsers = new ArrayList<>();
        selectedUsers2 = new ArrayList<>();


        RecyclerView recyclerAssigned= view.findViewById(R.id.recyclerview_assigned);

        getParentFragmentManager().setFragmentResultListener("selectedUsers", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                selectedUsers = result.getParcelableArrayList("selectedUsers");

                MiniAssignedAdapter miniAssignedAdapter = new MiniAssignedAdapter(getContext(), selectedUsers);

                recyclerAssigned.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                recyclerAssigned.setAdapter(miniAssignedAdapter);


                for(int i = 0; i < selectedUsers.size(); i++) {
                    selectedUsers2.add(new Member(selectedUsers.get(i).getName()));
                }

                miniAssignedAdapter.notifyDataSetChanged();

                Log.d("groupname", tv_groupName.getText().toString());
            }
        });


        if (getActivity() != null) {
            MainActivity mainActivity = (MainActivity) getActivity();

            TextView tv_onTop  = mainActivity.findViewById(R.id.tv_TopMainText);
            TextView tv_back = mainActivity.findViewById(R.id.btn_back);
            ImageView img_avatar = mainActivity.findViewById(R.id.iv_TopMainAvatar);

            tv_onTop.setText("New Task");
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
                            .replace(R.id.fragment_newtask, groupDetailsFragment)
                            .commit();
                    tv_onTop.setText("Details");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }

        upload = view.findViewById(R.id.recyclerview_upload);
        assigned = view.findViewById(R.id.recyclerview_assigned);

        RecyclerView recyclerUpload = view.findViewById(R.id.recyclerview_upload);
        recyclerUpload.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fileList =new ArrayList<>();
        adapter = new UploadFileAdapter(fileList, getContext());
        upload.setAdapter(adapter);

        adapter.setItemClickListener(new RecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    openFilePicker();
                } else {
                    String fileName = fileList.get(position - 1); // Trừ đi 1 vì đã thêm "Upload file" ở vị trí 0
                    Toast.makeText(getContext(), "File: " + fileName, Toast.LENGTH_SHORT).show();
                }
            }
        });


        tv_addAssignee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("groupName", tv_groupName.getText().toString());
                bundle.putString("groupDescription", tv_description.getText().toString());

                AssignedUserFragment addAssignFragment = new AssignedUserFragment();
                addAssignFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_newtask, addAssignFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        startdate = view.findViewById(R.id.cal_startdate);
        duedate = view.findViewById(R.id.cal_duedate);

        // Lấy ngày và giờ hiện tại
        calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        final int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        // Định dạng ngày và giờ
        final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        // Thiết lập DatePickerDialog và TimePickerDialog khi người dùng bấm vào TextView
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Lưu ngày được chọn
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Hiển thị TimePickerDialog để cho phép người dùng chọn giờ và phút
                                TimePickerDialog timePickerDialog = new TimePickerDialog(requireActivity(),
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                // Lưu giờ và phút được chọn
                                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                calendar.set(Calendar.MINUTE, minute);

                                                // Hiển thị ngày và giờ được chọn trong TextView
                                                String selectedDateTime = dateTimeFormat.format(calendar.getTime());
                                                startdate.setText(selectedDateTime);
                                            }
                                        }, hourOfDay, minute, true);
                                timePickerDialog.show();
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Lưu ngày được chọn
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Hiển thị TimePickerDialog để cho phép người dùng chọn giờ và phút
                                TimePickerDialog timePickerDialog = new TimePickerDialog(requireActivity(),
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                // Lưu giờ và phút được chọn
                                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                calendar.set(Calendar.MINUTE, minute);

                                                // Hiển thị ngày và giờ được chọn trong TextView
                                                String selectedDateTime = dateTimeFormat.format(calendar.getTime());
                                                duedate.setText(selectedDateTime);
                                            }
                                        }, hourOfDay, minute, true);
                                timePickerDialog.show();
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        TextView createTextView = view.findViewById(R.id.create_new_task);
        EditText ed_taskName=view.findViewById(R.id.ed_taskname);
        EditText ed_description = view.findViewById(R.id.ed_discription);
        createTextView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick (View v){
                DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference().child("tasks");
                tasksRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    long numberOfTasks;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            numberOfTasks = snapshot.getChildrenCount();
                        }
                        String taskId = String.valueOf(numberOfTasks + 1);
                        DatabaseReference updateTask = FirebaseDatabase.getInstance().getReference().child("tasks").child(taskId);

                        String taskname = ed_taskName.getText().toString();
                        if (TextUtils.isEmpty(taskname)) {
                            Toast.makeText(getActivity(), "Vui lòng nhập tên cho task!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String discription = ed_description.getText().toString();

                        if (TextUtils.isEmpty(discription)) {
                            Toast.makeText(getActivity(), "Vui lòng nhập mô tả cho task!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String starttime = startdate.getText().toString();
                        String duetime = duedate.getText().toString();

                        // Convert selectedUsers2 to a list of Maps
                        List<Map<String, Object>> assignedUsersData = new ArrayList<>();
                        for (Member user : selectedUsers2) {
                            assignedUsersData.add(user.toMap());
                        }

                        Map<String, Object> taskData = new HashMap<>();
                        taskData.put("taskId", String.valueOf(taskId));
                        taskData.put("groupName", tv_groupName.getText().toString());
                        taskData.put("taskName", taskname);
                        taskData.put("taskDescription", discription);
                        taskData.put("taskStartTime", starttime);
                        taskData.put("taskDueTime", duetime);
                        taskData.put("files", fileList);
                        taskData.put("assignedUsers", assignedUsersData);
                        taskData.put("status", -1);
                        taskData.put("progressPercent",0);
                        updateTask.updateChildren(taskData);
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
                        .replace(R.id.fragmentDetailGroup, groupDetailsFragment)
                        .commit();
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