package com.example.lena;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlantsCareFragment extends Fragment {



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
        return inflater.inflate(R.layout.fragment_plants_care, container, false);
    }
}