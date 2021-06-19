package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class EditPlantsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private ImageView plantInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plants);

        backBtn = findViewById(R.id.editPlantsBackBtn);
        plantInfoBtn = findViewById(R.id.plantInfoImageView);

        backBtn.setOnClickListener(this);
        plantInfoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editPlantsBackBtn:
                finish();
                break;
            case R.id.plantInfoImageView:
                Intent i = new Intent(this, PlantInformationActivity.class);
                startActivity(i);
                break;
        }
    }
}