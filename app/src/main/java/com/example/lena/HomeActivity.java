package com.example.lena;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private StatisticsFragment statisticsFragment;
    private PlantsCareFragment plantsCareFragment;
    private ProfileFragment profileFragment;
    private MorphBottomNavigationView navigator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigator = findViewById(R.id.navigator);

        homeFragment = HomeFragment.newInstance();
        statisticsFragment = StatisticsFragment.newInstance();
        plantsCareFragment = PlantsCareFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();

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

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.commit();
    }
}