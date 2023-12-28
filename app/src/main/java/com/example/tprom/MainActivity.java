package com.example.tprom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tprom.databinding.ActivityMainBinding;
import com.example.tprom.login.Login;
import com.example.tprom.mainfragment.CalendarFragment;
import com.example.tprom.group.GroupFragment;
import com.example.tprom.notification.NotificationFragment;
import com.example.tprom.mainfragment.ProfileFragment;
import com.example.tprom.mainfragment.SlidePageAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInOptions signInOptions;
    private GoogleSignInClient signInClient;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    ActivityMainBinding binding;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for layouts

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
                        bottomNavigationView.getMenu().findItem(R.id.main_group).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.main_calendar).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.main_notification).setChecked(true);
                        break;
                    case 3:
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
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.main_calendar:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.main_notification:
                        viewPager2.setCurrentItem(2);
                        break;
                    case R.id.main_profile:
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


        //Button btSignout = findViewById(R.id.profile_logout);

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        signInClient = GoogleSignIn.getClient(this,signInOptions);
//
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//
//        btSignout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signOut();
//            }
//        });

    }

    void signOut(){
        signInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
    }
    void loadFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_fragment,fragment);
        fragmentTransaction.commit();
    }

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