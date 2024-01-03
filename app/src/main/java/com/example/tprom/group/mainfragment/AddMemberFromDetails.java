package com.example.tprom.group.mainfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tprom.MainActivity;
import com.example.tprom.R;

public class AddMemberFromDetails extends Fragment {
    RecyclerView rc_mem;
    TextView tv_mem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rc_mem = view.findViewById(R.id.details_recycle_mem);
        tv_mem = view.findViewById(R.id.details_tv_members);

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
                            .replace(R.id.fragmentDetailGroup, new GroupDetailsFragment())
                            .commitAllowingStateLoss();
                    tv_onTop.setText("Details");
                    tv_back.setVisibility(View.GONE);
                    img_avatar.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
