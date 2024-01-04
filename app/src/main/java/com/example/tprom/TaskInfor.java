package com.example.tprom;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import at.blogc.android.views.ExpandableTextView;


public class TaskInfor extends Fragment {
    private Date deadline;
    final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    private View rootView;
    private RecyclerView upload, file;
    private UploadFileAdapter adapter;
    private FileAdapter adapter2;
    private boolean isExpanded = false;
    private List<String> fileList;
    private List<FileAdapter.FileItem> fileItemList;
    private boolean isAdmin = false;
    private ProgressBar progressBar;
    private TextView complete, complete_ad, edit_ad, percent, tv_progress, tv_upload;
    private RelativeLayout layout_progress, layout_uploadfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_task_infor, container, false);

        final TextView expandableTextView = rootView.findViewById(R.id.expand_readmore);
        final TextView additionalContentTextView = rootView.findViewById(R.id.tv_readmore);

        expandableTextView.setText("Read more");

        expandableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    expandableTextView.setText("Read more");
                    additionalContentTextView.setMaxLines(2); // Thu gọn đoạn văn bản thành 2 dòng
                } else {
                    expandableTextView.setText("Read less");
                    additionalContentTextView.setMaxLines(Integer.MAX_VALUE); // Mở rộng đoạn văn bản hiển thị tối đa
                }
                isExpanded = !isExpanded;
            }
        });

        setStartDate("01/01/2024");
        setEndDate("31/12/2024");

        return rootView;
    }
    @SuppressLint({"ResourceType", "CutPasteId", "UseCompatLoadingForDrawables"})
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout_progress = view.findViewById(R.id.layout_progress);
        layout_uploadfile= view.findViewById(R.id.layout_upload);
        complete = view.findViewById(R.id.tv_complete);
        complete_ad = view.findViewById(R.id.tv_complete_ad);
        edit_ad = view.findViewById(R.id.tv_edit_ad);
        tv_progress = view.findViewById(R.id.tv_progressbar);
        tv_upload = view.findViewById(R.id.upload);
        percent = view.findViewById(R.id.percent);


        upload = view.findViewById(R.id.recyclerview_upload_taskinfo);
        file = view.findViewById(R.id.recyclerview_file_taskinfo);
        RecyclerView recyclerFile = view.findViewById(R.id.recyclerview_file_taskinfo);
        RecyclerView recyclerUpload = view.findViewById(R.id.recyclerview_upload_taskinfo);
        file.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerUpload.setLayoutManager(new GridLayoutManager(getContext(), 3));
        // Kiểm tra giá trị của isAdmin và thực hiện thay đổi giao diện tương ứng
        if (isAdmin) {
            // Ẩn RecyclerView upload
            upload.setVisibility(View.GONE);
            layout_uploadfile.setVisibility(View.GONE);
            tv_upload.setVisibility(View.GONE);
            complete.setVisibility(View.GONE);
            percent.setVisibility(View.VISIBLE);
            complete_ad.setVisibility(View.VISIBLE);
            edit_ad.setVisibility(View.VISIBLE);
            layout_progress.setVisibility(View.VISIBLE);
            tv_progress.setVisibility(View.VISIBLE);



        } else {
            // Hiển thị RecyclerView upload
            upload.setVisibility(View.VISIBLE);
            layout_uploadfile.setVisibility(View.VISIBLE);
            tv_upload.setVisibility(View.VISIBLE);
            complete.setVisibility(View.VISIBLE);
            complete_ad.setVisibility(View.GONE);
            percent.setVisibility(View.GONE);
            edit_ad.setVisibility(View.GONE);
            layout_progress.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
            fileList = new ArrayList<>();
            fileList.add("File 1");
            fileList.add("File 2");
            fileList.add("File 3");
            adapter = new UploadFileAdapter(fileList, getContext());
            upload.setAdapter(adapter);
        }
        fileItemList = new ArrayList<>();
        fileItemList.add(new FileAdapter.FileItem("File 1", "10 MB"));
        fileItemList.add(new FileAdapter.FileItem("File 2", "5 MB"));
        fileItemList.add(new FileAdapter.FileItem("File 3", "3 MB"));
        adapter2 = new FileAdapter(fileItemList,getContext());
        file.setAdapter(adapter2);




    }

    public void setStartDate(String startDate) {
        TextView startDateTextView = rootView.findViewById(R.id.startdate);
        startDateTextView.setText(startDate);
    }

    public void setEndDate(String endDate) {
        TextView endDateTextView = rootView.findViewById(R.id.enddate);
        endDateTextView.setText(endDate);
    }

    public void setAdminStatus(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public String getDeadline() {
        return dateTimeFormat.format(deadline);
    }
}
