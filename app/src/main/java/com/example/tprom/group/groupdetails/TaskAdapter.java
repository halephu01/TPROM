package com.example.tprom.group.groupdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;
import com.example.tprom.group.GroupAdapter;
import com.example.tprom.properties.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private Context context;
    private ArrayList<Task> tasks;
    boolean isAdmin;
    public TaskAdapter(Context context, ArrayList<Task> tasks,boolean isAdmin) {
        this.context = context;
        this.tasks = tasks;
        this.isAdmin=isAdmin;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task=tasks.get(position);
        holder.Title.setText(task.getNameTask());
        holder.Description.setText(task.getDescribeTask());
        holder.Time.setText(task.getDeadline());
        MiniMemberAdapter2 miniMemberAdapter=new MiniMemberAdapter2(this.context,task.getMember());
        holder.MiniAvatar.setAdapter(miniMemberAdapter);
        holder.MiniAvatar.setLayoutManager(new LinearLayoutManager(this.context,RecyclerView.HORIZONTAL,false));
        miniMemberAdapter.notifyDataSetChanged();
        //Neu co tep dinh kem thi hien thi so luong
        if(task.getNumberOfAttachment()>0){
            holder.NumberOfAttachment.setVisibility(View.VISIBLE);
            holder.NumberOfAttachment.setText(Integer.toString(task.getNumberOfAttachment()));
        }
        //Neu la admin thi xem duoc muc do hoan thanh cua task
        if(isAdmin){
            int progress=(int)(task.getProgressPercent()*100);
            holder.progressBar.setMax(100);
            holder.ProgressBarLayout.setVisibility(View.VISIBLE);
            holder.progressBar.setProgress(progress);
            holder.ProgressPercent.setText(Integer.toString(progress)+"%");
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskHolder extends RecyclerView.ViewHolder{
        TextView Title,Description,NumberOfAttachment,Time,ProgressPercent;
        LinearLayout ProgressBarLayout;
        ProgressBar progressBar;
        RecyclerView MiniAvatar;
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
        }
    }
}
