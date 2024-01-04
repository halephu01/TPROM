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
import com.example.tprom.properties.Member;
import com.example.tprom.properties.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MemberFragment extends Fragment {
    ArrayList<Member> member;
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
        member=new ArrayList<>();

        ListMember = view.findViewById(R.id.details_recycle_mem);
        tv_members = view.findViewById(R.id.details_tv_members);

        MemberAdapter memberAdapter= new MemberAdapter(this.getContext(),member);
        ListMember.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));

        ListMember.setAdapter(memberAdapter);
        memberAdapter.notifyDataSetChanged();

        TextView tv_groupname = view.findViewById(R.id.mem_groupname);
        TextView tv_description = view.findViewById(R.id.mem_description);

        Bundle bundle2 = getArguments();
        if(bundle2!=null){
            String groupName = bundle2.getString("groupName");
            String groupDescription = bundle2.getString("groupDescription");
            tv_groupname.setText(groupName);
            tv_description.setText(groupDescription);

            DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("groups");
            groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                ArrayList<Member> members = new ArrayList<>();
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                        GroupItem memberName = memberSnapshot.getValue(GroupItem.class);
                        if (memberName.groupName.toString().equals(groupName)) {
                            members = memberName.getMembers();
                            members = memberName.getMembers();

                            for (int i = 0; i < members.size(); i++) {
                                String name = members.get(i).getName();
                                String role = members.get(i).getRole();
                                member.add(new Member(name, role, -1));
                            }

                            String number = String.valueOf(members.size());
                            tv_members.setText(number + " members");
                            break;
                        }
                    }

                    memberAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("GroupFragment", "Failed to read members: " + databaseError.getMessage());
                }
            });
        }
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
                    GroupDetailsFragment groupDetailsFragment = new GroupDetailsFragment();
                    Bundle bundle5 = new Bundle();
                    bundle5.putString("groupName", tv_groupname.getText().toString());
                    bundle5.putString("groupDescription", tv_description.getText().toString());
                    groupDetailsFragment.setArguments(bundle5);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_details_members, groupDetailsFragment)
                            .addToBackStack(null)
                            .commit();

                    tv_onTop.setText("Details");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }

            });
        }

    }


}
