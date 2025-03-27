package com.example.readinglmao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readinglmao.R;
import com.example.readinglmao.adapter.MangaAdapter1;
import com.example.readinglmao.model.MangaListDTO;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageChapterActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private MangaAdapter1 mangaAdapter;

    private FloatingActionButton btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_chapter);

        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ManageChapterActivity.this, AddChapterActivity.class);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchMangaList();

        bottomNavigationView = findViewById(R.id.admin_bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

    }
    private void fetchMangaList() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getAllManga().enqueue(new Callback<List<MangaListDTO>>() {
            @Override
            public void onResponse(Call<List<MangaListDTO>> call, Response<List<MangaListDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MangaListDTO> mangaList = response.body();
                    mangaAdapter = new MangaAdapter1(ManageChapterActivity.this, mangaList, "ManageChapterActivity");
                    recyclerView.setAdapter(mangaAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<MangaListDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
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