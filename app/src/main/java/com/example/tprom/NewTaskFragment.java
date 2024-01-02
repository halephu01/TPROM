package com.example.tprom;

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
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tprom.group.GroupAdapter;

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
    private TextView startdate, duedate;;
    private Calendar calendar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_task, container, false);
    }
    @SuppressLint({"ResourceType", "CutPasteId", "UseCompatLoadingForDrawables"})
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upload = view.findViewById(R.id.recyclerview_upload);
        assigned = view.findViewById(R.id.recyclerview_assigned);
        RecyclerView recyclerAssigned= view.findViewById(R.id.recyclerview_assigned);
        RecyclerView recyclerUpload = view.findViewById(R.id.recyclerview_upload);
        recyclerUpload.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerAssigned.setLayoutManager(new GridLayoutManager(getContext(), 5));
        fileList =new ArrayList<>();
        fileList.add("File 1");
        fileList.add("File 2");
        fileList.add("File 3");
        fileList.add("File 4");
        fileList.add("File 5");
        adapter = new UploadFileAdapter(fileList, getContext());
        upload.setAdapter(adapter);

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