package com.example.tprom.group.mainfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.example.tprom.group.adapters.AddMemberAdapter;
import com.example.tprom.properties.Member;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CreateGroupFragment extends Fragment {
    EditText ed_teamname, ed_description;
    TextView tv_create, tv_addMem,tv_member;

    RecyclerView rc_member;
    private List<Member> updatedMembers = new ArrayList<>();


    private DatabaseReference dataB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataB = FirebaseDatabase.getInstance("https://tprom-ac5ce-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ed_teamname = view.findViewById(R.id.ed_teamname);
        ed_description = view.findViewById(R.id.ed_description);

        tv_addMem = view.findViewById(R.id.tv_addmem);
        tv_create = view.findViewById(R.id.create);
        tv_member = view.findViewById(R.id.member);

        LinearLayout ll_addmem = view.findViewById(R.id.ll_addmem);
        rc_member = view.findViewById(R.id.rc_member);

        if (getActivity() != null) {
            MainActivity mainActivity = (MainActivity) getActivity();

            TextView tv_onTop  = mainActivity.findViewById(R.id.tv_TopMainText);
            TextView tv_back = mainActivity.findViewById(R.id.btn_back);
            ImageView img_avatar = mainActivity.findViewById(R.id.iv_TopMainAvatar);

            tv_onTop.setText("Create");
            tv_back.setVisibility(View.VISIBLE);
            img_avatar.setVisibility(View.GONE);
            tv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentGroup, new GroupFragment())
                            .commitAllowingStateLoss();
                    tv_onTop.setText("Teams");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }
        rc_member.setLayoutManager(new LinearLayoutManager(getContext()));

        AddMemberAdapter adapter = new AddMemberAdapter(getContext(), updatedMembers);
        rc_member.setAdapter(adapter);

        tv_addMem.setOnClickListener(new View.OnClickListener() {
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

                        updatedMembers.add(new Member(memberName, memberRole,-1));

                        rc_member.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));

                        updateMembersRecycleVIew(updatedMembers);

                        dialog.dismiss();
                    }
                });

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setVisibility(View.GONE);
            }
        });


        tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference groupReference = dataB.child("groups");
                groupReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    long numberOfGroups;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            numberOfGroups = snapshot.getChildrenCount();
                        }

                        String groupId = String.valueOf(numberOfGroups +1);
                        DatabaseReference updateGroup = dataB.child("groups").child(groupId);

                        String teamname = ed_teamname.getText().toString();

                        if(TextUtils.isEmpty(teamname)){
                            Toast.makeText(getActivity(),"Vui lòng nhập tên cho nhóm!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String description = ed_description.getText().toString();

                        if(TextUtils.isEmpty(description)){
                            Toast.makeText(getActivity(),"Vui lòng nhập mô tả cho nhóm!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Map<String, Object> groupData = new HashMap<>();
                        groupData.put("groupId", String.valueOf(groupId));
                        groupData.put("groupName", teamname);
                        groupData.put("groupDescription", description);
                        groupData.put("members", updatedMembers);

                        updateGroup.updateChildren(groupData);

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentGroup, new GroupFragment())
                                .commitAllowingStateLoss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void updateMembersRecycleVIew(List<Member> members) {
        updatedMembers = members;
        AddMemberAdapter adapter = new AddMemberAdapter(getContext(), updatedMembers);
        rc_member.setAdapter(adapter);
    }

    private void updateMembersOnFirebase(String groupId, List<String> members) {
        DatabaseReference groupReference = dataB.child("groups").child(groupId);
        groupReference.child("members").setValue(members);
    }
}
