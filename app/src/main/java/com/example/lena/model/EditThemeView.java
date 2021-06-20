package com.example.lena.model;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lena.HomeActivity;
import com.example.lena.PreviewThemeActivity;
import com.example.lena.R;

public class EditThemeView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ConstraintLayout root;
    private TextView name;
    private TextView description;
    private ImageView image;
    private ImageButton deleteBtn;

    private Dialog dialog;
    private Button dismissDialogBtn;
    private Button deleteThemeDialogBtn;

    public EditThemeView(ConstraintLayout root) {
        super(root);
        this.root = root;
        name = root.findViewById(R.id.editThemeName);
        image = root.findViewById(R.id.editThemeIV);
        deleteBtn = root.findViewById(R.id.editThemeDeleteBtn);

        LayoutInflater inflater = (LayoutInflater) getRoot().getContext().getSystemService( getRoot().getContext().LAYOUT_INFLATER_SERVICE );

        View view = inflater.inflate(R.layout.theme_delete_dialog, null);
        dialog = new Dialog(getRoot().getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent_dialog_white);

        dismissDialogBtn = view.findViewById(R.id.dismissThemeDialogBtn);
        deleteThemeDialogBtn = view.findViewById(R.id.deleteThemeDialogBtn);

        deleteBtn.setOnClickListener(this);
        image.setOnClickListener(this);

        dismissDialogBtn.setOnClickListener(this);
        deleteThemeDialogBtn.setOnClickListener(this);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public TextView getName() {
        return name;
    }

    public TextView getDescription() {
        return description;
    }

    public ImageView getImage() {
        return image;
    }

    public ImageButton getDeleteBtn() {
        return deleteBtn;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editThemeIV:
                Intent i = new Intent(getRoot().getContext(), PreviewThemeActivity.class);
                i.putExtra("name", name.getText().toString());
                getRoot().getContext().startActivity(i);
                break;
            case R.id.editThemeDeleteBtn:
                dialog.show();
                break;
            case R.id.dismissThemeDialogBtn:
                dialog.dismiss();
                break;
            case R.id.deleteThemeDialogBtn:
                dialog.dismiss();
                Toast.makeText(getRoot().getContext(), "Se ha eliminado el tema", Toast.LENGTH_LONG).show();
                break;

        }
    }
}
