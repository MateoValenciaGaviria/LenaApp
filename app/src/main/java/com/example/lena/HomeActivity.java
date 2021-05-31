package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lena.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private StatisticsFragment statisticsFragment;
    private PlantsCareFragment plantsCareFragment;
    private ProfileFragment profileFragment;
    private MorphBottomNavigationView navigator;

    private User myUser;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null){
            goToLogin();
            return;
        }

        myUser = (User) getIntent().getExtras().getSerializable("myUser");
        Toast.makeText(this, "Bienvenido "+myUser.getUserName()+"!", Toast.LENGTH_LONG).show();

        navigator = findViewById(R.id.navigator);

        homeFragment = HomeFragment.newInstance();
        statisticsFragment = StatisticsFragment.newInstance();
        plantsCareFragment = PlantsCareFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();

        profileFragment.setUser(myUser);
        statisticsFragment.setUser(myUser);

        showFragment(homeFragment);

        navigator.setOnNavigationItemSelectedListener(
                (menuItem) -> {
                    switch (menuItem.getItemId()){
                        case R.id.home:
                            showFragment(homeFragment);
                            break;
                        case R.id.statistics:
                            showFragment(statisticsFragment);
                            break;
                        case R.id.plants_care:
                            showFragment(plantsCareFragment);
                            break;
                        case R.id.profile:
                            showFragment(profileFragment);
                            break;
                    }

                    return true; 
                }
        );
    }

    private void goToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.commit();
    }
}