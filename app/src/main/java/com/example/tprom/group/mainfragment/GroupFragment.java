package com.example.tprom.group.mainfragment;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tprom.R;
import com.example.tprom.group.GroupAdapter;
import com.example.tprom.group.GroupItem;
import com.example.tprom.properties.Group;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupFragment extends Fragment implements GroupAdapter.RecyclerViewClickListener{
    EditText ed_findteam;
    TextView tv_request,tv_create,tv_find;
    RecyclerView recyclerView;
    ArrayList<GroupItem> groupItems;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_findteam = view.findViewById(R.id.group_et_findteam);
        tv_create=view.findViewById(R.id.group_btn_create);
        tv_request=view.findViewById(R.id.group_tv_request);
        tv_find=view.findViewById(R.id.group_btn_find);
        recyclerView=view.findViewById(R.id.group_rv);
        groupItems=new ArrayList<>();


        if (groupItems != null) {
            GroupAdapter groupAdapter = new GroupAdapter(getContext(), groupItems, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(groupAdapter);
            groupAdapter.notifyDataSetChanged();

            DatabaseReference groupsRef = FirebaseDatabase.getInstance().getReference("groups");

            groupsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    groupItems.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới

                    // Duyệt qua tất cả các dữ liệu con trong node "groups"
                    for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                        // Lấy dữ liệu của mỗi group
                        Group group = groupSnapshot.getValue(Group.class);

                        if (group != null) {

                            String groupName = group.getGroupName();
                            String groupDescription = group.getGroupDescription();

                            groupItems.add(new GroupItem(groupName, groupDescription, "Phu", 4));
                        }
                    }

                    groupAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Lỗi khi truy xuất dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        }


        tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentGroup, new CreateGroupFragment())
                        .commit();

                tv_request.setVisibility(View.GONE);
                tv_create.setVisibility(View.GONE);
                tv_find.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        GroupItem clickedGroup = groupItems.get(position);
        clickedGroup.GroupName();

        Bundle bundle = new Bundle();
        bundle.putString("groupName", clickedGroup.GroupName());
        bundle.putString("groupOwner", clickedGroup.GroupOwner());
        bundle.putString("description", clickedGroup.GroupDescription());

        GroupDetailsFragment groupDetailsFragment = new GroupDetailsFragment();
        groupDetailsFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentGroup, groupDetailsFragment)
                .commit();

        tv_request.setVisibility(View.GONE);
        tv_create.setVisibility(View.GONE);
        tv_find.setVisibility(View.GONE);
    }
}