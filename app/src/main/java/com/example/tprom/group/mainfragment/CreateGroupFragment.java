package com.example.tprom.group.mainfragment;

import static android.content.DialogInterface.*;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    EditText ed_teamname, ed_description, ed_addmem;
    TextView tv_create, tv_addMem;

    GridView lv_member;
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
        ed_addmem = view.findViewById(R.id.ed_addmem);

        tv_addMem = view.findViewById(R.id.tv_addMem);
        tv_create = view.findViewById(R.id.create);

        lv_member = view.findViewById(R.id.lv_member);

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

                        updateMembersGridView(updatedMembers);

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
                if(teamname == null){
                    Toast.makeText(getActivity(),"Vui lòng nhập tên nhóm",Toast.LENGTH_SHORT).show();
                    return;
                }
                String description = ed_description.getText().toString();
                if(description==null){
                    Toast.makeText(getActivity(),"Vui lòng nhập mô tả nhóm", Toast.LENGTH_SHORT).show();
                }

                Map<String, Object> groupData = new HashMap<>();
                groupData.put("teamname", teamname);
                groupData.put("description", description);

                groupData.put("members", updatedMembers);

                groupReference.updateChildren(groupData);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentGroup, new GroupFragment())
                        .commitAllowingStateLoss();
            }
        });
    }

    private void updateMembersGridView(List<String> members) {
        AddMemberAdapter adapter = new AddMemberAdapter(getContext(), members);
        lv_member.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void updateMembersOnFirebase(String groupId, List<String> members) {
        DatabaseReference groupReference = dataB.child("groups").child(groupId);

        groupReference.child("members").setValue(members);
    }
}
