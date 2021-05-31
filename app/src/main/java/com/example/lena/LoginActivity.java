package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lena.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatButton loginBtn;
    private AppCompatButton loginFacebookBtn;
    private AppCompatButton loginGoogleBtn;
    private EditText email;
    private EditText password;
    private LinearLayout registerLN;
    private FirebaseAuth auth;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        loginBtn = findViewById(R.id.btn_login);
        loginFacebookBtn = findViewById(R.id.btn_login_facebook);
        loginGoogleBtn = findViewById(R.id.btn_login_google);
        registerLN = findViewById(R.id.register_LN);

        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);

        loginBtn.setOnClickListener(this);
        loginFacebookBtn.setOnClickListener(this);
        loginGoogleBtn.setOnClickListener(this);
        registerLN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(
                                task -> {
                                    if(task.isSuccessful()){
                                        FirebaseUser fbuser = auth.getCurrentUser();
                                        firestore.collection("users").document(fbuser.getUid()).get().addOnCompleteListener(
                                                        dbusertask -> {
                                                            DocumentSnapshot snapshot = dbusertask.getResult();
                                                            User user = snapshot.toObject(User.class);
                                                            if(fbuser.isEmailVerified()){
                                                                goToUserHomeActivity(user);
                                                            }else {
                                                                Toast.makeText(this, "Debes verificar tu cuenta para poder ingresar. Revisa tu correo electrónico!", Toast.LENGTH_LONG).show();
                                                                auth.signOut();
                                                            }

                                                        }
                                                );
                                    }else{
                                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                break;
            case R.id.btn_login_facebook:

                break;
            case R.id.btn_login_google:

                break;
            case R.id.register_LN:
                Intent j = new Intent(this, RegisterActivity.class);
                startActivity(j);
                finish();
                break;
        }
    }

    public void goToUserHomeActivity(User user) {
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("myUser", user);
        startActivity(i);
        finish();
    }
}