package com.example.lena;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lena.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class StatisticsFragment extends Fragment {

    private View root;

    private User myUser;
    private TextView statisticsUserName;

    private FirebaseFirestore firestore;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_statistics, container, false);

        firestore = FirebaseFirestore.getInstance();

        statisticsUserName = root.findViewById(R.id.statisticsUserNameTV);

        statisticsUserName.setText(myUser.getUserName());

        return root;
    }

    public void setUser(User user){
        this.myUser = user;
    }
}