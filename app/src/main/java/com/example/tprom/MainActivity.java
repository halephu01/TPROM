package com.example.tprom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tprom.databinding.ActivityMainBinding;
import com.example.tprom.login.Login;
import com.example.tprom.mainfragment.CalendarFragment;
import com.example.tprom.group.mainfragment.GroupFragment;
import com.example.tprom.notification.NotificationFragment;
import com.example.tprom.mainfragment.ProfileFragment;
import com.example.tprom.mainfragment.SlidePageAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity{
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    TextView tv_onTop, tv_back;
    ImageView img_avata;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] tvOnTopChange = {"Teams","Calendar", "Notification", "Profile"};
        //for layouts
        tv_onTop = findViewById(R.id.tv_TopMainText);
        tv_back = findViewById(R.id.btn_back);
        img_avata = findViewById(R.id.iv_TopMainAvatar);

        viewPager2=findViewById(R.id.layout_fragment);
        SlidePageAdapter adapter =new SlidePageAdapter(this);
        viewPager2.setAdapter(adapter);
        bottomNavigationView = findViewById(R.id.main_NavigationMenu);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch(position){
                    case 0:
                        tv_onTop.setText(tvOnTopChange[0]);
                        bottomNavigationView.getMenu().findItem(R.id.main_group).setChecked(true);
                        break;
                    case 1:
                        tv_onTop.setText(tvOnTopChange[1]);
                        bottomNavigationView.getMenu().findItem(R.id.main_calendar).setChecked(true);
                        break;
                    case 2:
                        tv_onTop.setText(tvOnTopChange[2]);
                        bottomNavigationView.getMenu().findItem(R.id.main_notification).setChecked(true);
                        break;
                    case 3:
                        tv_onTop.setText(tvOnTopChange[3]);
                        bottomNavigationView.getMenu().findItem(R.id.main_profile).setChecked(true);
                        break;
                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.main_group:
                        tv_onTop.setText(tvOnTopChange[0]);
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.main_calendar:
                        tv_onTop.setText(tvOnTopChange[1]);
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.main_notification:
                        tv_onTop.setText(tvOnTopChange[2]);
                        viewPager2.setCurrentItem(2);
                        break;
                    case R.id.main_profile:
                        tv_onTop.setText(tvOnTopChange[3]);
                        viewPager2.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
//        binding=ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        loadFragment(new ProfileFragment());
//        binding.mainNavigationMenu.setOnItemSelectedListener(this::onNavigationItemSelected);

    }


    void loadFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_fragment,fragment);
        fragmentTransaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_group:
                loadFragment(new GroupFragment());
                break;
            case R.id.main_calendar:
                loadFragment(new CalendarFragment());
                break;
            case R.id.main_notification:
                loadFragment(new NotificationFragment());
                break;
            case R.id.main_profile:
                loadFragment(new ProfileFragment());
                break;
        }
        return true;
    }
}