package com.example.readinglmao.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.readinglmao.R;
import com.example.readinglmao.ui.fragment.HomeFragment;
import com.example.readinglmao.ui.fragment.ProfileFragment;
import com.example.readinglmao.ui.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set the default fragment to HomeFragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Handle item selection from the bottom navigation view
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.item_home) {
                    selectedFragment = new HomeFragment();
                    Log.d("MainActivity", "HomeFragment selected");
                } else if (itemId == R.id.item_search) {
                    selectedFragment = new SearchFragment();
                    Log.d("MainActivity", "SearchFragment selected");
                } else if (itemId == R.id.item_profile) {
                    selectedFragment = new ProfileFragment();
                    Log.d("MainActivity", "ProfileFragment selected");
                }

                return loadFragment(selectedFragment);
            }
        });
    }

    // Method to replace fragments dynamically
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)  // Replace the fragment container with the selected fragment
                    .commit();
            return true;
        }
        return false;
    }
}
