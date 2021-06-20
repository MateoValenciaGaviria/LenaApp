package com.example.lena.model;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lena.PreviewThemeActivity;
import com.example.lena.R;

public class ThemeView extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        image.setOnClickListener(this);
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
            case R.id.themeImageIV:
                Intent i = new Intent(getRoot().getContext(), PreviewThemeActivity.class);
                i.putExtra("name", name.getText().toString());
                getRoot().getContext().startActivity(i);
                break;
        }
    }
}
