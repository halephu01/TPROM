package com.example.tprom;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UploadFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_UPLOAD = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<String> fileList;
    private Context context;

    public UploadFileAdapter(List<String> fileList, Context context) {
        this.fileList = fileList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_UPLOAD) {
            View view = inflater.inflate(R.layout.item_upload, parent, false);
            return new UploadViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_file, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_UPLOAD) {
            // Xử lý logic cho element upload
            UploadViewHolder uploadViewHolder = (UploadViewHolder) holder;
            uploadViewHolder.textView.setText("Upload File");

            // ...
        } else {
            // Xử lý logic cho các item khác
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String fileName = fileList.get(position - 1); // Trừ 1 vì có một element upload
            itemViewHolder.textView.setText(fileName);
            // Xử lý sự kiện khi người dùng nhấn vào item
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý tương ứng với item được chọn
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return fileList.size() + 1; // +1 để tính cả element upload
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_UPLOAD;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    public static class UploadViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public UploadViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

}