package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PreviewThemeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button deleteTheme;
    private Button defineTheme;

    private Button deleteThemeDialogBtn;
    private Button dismissDialogBtn;

    private Dialog dialog;
    private ConstraintLayout dialogConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_theme);

        defineTheme = findViewById(R.id.defineThemePreviewBtn);
        deleteTheme = findViewById(R.id.deleteThemePreviewBtn);

        View view = getLayoutInflater().inflate(R.layout.theme_delete_dialog, null);
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view);

        dialogConstraintLayout = view.findViewById(R.id.delete_theme_dialog);

        dismissDialogBtn = view.findViewById(R.id.dismissThemeDialogBtn);
        deleteThemeDialogBtn = view.findViewById(R.id.deleteThemeDialogBtn);

        dismissDialogBtn.setOnClickListener(this);
        deleteThemeDialogBtn.setOnClickListener(this);

        defineTheme.setOnClickListener(this);
        deleteTheme.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.defineThemePreviewBtn:
                break;
            case R.id.deleteThemePreviewBtn:
                dialog.show();
                break;
            case R.id.dismissThemeDialogBtn:
                dialog.dismiss();
                break;
            case R.id.deleteThemeDialogBtn:
                Toast.makeText(this, "Se ha eliminado el tema", Toast.LENGTH_LONG).show();
                break;
        }
    }
}