package com.example.tprom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private EditText ed_username, ed_password, ed_email, ed_password_confirm;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions signInOptions;
    private GoogleSignInClient signinClient;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth= FirebaseAuth.getInstance();

        ed_username=findViewById(R.id.rg_teit_user);
        ed_email=findViewById(R.id.rg_teit_validEmail);
        ed_password=findViewById(R.id.rg_teit_pw);
        ed_password_confirm=findViewById(R.id.rg_teit_pwconfirm);

        TextView tv_next = findViewById(R.id.tv_next);
        TextView tv_signin = findViewById(R.id.tv_signin);

        tv_next.setOnClickListener(v -> next());
        tv_signin.setOnClickListener(v -> signin());

        //đăng nhập bằng google
        TextView tv_signinGoogle = findViewById(R.id.tv_google);

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        signinClient = GoogleSignIn.getClient(this,signInOptions);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null) {
            loginWithGoogle();
        }

        tv_signinGoogle.setOnClickListener(v -> signIn());
    }

    //đăng nhập bằng google
    void signIn(){
        Intent signInIntent = signinClient.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                loginWithGoogle();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }
    //hàm mở class Main khi đăng nhập bằng google
    void loginWithGoogle(){
        finish();
        Intent intent=new Intent(Register.this,MainActivity.class);
        startActivity(intent);
    }
    //mở class Login khi đăng kí thành công
    private void next(){
        String username, email, password, password_confirm;
        username=ed_username.getText().toString();
        email=ed_email.getText().toString();
        password=ed_password.getText().toString();
        password_confirm=ed_password_confirm.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Vui lòng nhập Username hoặc Email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Vui lòng nhập địa chỉ email Email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Vui lòng nhập Password!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password_confirm)){
            Toast.makeText(this,"Vui lòng nhập Password để xác nhận!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(password_confirm)){
            Toast.makeText(this, "Vui lòng nhập Password và Password xác nhận khớp với nhau!",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this,"Tài khoản tạo thành công!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Tài khoản tạo thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Tạo tài khoản thất bại.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //mở class Login khi bấm button sign in
    private void signin(){
        Intent i = new Intent(Register.this, Login.class);
        startActivity(i);
    }

}