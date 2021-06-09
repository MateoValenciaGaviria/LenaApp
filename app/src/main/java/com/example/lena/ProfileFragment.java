package com.example.lena;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lena.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private View root;

    private User myUser;
    private TextView userName;

    private AppCompatButton editBtn;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        editBtn = root.findViewById(R.id.editWorkingDaysBtn);

        userName = root.findViewById(R.id.userNameTV);

        userName.setText(myUser.getUserName());

        editBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //resolverMyUser();
    }

    private void resolverMyUser() {
        FirebaseUser fbuser = auth.getCurrentUser();
        firestore.collection("users").document(fbuser.getUid()).get().addOnCompleteListener(
                dbusertask -> {
                    DocumentSnapshot snapshot = dbusertask.getResult();
                    myUser = snapshot.toObject(User.class);
                }
        );
    }

    public void setUser(User user){
        this.myUser = user;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editWorkingDaysBtn:
                Intent i = new Intent(getContext(), WorkingDayActivity.class);
                startActivity(i);
                break;
        }
    }
}