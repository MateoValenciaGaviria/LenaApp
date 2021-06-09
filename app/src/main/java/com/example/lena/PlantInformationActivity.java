package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PlantInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_information);

        backBtn = findViewById(R.id.plantInformationBackBtn);

        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plantInformationBackBtn:
                finish();
                break;
        }
    }
}