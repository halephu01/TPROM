package com.example.tprom.group;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.R;
import com.example.tprom.UploadFileAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateGroupFragment extends Fragment {
    EditText ed_teamname, ed_description,ed_addrole, ed_addmem;
    TextView tv_create, tv_addRole, tv_addMem;
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
        ed_addmem = view.findViewById(R.id.ed_addrole);
        ed_addrole = view.findViewById(R.id.ed_addrole);

        tv_addRole = view.findViewById(R.id.ed_addrole);
        tv_addMem = view.findViewById(R.id.ed_addmem);
        tv_create = view.findViewById(R.id.create);





        tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

                // Lấy một chữ cái ngẫu nhiên
                char groupId = alphabet[new Random().nextInt(alphabet.length)];


                DatabaseReference groupReference = dataB.child("groups").child(String.valueOf(groupId));

                String teamname = ed_teamname.getText().toString();
                String description = ed_description.getText().toString();
                String role = ed_addrole.getText().toString();
                String mem = ed_addmem.getText().toString();

                Map<String, Object> groupData = new HashMap<>();
                groupData.put("teamname", teamname);
                groupData.put("description", description);
                groupData.put("role", role);
                groupData.put("member", mem);

                // Sử dụng updateChildren() để cập nhật dữ liệu
                groupReference.setValue(groupData);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentGroup, new GroupFragment())
                        .commitAllowingStateLoss();
            }
        });
    }
}
