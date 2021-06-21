package com.example.lena.model;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class EditThemeView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ConstraintLayout root;
    private TextView name;
    private TextView description;
    private ImageView image;
    private ImageButton deleteBtn;

    private Dialog dialog;
    private Button dismissDialogBtn;
    private Button deleteThemeDialogBtn;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    private Theme currentTheme;

    public EditThemeView(ConstraintLayout root) {
        super(root);
        this.root = root;
        name = root.findViewById(R.id.editThemeName);
        image = root.findViewById(R.id.editThemeIV);
        deleteBtn = root.findViewById(R.id.editThemeDeleteBtn);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

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
                loadTheme(name.getText().toString());
                dialog.dismiss();
                break;

        }
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
                            deleteTheme();
                        }
                    }
                }
        );
    }

    public void deleteTheme(){
        firestore.collection("users").document(auth.getCurrentUser().getUid())
                .update("themes", FieldValue.arrayRemove(currentTheme)).addOnCompleteListener(
                task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getRoot().getContext(), "Se ha eliminado el tema "+currentTheme.getName(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
