package com.example.lena;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View root;

    private User myUser;

    private RecyclerView userThemesList;
    private LinearLayoutManager layoutManager;
    private UserThemesAdapter userThemesAdapter;

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

        //userThemesList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        userThemesList.setLayoutManager(layoutManager);

        userThemesAdapter = new UserThemesAdapter();
        userThemesList.setAdapter(userThemesAdapter);

        return root;
    }

    public void loadFreeThemes(){
        if(myUser != null){
            userThemesAdapter.clear();

            ArrayList<Theme> userThemes = new ArrayList<>();
            userThemes = myUser.getThemes();

            Log.e(">>>", userThemes.get(0).getName());

            for (int i = 0; i < userThemes.size(); i++){
                userThemesAdapter.addTheme(userThemes.get(i));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFreeThemes();
    }

    public void setUser(User user){
        this.myUser = user;
    }

}