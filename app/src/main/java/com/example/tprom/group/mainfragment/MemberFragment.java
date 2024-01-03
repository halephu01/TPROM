package com.example.tprom.group.mainfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.example.tprom.group.GroupItem;
import com.example.tprom.group.adapters.MemberAdapter;
import com.example.tprom.group.adapters.MiniMemberAdapter;
import com.example.tprom.properties.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MemberFragment extends Fragment {
    ArrayList<User> users;
    RecyclerView ListMember;
    TextView tv_members;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        users=new ArrayList<>();
        ListMember = view.findViewById(R.id.details_recycle_mem);
        tv_members = view.findViewById(R.id.details_tv_members);

        MemberAdapter memberAdapter= new MemberAdapter(this.getContext(),users);
        ListMember.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));

        ListMember.setAdapter(memberAdapter);
        memberAdapter.notifyDataSetChanged();

        if (getActivity() != null) {
            MainActivity mainActivity = (MainActivity) getActivity();

            TextView tv_onTop  = mainActivity.findViewById(R.id.tv_TopMainText);
            TextView tv_back = mainActivity.findViewById(R.id.btn_back);
            ImageView img_avatar = mainActivity.findViewById(R.id.iv_TopMainAvatar);

            tv_onTop.setText("Members");
            tv_back.setVisibility(View.VISIBLE);
            img_avatar.setVisibility(View.GONE);
            tv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_details_members, new GroupDetailsFragment())
                            .commit();
                    tv_onTop.setText("Details");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }

        Bundle bundle2 = getArguments();
        if(bundle2!=null){
            String groupName = bundle2.getString("groupName");
            Log.d("group", "group" + groupName);


            DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("groups");
            groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                List<String> members;

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                        GroupItem memberName = memberSnapshot.getValue(GroupItem.class);
                        if (memberName.groupName.toString().equals(groupName)) {
                            members = memberName.getMembers();

                            members.size();
                        }
                    }
                    for(int i=0;i<members.size();i++){
                        users.add(new User(i, members.get(i), "1","1",-1));
                    }
                    tv_members.setText(String.valueOf(members.size())+" members");
                    memberAdapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("MemberFragment", "Failed to read members: " + databaseError.getMessage());
                }
            });


        }
    }
}
