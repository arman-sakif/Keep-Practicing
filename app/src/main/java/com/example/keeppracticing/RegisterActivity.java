package com.example.keeppracticing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button register;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById((R.id.register));

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_pass = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass))
                    Toast.makeText(RegisterActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                else if(txt_pass.length() < 6)
                    Toast.makeText(RegisterActivity.this, "too small, too weak. Eat protein", Toast.LENGTH_SHORT).show();
                else if(!txt_email.endsWith(".com"))
                    Toast.makeText(RegisterActivity.this, "WHOA! What kinda email is this!", Toast.LENGTH_SHORT).show();
                else
                    registerUser(txt_email,txt_pass);

            }
        });


    }

    private void registerUser(String txt_email, String txt_pass) {

        auth.createUserWithEmailAndPassword(txt_email, txt_pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this, "WELCOME ABOARD MATE!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }

                else
                    Toast.makeText(RegisterActivity.this, "failure is the pillar of success. Try again", Toast.LENGTH_SHORT).show();

            }
        });
    }
}