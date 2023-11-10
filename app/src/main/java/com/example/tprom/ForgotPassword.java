package com.example.tprom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth= FirebaseAuth.getInstance();

        EditText edEmail = findViewById(R.id.fogotpw_email);
        TextView tvNext = findViewById(R.id.fogotpw_button_next);
        TextView tvBack = findViewById(R.id.fogotpw_back);

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgotPassword.this,"Reset password thành công",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgotPassword.this, ForgotPassword_Confirmation.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });

        tvBack.setOnClickListener(v->loginScreen());
    }

    private void loginScreen(){
        Intent intent = new Intent(ForgotPassword.this,Login.class);
        startActivity(intent);
    }
}