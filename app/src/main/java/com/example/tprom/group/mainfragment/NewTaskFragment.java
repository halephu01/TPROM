package com.example.tprom.group.mainfragment;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.example.tprom.group.GroupItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTaskFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    private List<String> uploadedFiles;
    private GridView gridView;
    private ArrayAdapter<String> adapter;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private TextView mStartTimeTextView;
    private TextView mDueTimeTextView;
    String fileName;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_newtask, container, false);
        uploadedFiles = new ArrayList<>();
        gridView = view.findViewById(R.id.gridView);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, uploadedFiles);
        gridView.setAdapter(adapter);

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
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentDetailGroup, new GroupDetailsFragment())
                            .commitAllowingStateLoss();
                    tv_onTop.setText("Details");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }


        TextView uploadTextView = view.findViewById(R.id.create);

        mStartTimeTextView = view.findViewById(R.id.startTime);
        mDueTimeTextView = view.findViewById(R.id.dueTime);

        uploadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filePath = uploadedFiles.get(position);
                // Do something with the selected file
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(filePath), "*/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mStartTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(mStartTimeTextView);
            }
        });

        mDueTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(mDueTimeTextView);
            }
        });
        Bundle bundle3 = getArguments();

        String groupName = bundle3.getString("groupName");

        TextView createTextView = view.findViewById(R.id.create_new_task);
        EditText ed_taskName=view.findViewById(R.id.ed_taskname);
        EditText ed_description = view.findViewById(R.id.ed_discription);

        createTextView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick (View v){
                DatabaseReference groupsRef = FirebaseDatabase.getInstance().getReference().child("groups");
                groupsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    String groupId;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                            GroupItem taskName = taskSnapshot.getValue(GroupItem.class);
                            if (taskName.groupName.toString().equals(groupName)) {
                                groupId = taskName.GroupId();
                            }
                        }
                        DatabaseReference updateTask = FirebaseDatabase.getInstance().getReference("groups").child(groupId);
                        updateTask.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                DatabaseReference taskRef = groupsRef.child("tasks");
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
                                String starttime = mStartTimeTextView.getText().toString();
                                String duetime = mDueTimeTextView.getText().toString();

                                Map<String, Object> taskData = new HashMap<>();
                                taskData.put("taskName", taskname);
                                taskData.put("taskDescription", discription);
                                taskData.put("taskStartTime", starttime);
                                taskData.put("taskDueTime", duetime);
                                taskData.put("files", fileName);
                                taskRef.updateChildren(taskData);
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
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentDetailGroup, new GroupDetailsFragment())
                        .commit();
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            fileName = getFileNameFromUri(fileUri); // Lấy tên tệp tin từ Uri
            uploadedFiles.add(fileName);
            adapter.notifyDataSetChanged();
        }
    }


    // Phương thức để lấy tên tệp tin cùng với đuôi file từ Uri
    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (nameIndex != -1) {
                    fileName = cursor.getString(nameIndex);
                }
                cursor.close();
            }
        }
        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }

        // Thêm đuôi file (extension) nếu có
        String extension = MimeTypeMap.getFileExtensionFromUrl(fileName);
        if (extension != null && !extension.isEmpty()) {
            fileName = fileName + "." + extension;
        }

        return fileName;
    }

    private void showDateTimePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar timeCalendar = Calendar.getInstance();
                int hour = timeCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = timeCalendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(Calendar.YEAR, year);
                        selectedCalendar.set(Calendar.MONTH, monthOfYear);
                        selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedCalendar.set(Calendar.MINUTE, minute);

                        Date selectedDate = selectedCalendar.getTime();
                        String selectedTimeString = mDateFormat.format(selectedDate);

                        textView.setText(selectedTimeString);
                    }
                }, hour, minute, true);

                timePickerDialog.show();
            }
        }, year, month, day);

        datePickerDialog.show();
    }

}
