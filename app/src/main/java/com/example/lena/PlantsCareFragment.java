package com.example.lena;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lena.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PlantsCareFragment extends Fragment implements View.OnClickListener {

    private View root;

    private User myUser;

    private Button editPlants;

    private ImageView imageClickable;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public PlantsCareFragment() {
        // Required empty public constructor
    }

    public static PlantsCareFragment newInstance() {
        PlantsCareFragment fragment = new PlantsCareFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_plants_care, container, false);

        editPlants = root.findViewById(R.id.btn_edit_plants);
        imageClickable = root.findViewById(R.id.plantsImageClickable);

        editPlants.setOnClickListener(this);
        imageClickable.setOnClickListener(this);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        return root;
    }

    public void setUser(User user){
        this.myUser = user;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit_plants:
                Intent i = new Intent(getContext(), EditPlantsActivity.class);
                startActivity(i);
                break;
            case R.id.plantsImageClickable:
                Intent j = new Intent(getContext(), PlantInformationActivity.class);
                startActivity(j);
                break;
        }
    }
}