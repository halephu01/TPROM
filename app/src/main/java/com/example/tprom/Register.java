package com.example.tprom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private EditText ed_username, ed_password, ed_email, ed_password_confirm;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth= FirebaseAuth.getInstance();

        ed_username=findViewById(R.id.ed_username);
        ed_email=findViewById(R.id.ed_email);
        ed_password=findViewById(R.id.ed_password);
        ed_password_confirm=findViewById(R.id.ed_password_confirm);

        TextView tv_next = findViewById(R.id.tv_next);

        TextView tv_signin = findViewById(R.id.tv_signin);

        tv_next.setOnClickListener(v -> next());

        tv_signin.setOnClickListener(v -> signin());

    }
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

        /*if(!password.equals(password_confirm)){
        /    Toast.makeText(this, "Vui lòng nhập Password và Password xác nhận khớp với nhau!",Toast.LENGTH_SHORT).show();
        /    return;
        }*/

        mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this,"Tài khoản tạo thành công!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void signin(){
        Intent i = new Intent(Register.this, Login.class);
        startActivity(i);
    }

}