package com.example.lena.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lena.R;

public class ThemeView extends RecyclerView.ViewHolder{

    private ConstraintLayout root;
    private TextView name;
    private ImageView image;
    private ConstraintLayout nameContainer;

    public ThemeView(ConstraintLayout root) {
        super(root);
        this.root = root;
        name = root.findViewById(R.id.themeNameTV);
        image = root.findViewById(R.id.themeImageIV);
        nameContainer = root.findViewById(R.id.themeNameContainer);
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

    public void setNameContainer(int result){
        switch (result){
            case 0:
                nameContainer.setBackgroundResource(R.drawable.mountain_theme_card_bg);
                break;
            case 1:
                nameContainer.setBackgroundResource(R.drawable.sunflower_theme_card_bg);
                break;
            case 2:
                nameContainer.setBackgroundResource(R.drawable.forest_theme_card_bg);
                break;

        }
    }
}
