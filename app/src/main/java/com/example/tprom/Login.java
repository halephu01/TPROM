package com.example.tprom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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


/** @noinspection ALL*/
public class Login extends AppCompatActivity {
    private EditText ed_username, ed_password;
    private FirebaseAuth mAuth;
    private GoogleSignInClient signinClient;
    private GoogleSignInOptions signInOptions;
    int RC_SIGN_IN = 20;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();

        ed_username=findViewById(R.id.ed_username);
        ed_password=findViewById(R.id.ed_password);

        TextView tv_login = findViewById(R.id.tv_login);
        TextView tv_signGoogle = findViewById(R.id.tv_login_google);
        TextView tv_register = findViewById(R.id.tv_register);

        tv_login.setOnClickListener(v -> login());

        tv_register.setOnClickListener(v -> register());

        /*ed_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    int drawableEnd = 2;
                    if (event.getRawX() >= (ed_Password.getRight() - ed_Password.getCompoundDrawables()[drawableEnd].getBounds().width())) {

                    }
                }
                return false;
            }
        });*/

        //đăng nhập bằng google
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
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
    }
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

    void loginWithGoogle(){
        finish();
        Intent intent=new Intent(Login.this,MainActivity.class);
        startActivity(intent);
    }


    private void register(){
        Intent i = new Intent(Login.this,Register.class);
        startActivity(i);
    }

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

}