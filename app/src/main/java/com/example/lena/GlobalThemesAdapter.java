package com.example.lena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.lena.model.GlobalThemeView;
import com.example.lena.model.Theme;
import com.example.lena.model.ThemeView;
import com.example.lena.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GlobalThemesAdapter extends RecyclerView.Adapter<GlobalThemeView> {

    private ArrayList<Theme> themes;
    private FirebaseFirestore firestore;

    private User myUser;

    public GlobalThemesAdapter(){
        themes = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
    }

    public void addTheme (Theme theme){
        themes.add(theme);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GlobalThemeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //XML -> View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.globalthemerow, parent, false);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        GlobalThemeView globalThemeView = new GlobalThemeView(rowroot, myUser);
        return globalThemeView;
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalThemeView holder, int position) {
        holder.getName().setText(themes.get(position).getName());
        Glide.with(holder.getRoot()).load(themes.get(position).getImage()).centerCrop().transform(new GranularRoundedCorners(64, 64, 0,0)).into(holder.getImage());
        switch (themes.get(position).getName()){
            case "Water":
                holder.setGlobalNameContainer(0);
                break;
            case "Forest":
                holder.setGlobalNameContainer(1);
                break;
            case "Mountain":
                holder.setGlobalNameContainer(2);
                break;
            case "Iceland":
                holder.setGlobalNameContainer(3);
                break;
            case "Dark Forest":
                holder.setGlobalNameContainer(4);
                break;
            case "Sunflower":
                holder.setGlobalNameContainer(5);
                break;
            case "Nature":
                holder.setGlobalNameContainer(6);
                break;
            case "Island":
                holder.setGlobalNameContainer(7);
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

    public void setUser(User user){
        this.myUser = user;
    }
}
