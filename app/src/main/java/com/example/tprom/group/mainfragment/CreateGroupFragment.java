package com.example.tprom.group.mainfragment;

import static android.content.DialogInterface.*;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CreateGroupFragment extends Fragment {
    EditText ed_teamname, ed_description;
    TextView tv_create, tv_addMem;

    RecyclerView rc_member;
    private List<String> updatedMembers = new ArrayList<>();


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

                EditText addmember = dialogView.findViewById(R.id.dialog_addmember_username);
                TextView addTextView = dialogView.findViewById(R.id.dialog_addmember_add);

                AlertDialog dialog = builder.create();
                dialog.show();

                addTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String memberName = addmember.getText().toString();

                        updatedMembers.add(memberName);

                        rc_member.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));
                        int itemCount = rc_member.getAdapter().getItemCount();

                        if(itemCount>3){
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, 100);
                            ll_addmem.setLayoutParams(layoutParams);
                        }



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
                char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                char groupId = alphabet[new Random().nextInt(alphabet.length)];

                DatabaseReference groupReference = dataB.child("groups").child(String.valueOf(groupId));

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
                groupData.put("groupName", teamname);
                groupData.put("groupDescription", description);
                groupData.put("members", updatedMembers);
                groupReference.updateChildren(groupData);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentGroup, new GroupFragment())
                        .commitAllowingStateLoss();
            }
        });
    }

    private void updateMembersRecycleVIew(List<String> members) {
        updatedMembers = members;

        // Cập nhật RecyclerView thông qua Adapter
        AddMemberAdapter adapter = new AddMemberAdapter(getContext(), updatedMembers);
        rc_member.setAdapter(adapter);
    }

    private void updateMembersOnFirebase(String groupId, List<String> members) {
        DatabaseReference groupReference = dataB.child("groups").child(groupId);

        groupReference.child("members").setValue(members);
    }
}
