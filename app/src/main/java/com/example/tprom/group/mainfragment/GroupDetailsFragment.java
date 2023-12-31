package com.example.tprom.group.mainfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.example.tprom.group.adapters.MiniMemberAdapter;
import com.example.tprom.group.adapters.TaskAdapter;
import com.example.tprom.properties.Task;
import com.example.tprom.properties.User;

import java.util.ArrayList;
import java.util.Date;

public class GroupDetailsFragment extends Fragment {
    ImageView GroupAvatar;
    TextView GroupName,Description;
    TextView AddMember,AddTask;
    RecyclerView ListMember,ListTask;
    ArrayList<User> users;
    ArrayList<Task> tasks;
    boolean isAdmin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GroupAvatar=view.findViewById(R.id.groupdetail_groupavt);
        GroupName=view.findViewById(R.id.groupdetail_groupname);
        Description=view.findViewById(R.id.groupdetail_description);
        AddMember=view.findViewById(R.id.groupdetail_btn_addmember);
        AddTask=view.findViewById(R.id.groupdetail_btn_addtask);
        ListMember=view.findViewById(R.id.groupdetail_rv_member);
        ListTask=view.findViewById(R.id.groupdetail_rv_task);
        tasks =new ArrayList<>();
        users=new ArrayList<>();
        isAdmin=true;
        InitSample();
        TaskAdapter taskAdapter = new TaskAdapter(this.getContext(),tasks,isAdmin);
        MiniMemberAdapter miniMemberAdapter= new MiniMemberAdapter(this.getContext(),users);
        ListTask.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL,false));
        ListMember.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        ListTask.setAdapter(taskAdapter);
        ListMember.setAdapter(miniMemberAdapter);
        taskAdapter.notifyDataSetChanged();
        miniMemberAdapter.notifyDataSetChanged();
        //Neu la admin thi hien 2 button
        if(isAdmin){
            setButton();
        }else{
            AddTask.setVisibility(View.GONE);
            AddMember.setVisibility(View.GONE);
        }

        if (getActivity() != null) {
            MainActivity mainActivity = (MainActivity) getActivity();

            TextView tv_onTop  = mainActivity.findViewById(R.id.tv_TopMainText);
            TextView tv_back = mainActivity.findViewById(R.id.btn_back);
            ImageView img_avatar = mainActivity.findViewById(R.id.iv_TopMainAvatar);

            tv_onTop.setText("Details");
            tv_back.setVisibility(View.VISIBLE);
            img_avatar.setVisibility(View.GONE);
            tv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentDetailGroup, new GroupFragment())
                            .commitAllowingStateLoss();
                    tv_onTop.setText("Teams");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }

    }
    private void InitSample(){
        users.add(new User(1,"Phuc","aa","123",-1));
        users.add(new User(2,"Phuc1","aa","123",-1));
        users.add(new User(3,"Phuc2","aa","123",-1));
        users.add(new User(4,"Phuc3","aa","123",-1));
        tasks.add(new Task(1,1,1,"Task 1","hihihaha",0.6,new Date(123,11,23,12,23),0,1,users));
        tasks.add(new Task(2,1,1,"Task 2","hahahoho",0,new Date(123,11,23,12,23),0,1,users));
        tasks.add(new Task(2,1,1,"Task 3","hohohehe",0.9,new Date(123,11,23,12,23),0,1,users));
        tasks.add(new Task(2,1,1,"Task 4","hohohehehaha",0.9,new Date(123,11,23,12,23),0,1,users));
    }
    private void setButton(){
        AddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        AddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}