package com.example.tprom;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tprom.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInOptions signInOptions;
    private GoogleSignInClient signInClient;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for layouts
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadFragment(new ProfileFragment());
        binding.mainNavigationMenu.setOnItemSelectedListener(this::onNavigationItemSelected);


        //Button btSignout = findViewById(R.id.profile_logout);

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        signInClient = GoogleSignIn.getClient(this,signInOptions);
//
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
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
                startActivity(new Intent(MainActivity.this,Login.class));
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
            case R.id.main_home:
                loadFragment(new HomeFragment());
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