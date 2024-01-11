package com.example.tprom.group.mainfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.MainActivity;
import com.example.tprom.OnCheckedChangeListener;
import com.example.tprom.R;
import com.example.tprom.group.GroupItem;
import com.example.tprom.group.adapters.AssignedUserAdapter;
import com.example.tprom.properties.Member;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignedUserFragment extends Fragment implements OnCheckedChangeListener {
    CheckBox chb_selectAll;
    RecyclerView rcv_assignedUser;
    TextView tv_addUser;
    AssignedUserAdapter assignedUserAdapter;

    ArrayList<Member> memberList;
    List<Member> selectedUsers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_user, container, false);
    }

    @SuppressLint({"ResourceType", "CutPasteId", "UseCompatLoadingForDrawables"})
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chb_selectAll = view.findViewById(R.id.select_all);
        rcv_assignedUser = view.findViewById(R.id.recycler_view_select_user);
        tv_addUser = view.findViewById(R.id.add_select_user);

        TextView tv_groupName = view.findViewById(R.id.tv_groupName);
        TextView tv_description = view.findViewById(R.id.tv_description);

        AssignedUserAdapter assignedUserAdapter = new AssignedUserAdapter(getContext(), memberList);
        assignedUserAdapter = new AssignedUserAdapter(getContext(), memberList);
        assignedUserAdapter.setOnCheckedChangeListener(this);

        rcv_assignedUser.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rcv_assignedUser.setAdapter(assignedUserAdapter);
        assignedUserAdapter.notifyDataSetChanged();

        Bundle bundle = getArguments();
        if (bundle != null) {
            tv_groupName.setText(bundle.getString("groupName"));
            tv_description.setText(bundle.getString("groupDescription"));

            DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("groups");
            groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                ArrayList<Member> members = new ArrayList<>();
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (memberList == null) {
                        memberList = new ArrayList<>(); // Khởi tạo memberList nếu nó là null
                    } else {
                        memberList.clear(); // Xóa danh sách hiện tại trước khi thêm các mục mới
                    }

                    for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                        GroupItem memberName = memberSnapshot.getValue(GroupItem.class);
                        if (memberName.groupName.toString().equals(tv_groupName.getText().toString())) {
                            members.clear(); // Clear members before adding items
                            members.addAll(memberName.getMembers()); // AddAll instead of assignment

                            for (int i = 0; i < members.size(); i++) {
                                String name = members.get(i).getName();
                                memberList.add(new Member(name));
                            }
                            break;
                        }
                    }

                    AssignedUserAdapter assignedUserAdapter = new AssignedUserAdapter(getContext(), memberList);
                    rcv_assignedUser.setAdapter(assignedUserAdapter);
                    assignedUserAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle onCancelled
                }
            });
        }

        tv_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelectedUsers();

                Bundle result = new Bundle();
                result.putParcelableArrayList("selectedUsers", (ArrayList<? extends Parcelable>) selectedUsers);
                getParentFragmentManager().setFragmentResult("selectedUsers", result);

                getParentFragmentManager().popBackStack();
            }
        });

        if (getActivity() != null) {
            MainActivity mainActivity = (MainActivity) getActivity();

            TextView tv_onTop = mainActivity.findViewById(R.id.tv_TopMainText);
            TextView tv_back = mainActivity.findViewById(R.id.btn_back);
            ImageView img_avatar = mainActivity.findViewById(R.id.iv_TopMainAvatar);

            tv_onTop.setText("Select Users");
            tv_back.setVisibility(View.VISIBLE);
            img_avatar.setVisibility(View.GONE);
            tv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewTaskFragment newTaskFragment = new NewTaskFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("groupName", tv_groupName.getText().toString());
                    bundle.putString("groupDescription", tv_description.getText().toString());
                    newTaskFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_select_user, newTaskFragment)
                            .commit();
                    tv_onTop.setText("New Task");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }


        AssignedUserAdapter finalAssignedUserAdapter = assignedUserAdapter;

        chb_selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < memberList.size(); i++) {
                    memberList.get(i).setSelected(isChecked);
                }
                finalAssignedUserAdapter.notifyDataSetChanged();
            }
        });
    }


    private void saveSelectedUsers() {
        selectedUsers.clear(); // Xóa danh sách hiện tại trước khi thêm
        for (Member member : memberList) {
            if (member.isSelected()) {
                selectedUsers.add(member);
            }
        }
    }


    @Override
    public void onCheckedChange(int position, boolean isChecked) {
        memberList.get(position).setSelected(isChecked);
        assignedUserAdapter.notifyDataSetChanged();
    }

}
