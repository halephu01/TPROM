package com.example.tprom.group.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;
import com.example.tprom.RecyclerViewClickListener;

import java.util.List;

public class UploadFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_UPLOAD = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<String> fileList;
    private Context context;

    private RecyclerViewClickListener itemClickListener;

    public UploadFileAdapter(List<String> fileList, Context context) {
        this.fileList = fileList;
        this.context = context;
    }

    public void setItemClickListener(RecyclerViewClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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
            uploadViewHolder.tv_upload.setText("Upload File");

            // Xử lý sự kiện khi người dùng nhấn vào element upload
            uploadViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, 0); // Gọi onItemClick với position là 0
                    }
                }
            });
        } else {
            // Xử lý logic cho các item khác
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String fileName = fileList.get(position - 1); // Trừ 1 vì có một element upload
            itemViewHolder.tv_file_name.setText(fileName);

            // Xử lý sự kiện khi người dùng nhấn vào item
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
        ImageView img_upload;
        TextView tv_upload;

        public UploadViewHolder(@NonNull View itemView) {
            super(itemView);
            img_upload = itemView.findViewById(R.id.img_upload_file);
            tv_upload = itemView.findViewById(R.id.tv_upload_file);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_file_name;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_file_name = itemView.findViewById(R.id.tv_file_name);
        }
    }

}