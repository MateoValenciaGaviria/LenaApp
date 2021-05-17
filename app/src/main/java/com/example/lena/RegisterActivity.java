package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lena.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerBtn;
    private ImageButton back;
    private EditText userName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        registerBtn = findViewById(R.id.btn_register);
        back = findViewById(R.id.registerBackBtn);

        userName = findViewById(R.id.user_ET);
        email = findViewById(R.id.usermail_ET);
        password = findViewById(R.id.userpassword_ET);
        confirmPassword = findViewById(R.id.confirmpassword_ET);

        registerBtn.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                FirebaseUser fireBaseUser = auth.getCurrentUser();
                                User user = new User(
                                        fireBaseUser.getUid(),
                                        userName.getText().toString(),
                                        email.getText().toString()
                                );
                                firestore.collection("users").document(user.getId()).set(user).addOnCompleteListener(
                                        firestoreTask -> {
                                            if(firestoreTask.isSuccessful()){
                                                sendVerification();
                                            }
                                        }
                                );
                            }else{
                                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                );
                break;
            case R.id.registerBackBtn:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    private void sendVerification() {
        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(
                task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(this, "Hemos enviado un correo de verificación, revisa tu bandeja de entrada", Toast.LENGTH_LONG).show();
                        auth.signOut();
                        Intent j = new Intent(this, LoginActivity.class);
                        startActivity(j);
                        finish();
                    }else{
                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}