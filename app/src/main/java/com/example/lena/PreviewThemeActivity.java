package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.lena.model.Theme;
import com.example.lena.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PreviewThemeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button deleteTheme;
    private Button defineTheme;
    private ImageButton backBtn;
    private ImageView likeBtn;

    private Button deleteThemeDialogBtn;
    private Button dismissDialogBtn;

    private Dialog dialog;
    private ConstraintLayout dialogConstraintLayout;

    private TextView themeName;
    private TextView themeTittle;
    private TextView currentTime;
    private ImageView currentImage;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    private String myName;
    private Theme currentTheme;
    private User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_theme);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        myName = getIntent().getExtras().getString("name");

        defineTheme = findViewById(R.id.defineThemePreviewBtn);
        deleteTheme = findViewById(R.id.deleteThemePreviewBtn);
        backBtn = findViewById(R.id.previewThemeBackBtn);

        themeName = findViewById(R.id.previewThemeNameTV);
        themeTittle = findViewById(R.id.previewThemeTittleTV);
        currentTime = findViewById(R.id.previewThemeTimeTV);
        currentImage = findViewById(R.id.previewThemeImageIV);
        likeBtn = findViewById(R.id.previewThemeLikeBtn);

        View view = getLayoutInflater().inflate(R.layout.theme_delete_dialog, null);
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent_dialog_white);

        dialogConstraintLayout = view.findViewById(R.id.delete_theme_dialog);

        dismissDialogBtn = view.findViewById(R.id.dismissThemeDialogBtn);
        deleteThemeDialogBtn = view.findViewById(R.id.deleteThemeDialogBtn);

        dismissDialogBtn.setOnClickListener(this);
        deleteThemeDialogBtn.setOnClickListener(this);

        backBtn.setOnClickListener(this);
        defineTheme.setOnClickListener(this);
        deleteTheme.setOnClickListener(this);

        resolverMyUser();
        loadTheme(myName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.defineThemePreviewBtn:
                firestore.collection("users").document(auth.getCurrentUser().getUid())
                        .update("currentTheme", currentTheme.getName()).addOnCompleteListener(
                        task2 -> {
                            if(task2.isSuccessful()){
                                if(myUser.getCurrentTheme().equals(currentTheme.getName())){
                                    Toast.makeText(this, currentTheme.getName()+" ya es tu tema actual", Toast.LENGTH_LONG).show();
                                    return;
                                }else{
                                    likeBtn.setVisibility(View.VISIBLE);
                                    Toast.makeText(this, "Se ha definido el tema " + currentTheme.getName() + " como tu tema actual!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
                break;
            case R.id.deleteThemePreviewBtn:
                dialog.show();
                break;
            case R.id.dismissThemeDialogBtn:
                dialog.dismiss();
                break;
            case R.id.deleteThemeDialogBtn:
                firestore.collection("users").document(auth.getCurrentUser().getUid())
                        .update("themes", FieldValue.arrayRemove(currentTheme)).addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(this, "Se ha eliminado el tema "+currentTheme.getName(), Toast.LENGTH_LONG).show();
                            }
                        }
                );
                dialog.dismiss();
                finish();
                break;
            case R.id.previewThemeBackBtn:
                finish();
                break;
        }
    }

    public void resolverMyUser(){
        FirebaseUser fbuser = auth.getCurrentUser();
        firestore.collection("users").document(fbuser.getUid()).get().addOnCompleteListener(
                dbusertask -> {
                    DocumentSnapshot snapshot = dbusertask.getResult();
                    myUser = snapshot.toObject(User.class);
                }
        );
    }

    public void loadTheme(String name){
        firestore.collection("themes").whereEqualTo("name", name).get().addOnCompleteListener(
                task1 -> {
                    if(task1.isSuccessful()){
                        ArrayList<Theme> globalThemes = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : task1.getResult()){
                            Theme currentTheme = documentSnapshot.toObject(Theme.class);
                            globalThemes.add(currentTheme);
                        }
                        if(globalThemes.size() != 0){
                            currentTheme = globalThemes.get(0);
                            loadInformation();
                        }
                    }
                }
        );
    }

    public void loadInformation(){
        themeName.setText(currentTheme.getName());
        themeTittle.setText(currentTheme.getName());
        Glide.with(this).load(currentTheme.getImage()).centerCrop().transform(new GranularRoundedCorners(100, 100, 70,70)).into(currentImage);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        String time = format.format(calendar.getTime());
        currentTime.setText(time);
        if(myUser.getCurrentTheme().equals(currentTheme.getName())){
            likeBtn.setVisibility(View.VISIBLE);
        }
    }
}