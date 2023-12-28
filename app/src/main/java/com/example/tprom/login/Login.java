package com.example.tprom.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/** @noinspection ALL*/
public class Login extends AppCompatActivity {
    private EditText ed_username, ed_password;
    private FirebaseAuth mAuth;
    private GoogleSignInClient signinClient;
    private GoogleSignInOptions signInOptions;
    private DatabaseReference dataB;
    int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //đăng nhập với username và passowrd
        mAuth= FirebaseAuth.getInstance();

        ed_username=findViewById(R.id.tiet_login_user);
        ed_password=findViewById(R.id.tiet_login_pw);

        TextView tv_login = findViewById(R.id.tv_login);
        TextView tv_register = findViewById(R.id.tv_register);

        tv_login.setOnClickListener(v -> login());

        tv_register.setOnClickListener(v -> register());

        //đăng nhập bằng google
        TextView tv_signGoogle = findViewById(R.id.tv_login_google);

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("YOUR_WEB_CLIENT_ID")
                .requestEmail()
                .build();
        signinClient = GoogleSignIn.getClient(this,signInOptions);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            loginWithGoogle();
        }

        tv_signGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //quên mật khẩu
        TextView tv_forgotPassword=findViewById(R.id.login_forgotpassword);
        tv_forgotPassword.setOnClickListener(v-> forgotPassword());

        dataB = FirebaseDatabase.getInstance("https://tprom-ac5ce-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

    }

    //đăng nhập bằng google
    void signIn(){
        Intent signInIntent = signinClient.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loginWithGoogle();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //hàm mở class Main khi đăng nhập bằng google
    void loginWithGoogle(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //hàm để mở class register
    private void register(){
        Intent intent = new Intent(Login.this,Register.class);
        startActivity(intent);
    }

    //hàm đăng nhập với username và password
    private void login(){
        String username, password;

        username=ed_username.getText().toString();
        password=ed_password.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Vui lòng nhập Username hoặc Email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Vui lòng nhập Password!",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Đăng nhập không thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //hàm mở class quên mật khẩu
    private void forgotPassword(){
        Intent intent = new Intent(Login.this, ForgotPassword.class);
        startActivity(intent);
    }

}