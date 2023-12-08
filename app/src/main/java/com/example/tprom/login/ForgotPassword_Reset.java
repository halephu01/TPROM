package com.example.tprom.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tprom.R;

public class ForgotPassword_Reset extends AppCompatActivity {
    TextView tvBack,tvNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_reset);

        tvNext = findViewById(R.id.forgotpw_cf_button_next);
        tvBack = findViewById(R.id.forgotpw_cf_back);
        tvNext.setOnClickListener(v -> screenLogin());
        tvBack.setOnClickListener(v -> screenForgotPassword());
    }

    void screenLogin(){
        Intent intent = new Intent(ForgotPassword_Reset.this, Login.class);
        startActivity(intent);
    }

    void screenForgotPassword(){
        Intent intent = new Intent(ForgotPassword_Reset.this,ForgotPassword.class);
        startActivity(intent);
    }
}