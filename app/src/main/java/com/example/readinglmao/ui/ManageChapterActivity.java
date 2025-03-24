package com.example.readinglmao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.readinglmao.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManageChapterActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bottomNavigationView = findViewById(R.id.admin_bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_manga_list) {
            startActivity(new Intent(this, AdminActivity.class));
            return true;
        } else if (item.getItemId() == R.id.nav_add_manga) {
            startActivity(new Intent(this, AddMangaActivity.class));
            return true;
        } else if (item.getItemId() == R.id.nav_manage_chapter) {
            startActivity(new Intent(this, ManageChapterActivity.class));
            return true;
        }
        return false;

    }
}