package com.example.lena.model;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lena.EditThemeActivity;
import com.example.lena.HomeFragment;
import com.example.lena.R;
import com.example.lena.UserThemesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GlobalThemeView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ConstraintLayout root;
    private TextView name;
    private ImageView image;
    private ConstraintLayout nameContainer;
    private ImageButton likeBtn;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    private User myUser;

    public GlobalThemeView(ConstraintLayout root, User user) {
        super(root);
        this.root = root;
        this.myUser = user;
        name = root.findViewById(R.id.globalThemeNameTV);
        image = root.findViewById(R.id.globalThemeImageIV);
        nameContainer = root.findViewById(R.id.globalThemeNameContainer);
        likeBtn = root.findViewById(R.id.likeGlobalThemeBtn);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        likeBtn.setOnClickListener(this);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public TextView getName() {
        return name;
    }

    public ImageView getImage() {
        return image;
    }

    public ConstraintLayout getNameContainer() {
        return nameContainer;
    }

    public ImageButton getLikeBtn() {
        return likeBtn;
    }

    public void setGlobalNameContainer(int result){
        switch (result){
            case 0:
                nameContainer.setBackgroundResource(R.drawable.water_theme_card_bg);
                break;
            case 1:
                nameContainer.setBackgroundResource(R.drawable.forest_theme_card_bg);
                break;
            case 2:
                nameContainer.setBackgroundResource(R.drawable.mountain_theme_card_bg);
                break;
            case 3:
                nameContainer.setBackgroundResource(R.drawable.iceland_theme_card_bg);
                break;
            case 4:
                nameContainer.setBackgroundResource(R.drawable.darkforest_theme_card_bg);
                break;
            case 5:
                nameContainer.setBackgroundResource(R.drawable.sunflower_theme_card_bg);
                break;
            case 6:
                nameContainer.setBackgroundResource(R.drawable.nature_theme_card_bg);
                break;
            case 7:
                nameContainer.setBackgroundResource(R.drawable.island_theme_card_bg);
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.likeGlobalThemeBtn:
                firestore.collection("themes").whereEqualTo("name", name.getText().toString()).get().addOnCompleteListener(
                        task1 -> {
                            if(task1.isSuccessful()){
                                ArrayList<Theme> globalThemes = new ArrayList<>();
                                for (QueryDocumentSnapshot documentSnapshot : task1.getResult()){
                                    Theme currentTheme = documentSnapshot.toObject(Theme.class);
                                    globalThemes.add(currentTheme);
                                }
                                if(globalThemes.size() != 0){
                                    Theme newUserTheme = globalThemes.get(0);
                                    firestore.collection("users").document(auth.getCurrentUser().getUid())
                                            .update("themes", FieldValue.arrayUnion(newUserTheme)).addOnCompleteListener(
                                            task2 -> {
                                                if(task2.isSuccessful()){
                                                    for (int i = 0; i < myUser.getThemes().size(); i++){
                                                        if(myUser.getThemes().get(i).getName().equals(newUserTheme.getName())){
                                                            Toast.makeText(getRoot().getContext(), "Ya tienes este tema", Toast.LENGTH_LONG).show();
                                                            return;
                                                        }
                                                    }
                                                    Toast.makeText(getRoot().getContext(), "Se ha agregado el tema " + newUserTheme.getName() + " a tu colección!", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                    );
                                }
                            }
                        }
                );
                break;
        }
    }
}
