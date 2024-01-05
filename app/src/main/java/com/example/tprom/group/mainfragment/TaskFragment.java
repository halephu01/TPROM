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
import com.example.tprom.group.adapters.TaskAdapter;
import com.example.tprom.properties.Task;
import com.example.tprom.properties.User;

import java.util.ArrayList;
import java.util.Date;

public class TaskFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Task> tasks;

    TextView tv_groupName,tv_description;

    boolean isAdmin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_description=view.findViewById(R.id.tv_description);
        tv_groupName=view.findViewById(R.id.tv_groupName);

        recyclerView=view.findViewById(R.id.task_rv);
        isAdmin=true;
        tasks=new ArrayList<>();
        TaskAdapter taskAdapter=new TaskAdapter(this.getContext(),tasks,isAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();

        if (getActivity() != null) {
            MainActivity mainActivity = (MainActivity) getActivity();

            TextView tv_onTop  = mainActivity.findViewById(R.id.tv_TopMainText);
            TextView tv_back = mainActivity.findViewById(R.id.btn_back);
            ImageView img_avatar = mainActivity.findViewById(R.id.iv_TopMainAvatar);

            tv_onTop.setText("Task");
            tv_back.setVisibility(View.VISIBLE);
            img_avatar.setVisibility(View.GONE);
            tv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupDetailsFragment groupDetailsFragment = new GroupDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("groupName", tv_groupName.getText().toString());
                    bundle.putString("groupDescription", tv_description.getText().toString());
                    groupDetailsFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentDetailGroup, groupDetailsFragment)
                            .commit();
                    tv_onTop.setText("Details");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            String groupName = bundle.getString("groupName");
            String description = bundle.getString("groupDescription");
            tv_groupName.setText(groupName);
            tv_description.setText(description);
        }
    }
}