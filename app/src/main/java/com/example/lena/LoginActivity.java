package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatButton loginBtn;
    private AppCompatButton loginFacebookBtn;
    private AppCompatButton loginGoogleBtn;
    private EditText userET;
    private EditText passwordET;
    private LinearLayout registerLN;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.btn_login);
        loginFacebookBtn = findViewById(R.id.btn_login_facebook);
        loginGoogleBtn = findViewById(R.id.btn_login_google);
        registerLN = findViewById(R.id.register_LN);

        loginBtn.setOnClickListener(this);
        loginFacebookBtn.setOnClickListener(this);
        loginGoogleBtn.setOnClickListener(this);
        registerLN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Intent i = new Intent(this, HomeActivity.class);
                startActivity(i);
                finish();
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
}