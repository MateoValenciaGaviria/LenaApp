package com.example.lena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.lena.model.Theme;
import com.example.lena.model.ThemeView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserThemesAdapter extends RecyclerView.Adapter<ThemeView> {

    private ArrayList<Theme> themes;
    private FirebaseFirestore firestore;

    public UserThemesAdapter(){
        themes = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
    }

    public void addTheme (Theme theme){
        themes.add(theme);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThemeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //XML -> View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.themerow, parent, false);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        ThemeView themeView = new ThemeView(rowroot);
        return themeView;
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeView holder, int position) {
        holder.getName().setText(themes.get(position).getName());
        Glide.with(holder.getRoot()).load(themes.get(position).getImage()).centerCrop().transform(new GranularRoundedCorners(64, 64, 0,0)).into(holder.getImage());
        switch (themes.get(position).getName()){
            case "Mountain":
                holder.setNameContainer(0);
                break;
            case "Sunflower":
                holder.setNameContainer(1);
                break;
            case "Forest":
                holder.setNameContainer(2);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return themes.size();
    }

    public void clear(){
        themes.clear();
        this.notifyDataSetChanged();
    }
}
