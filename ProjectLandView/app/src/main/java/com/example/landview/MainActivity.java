package com.example.landview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
//    private ActionBar actionBar;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ánh xạ
        bottomNavigationView = findViewById(R.id.bottomNav);
//        actionBar = getSupportActionBar();
        //đánh dấu mặc định
//        actionBar.setTitle("Home");
        loadFragment(new HomeFragment());

        //sét sự kiện item bottom
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId())
                {
                    case R.id.home:
//                        actionBar.setTitle("Home");
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.travel:
//                        actionBar.setTitle("Travel");
                        fragment = new TravelFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.favorite:
//                        actionBar.setTitle("Favorite");
                        fragment = new FavoriteFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.user:
//                        actionBar.setTitle("User");
                        fragment = new UserFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layoutFrame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}