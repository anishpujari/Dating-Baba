package com.example.datingbaba;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailRegister, passwordRegister, confPassword;
    private Button registerButton;
    private TextView loginRedirect;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        emailRegister = findViewById(R.id.editTextEmail);
        passwordRegister = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonSignUp);
        loginRedirect = findViewById(R.id.loginhere);
        confPassword = findViewById(R.id.editTextConfirmPassword);

        registerButton.setOnClickListener(v -> registerUser());
        loginRedirect.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        });
    }

    private void registerUser() {

        String email = emailRegister.getText().toString().trim();
        String password = passwordRegister.getText().toString().trim();
        String confpassword = confPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confpassword)) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Validate RKNEC email
        if (!email.endsWith("@rknec.edu")) {
            emailRegister.setError("Only RKNEC emails allowed!");
            return;
        }

        // Validate Password Match
        if (!password.equals(confpassword)) {
            confPassword.setError("Passwords do not match!");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
