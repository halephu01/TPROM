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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {
    private EditText ed_username, ed_password, ed_email, ed_password_confirm;
    private FirebaseAuth mAuth;
    private DatabaseReference dataB;

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

        dataB = FirebaseDatabase.getInstance("https://tprom-ac5ce-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    }

    //mở class Login khi đăng kí thành công
    private void next() {
        String username, email, password, password_confirm;
        username = ed_username.getText().toString();
        email = ed_email.getText().toString();
        password = ed_password.getText().toString();
        password_confirm = ed_password_confirm.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Vui lòng nhập Username!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password_confirm)) {
            Toast.makeText(this, "Vui lòng nhập Password để xác nhận!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password_confirm)) {
            Toast.makeText(this, "Vui lòng nhập Password và Password xác nhận khớp với nhau!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String userId = mAuth.getCurrentUser().getUid();
                DatabaseReference userReference = dataB.child("users").child(userId);

                Map<String, Object> userData = new HashMap<>();
                userData.put("username", username);
                userData.put("email", email);
                userData.put("password",password);
                userReference.setValue(userData);

                Toast.makeText(this, "Tài khoản tạo thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Tạo tài khoản thất bại.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidGmail(String input) {
        String gmailPattern = "[a-zA-Z0-9._-]+@gmail.com";
        return input.matches(gmailPattern);
    }

    //mở class Login khi bấm button sign in
    private void signin(){
        Intent i = new Intent(Register.this, Login.class);
        startActivity(i);
    }

}