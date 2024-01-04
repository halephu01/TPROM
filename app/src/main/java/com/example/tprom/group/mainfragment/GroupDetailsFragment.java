package com.example.tprom.group.mainfragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.example.tprom.group.GroupItem;
import com.example.tprom.group.adapters.MemberAdapter;
import com.example.tprom.group.adapters.MiniMemberAdapter;
import com.example.tprom.group.adapters.TaskAdapter;
import com.example.tprom.properties.Member;
import com.example.tprom.properties.Task;
import com.example.tprom.properties.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupDetailsFragment extends Fragment {
    ImageView GroupAvatar;
    TextView GroupName,Description;
    TextView AddMember,AddTask;
    RecyclerView ListMember,ListTask;
    ArrayList<User> users;
    ArrayList<Task> tasks;

    MiniMemberAdapter miniMemberAdapter;

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

        TaskAdapter taskAdapter = new TaskAdapter(this.getContext(),tasks,isAdmin);

        miniMemberAdapter= new MiniMemberAdapter(this.getContext(),users);

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
                            .commit();
                    tv_onTop.setText("Teams");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }

        Bundle bundle = getArguments();
        if(bundle != null){
            String groupName = bundle.getString("groupName");
            String description = bundle.getString("groupDescription");

            GroupName.setText(groupName);
            Description.setText(description);
        }

        DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("groups");
        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Member> members = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                    GroupItem memberName = memberSnapshot.getValue(GroupItem.class);
                    if (memberName.groupName.toString().equals(GroupName.getText().toString())) {
                        members = memberName.getMembers();
                        for (Member member : members) {
                            String name = member.getName();
                            users.add(new User(0,name,"1","1",-1));
                        }
                        break;
                    }
                }
                miniMemberAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("GroupFragment", "Failed to read members: " + databaseError.getMessage());
            }
        });

        TextView tv_member = view.findViewById(R.id.groupdetail_tv_member);
        TextView tv_task = view.findViewById(R.id.groupdetail_tv_task);

        tv_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2= new Bundle();
                String groupName = GroupName.getText().toString();
                String description = Description.getText().toString();

                bundle2.putString("groupName",groupName);
                bundle2.putString("groupDescription",description);

                MemberFragment memberFragment = new MemberFragment();
                memberFragment.setArguments(bundle2);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentDetailGroup,memberFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        tv_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentDetailGroup,new TaskFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        InitSample();
    }

    private void InitSample(){
        tasks.add(new Task(1,1,1,"Task 1","hihihaha",0.6,new Date(123,11,23,12,23),0,1,users));
        tasks.add(new Task(2,1,1,"Task 2","hahahoho",0,new Date(123,11,23,12,23),0,1,users));
        tasks.add(new Task(2,1,1,"Task 3","hohohehe",0.9,new Date(123,11,23,12,23),0,1,users));
        tasks.add(new Task(2,1,1,"Task 4","hohohehehaha",0.9,new Date(123,11,23,12,23),0,1,users));
    }

    private void setButton(){
        AddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mcontext = getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                View dialogView = LayoutInflater.from(mcontext).inflate(R.layout.dialog_addmember, null);

                builder.setView(dialogView);

                EditText addMember = dialogView.findViewById(R.id.dialog_addmember_username);
                EditText addRole = dialogView.findViewById(R.id.dialog_addrole_username);
                TextView addTextView = dialogView.findViewById(R.id.dialog_addmember_add);

                AlertDialog dialog = builder.create();
                dialog.show();

                addTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String memberName = addMember.getText().toString();
                        String memberRole = addRole.getText().toString();

                        DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("groups");
                        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            String groupId;
                            ArrayList<Member> members = new ArrayList<>();
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot memberSnapshot : snapshot.getChildren()) {
                                    GroupItem groupItem = memberSnapshot.getValue(GroupItem.class);
                                    if (groupItem.groupName.equals(GroupName.getText().toString())) {
                                        groupId = groupItem.GroupId();
                                        members = groupItem.getMembers();
                                    }
                                }

                                int idMember = members.size() + 1;

                                DatabaseReference updateMember = FirebaseDatabase.getInstance().getReference("groups").child(groupId);
                                String memberId = String.valueOf(idMember);
                                Member newMember = new Member(memberName, memberRole, -1);
                                members.add(newMember);

                                updateMember.child("members").setValue(members);

                                dialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle onCancelled
                            }
                        });

                        users.add(new User(0,memberName,"1","1",-1));
                        miniMemberAdapter.notifyDataSetChanged();
                    }
                });


                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setVisibility(View.GONE);
            }
        });

        AddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle3= new Bundle();
                String groupName = GroupName.getText().toString();

                bundle3.putString("groupName",groupName);

                NewTaskFragment newTaskFragment = new NewTaskFragment();
                newTaskFragment.setArguments(bundle3);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentDetailGroup, newTaskFragment)
                        .commit();
            }
        });
    }
}