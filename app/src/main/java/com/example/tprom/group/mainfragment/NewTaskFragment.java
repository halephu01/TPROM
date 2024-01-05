package com.example.tprom.group.mainfragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.example.tprom.RecyclerViewClickListener;
import com.example.tprom.group.GroupAdapter;
import com.example.tprom.group.mainfragment.UploadFileAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewTaskFragment extends Fragment {
    private RecyclerView upload, assigned;

    private List<Drawable>  assignedList;
    private UploadFileAdapter adapter;
    private List<String> fileList;
    private TextView startdate, duedate;
    private Calendar calendar;

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

        tv_description = view.findViewById(R.id.tv_description);
        tv_groupName = view.findViewById(R.id.tv_groupName);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String groupName = bundle.getString("groupName");
            String description = bundle.getString("description");
            tv_groupName.setText(groupName);
            tv_description.setText(description);
        }

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
        fileList.add("File 1");
        fileList.add("File 2");
        adapter = new UploadFileAdapter(fileList, getContext());
        upload.setAdapter(adapter);

        adapter.setItemClickListener(new RecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    Toast.makeText(getContext(), "Upload file", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "File " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });


        RecyclerView recyclerAssigned= view.findViewById(R.id.recyclerview_assigned);
        recyclerAssigned.setLayoutManager(new GridLayoutManager(getContext(), 5));

        assignedList = new ArrayList<>();
        // Thêm các Drawable tượng trưng cho người được giao nhiệm vụ vào assignedList
        assignedList.add(getResources().getDrawable(R.drawable.mem));
        assignedList.add(getResources().getDrawable(R.drawable.mem));
        assignedList.add(getResources().getDrawable(R.drawable.mem));

        AssignedUserAdapter assignedAdapter = new AssignedUserAdapter(assignedList);
        assigned.setAdapter(assignedAdapter);

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
    }
}