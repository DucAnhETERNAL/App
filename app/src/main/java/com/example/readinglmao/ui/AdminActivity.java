package com.example.readinglmao.ui;

import android.content.Intent;
import android.os.Bundle;
import com.example.readinglmao.R;


import com.example.readinglmao.adapter.MangaAdapter;
import com.example.readinglmao.model.MangaDTO;
import com.example.readinglmao.service.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readinglmao.databinding.ActivityAdminBinding;

import com.example.readinglmao.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
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