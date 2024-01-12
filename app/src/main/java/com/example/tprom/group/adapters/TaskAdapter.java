package com.example.tprom.group.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;
import com.example.tprom.RecyclerViewClickListener;
import com.example.tprom.group.GroupAdapter;
import com.example.tprom.group.mainfragment.TaskFragment;
import com.example.tprom.properties.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private Context context;
    private ArrayList<Task> tasks;
    private boolean isAdmin;
    private RecyclerViewClickListener clickListener;

    // Khởi tạo adapter và truyền RecyclerViewClickListener
    public TaskAdapter(Context context, ArrayList<Task> tasks, boolean isAdmin, RecyclerViewClickListener clickListener) {
        this.context = context;
        this.tasks = tasks;
        this.isAdmin = isAdmin;
        this.clickListener = clickListener;
    }

    public void setClickListener(RecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, @SuppressLint("RecyclerView") int position) {
        Task task=tasks.get(position);
        holder.Title.setText(task.getTaskName());
        holder.Description.setText(task.getTaskDescription());
        holder.Time.setText(task.getTaskDueTime());
        MiniMemberAdapter2 miniMemberAdapter=new MiniMemberAdapter2(this.context,task.getAssignedUsers());
        holder.MiniAvatar.setAdapter(miniMemberAdapter);
        holder.MiniAvatar.setLayoutManager(new LinearLayoutManager(this.context,RecyclerView.HORIZONTAL,false));
        miniMemberAdapter.notifyDataSetChanged();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(v, position);
                }
            }
        });

        //Neu co tep dinh kem thi hien thi so luong
        if(task.getNumberOfFiles()>0){
            holder.NumberOfAttachment.setVisibility(View.VISIBLE);
            holder.NumberOfAttachment.setText(Integer.toString(task.getNumberOfFiles()));
        }
        //Neu la admin thi xem duoc muc do hoan thanh cua task
        if(isAdmin){
            int progress=(int)(task.getProgressPercent()*100);
            holder.progressBar.setMax(100);
            holder.ProgressBarLayout.setVisibility(View.VISIBLE);
            holder.progressBar.setProgress(progress);
            holder.ProgressPercent.setText(Integer.toString(progress)+"%");
        }else{
            holder.ProgressBarLayout.setVisibility(View.GONE);
        }
        if(task.getStatus()==-1){
            holder.background.setBackgroundResource(R.drawable.shape_task_uncompleted);
        }
        else if(task.getStatus()==1){
            holder.background.setBackgroundResource(R.drawable.shape_task_completed);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskHolder extends RecyclerView.ViewHolder {
        TextView Title,Description,NumberOfAttachment,Time,ProgressPercent;
        LinearLayout ProgressBarLayout;
        ProgressBar progressBar;
        RecyclerView MiniAvatar;
        ConstraintLayout background;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.taskitem_title);
            Description=itemView.findViewById(R.id.taskitem_description);
            NumberOfAttachment=itemView.findViewById(R.id.taskitem_attachment);
            Time=itemView.findViewById(R.id.taskitem_time);
            ProgressPercent=itemView.findViewById(R.id.taskitem_progress);
            ProgressBarLayout=itemView.findViewById(R.id.taskitem_ll_progressbar);
            progressBar=itemView.findViewById(R.id.taskitem_progressbar);
            MiniAvatar=itemView.findViewById(R.id.taskitem_rv_user);
            background=itemView.findViewById(R.id.taskitem_background);
        }
    }
}
