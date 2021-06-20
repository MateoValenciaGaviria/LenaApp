package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lena.model.Theme;
import com.example.lena.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EditThemeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private RecyclerView editThemesList;

    private User myUser;

    private LinearLayoutManager linearLayoutManager;
    private UserEditThemesAdapter userEditThemesAdapter;
    private TextView themesCount;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_theme);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        backBtn = findViewById(R.id.editThemesBackBtn);
        editThemesList = findViewById(R.id.edit_themes_RV);
        themesCount = findViewById(R.id.themesCountTV);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        editThemesList.setLayoutManager(linearLayoutManager);

        userEditThemesAdapter = new UserEditThemesAdapter();
        editThemesList.setAdapter(userEditThemesAdapter);

        backBtn.setOnClickListener(this);
        myUser = new User(auth.getCurrentUser().getUid(), "Prueba", "prueba@gmail.com");
        loadUserThemes();
    }

    public void loadUserThemes(){
        if(myUser != null){
            firestore.collection("users").document(myUser.getId()).get().addOnCompleteListener(
                    task -> {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            myUser = documentSnapshot.toObject(User.class);
                            ArrayList<Theme> userThemes = myUser.getThemes();
                            themesCount.setText("MIS TEMAS ("+userThemes.size()+")");
                            for (int i = 0; i < userThemes.size(); i++){
                                userEditThemesAdapter.addTheme(userThemes.get(i));
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editThemesBackBtn:
                finish();
                break;

        }
    }
}