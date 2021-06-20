package com.example.lena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.lena.model.EditThemeView;
import com.example.lena.model.Theme;
import com.example.lena.model.ThemeView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserEditThemesAdapter extends RecyclerView.Adapter<EditThemeView> {

    private ArrayList<Theme> themes;
    private FirebaseFirestore firestore;

    public UserEditThemesAdapter(){
        themes = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
    }

    public void addTheme (Theme theme){
        themes.add(theme);
        this.notifyDataSetChanged();
    }

    @Override
    public EditThemeView onCreateViewHolder(ViewGroup parent, int viewType) {
        //XML -> View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.editthemerow, parent, false);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        EditThemeView editThemeView = new EditThemeView(rowroot);
        return editThemeView;
    }

    @Override
    public void onBindViewHolder(EditThemeView holder, int position) {
        holder.getName().setText(themes.get(position).getName());
        Glide.with(holder.getRoot()).load(themes.get(position).getImage()).centerCrop().transform(new GranularRoundedCorners(54, 54, 54,54)).into(holder.getImage());
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
