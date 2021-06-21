package com.example.lena;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lena.model.Theme;
import com.example.lena.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View root;

    private User myUser;

    private AppCompatButton editThemes;

    private RecyclerView userThemesList;
    private RecyclerView globalThemesList;

    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private UserThemesAdapter userThemesAdapter;
    private GlobalThemesAdapter globalThemesAdapter;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        userThemesList = root.findViewById(R.id.user_themes_horizontal);
        globalThemesList = root.findViewById(R.id.new_themes_vertical);

        editThemes = root.findViewById(R.id.btn_editThemes);

        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        userThemesList.setLayoutManager(linearLayoutManager);

        gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        globalThemesList.setLayoutManager(gridLayoutManager);

        userThemesAdapter = new UserThemesAdapter();
        userThemesList.setAdapter(userThemesAdapter);

        globalThemesAdapter = new GlobalThemesAdapter();
        globalThemesList.setAdapter(globalThemesAdapter);

        editThemes.setOnClickListener(this);

        firestore.collection("users").document(auth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("TAG1", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.e("TAG2", "Current data: " + snapshot.getData());
                    loadFreeThemes();
                } else {
                    Log.e("TAG3", "Current data: null");
                }

            }
        });

        return root;
    }

    public void loadFreeThemes(){
        if(myUser != null){
            userThemesAdapter.clear();
            firestore.collection("users").document(myUser.getId()).get().addOnCompleteListener(
                task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        myUser = documentSnapshot.toObject(User.class);
                        ArrayList<Theme> userThemes = myUser.getThemes();

                        for (int i = 0; i < userThemes.size(); i++){
                            userThemesAdapter.addTheme(userThemes.get(i));
                        }
                    }
                }
            );
        }
    }

    public void loadGlobalThemes(){
        if(myUser != null){
            globalThemesAdapter.setUser(myUser);
            globalThemesAdapter.clear();

            firestore.collection("themes").get().addOnCompleteListener(
                    task -> {
                        if(task.isSuccessful()){
                            ArrayList<Theme> globalThemes = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                Theme currentTheme = documentSnapshot.toObject(Theme.class);
                                globalThemes.add(currentTheme);
                            }
                            for (int i = 0; i < globalThemes.size(); i++){
                                globalThemesAdapter.addTheme(globalThemes.get(i));
                            }
                        }
                    }
            );
        }
    }

    public void setUser(User user){
        this.myUser = user;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_editThemes:
                Intent i = new Intent(getContext(), EditThemeActivity.class);
                startActivity(i);
                break;

        }
    }
}