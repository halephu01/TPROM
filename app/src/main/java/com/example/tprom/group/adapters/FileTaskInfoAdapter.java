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
import com.example.tprom.properties.Member;

import java.util.List;

public class FileTaskInfoAdapter extends RecyclerView.Adapter<FileTaskInfoAdapter.ViewHolder>{
    private List<String> fileList;
    Context context;

    public FileTaskInfoAdapter(Context context, List<String> fileList) {
        this.fileList = fileList;
        this.context = context;
    }

    @NonNull
    @Override
    public FileTaskInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_taskinfo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileTaskInfoAdapter.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = holder;
        String fileName = fileList.get(position); // Trừ 1 vì có một element upload
        itemViewHolder.tv_namefile.setText(fileName);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_namefile;
        ImageView imageView,imageView2;
        TextView tv_size;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_namefile = itemView.findViewById(R.id.tv_namefile);
            imageView = itemView.findViewById(R.id.imageView);
            imageView2 = itemView.findViewById(R.id.imageView2);
            tv_size = itemView.findViewById(R.id.tv_size);
        }
    }
}
