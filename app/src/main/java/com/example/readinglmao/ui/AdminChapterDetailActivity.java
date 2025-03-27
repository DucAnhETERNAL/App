package com.example.readinglmao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readinglmao.R;
import com.example.readinglmao.adapter.ChapterAdapter;
import com.example.readinglmao.model.Chapter;
import com.example.readinglmao.model.MangaDetailsDTO;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminChapterDetailActivity extends AppCompatActivity {

    private TextView mangaTitle;
    private RecyclerView recyclerViewChapters;
    private ChapterAdapter chapterAdapter;
    private List<Chapter> chapterList;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chapter_detail);

        // Initialize views
        mangaTitle = findViewById(R.id.mangaTitle);
        recyclerViewChapters = findViewById(R.id.recyclerViewChapters);

        btnBack = findViewById(R.id.btnBack);

        // Initialize chapter list and adapter
        chapterList = new ArrayList<>();
        chapterAdapter = new ChapterAdapter(this, chapterList);
        recyclerViewChapters.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChapters.setAdapter(chapterAdapter);

        btnBack.setOnClickListener(v -> finish()); // Kết thúc Activity, quay lại màn hình trước đó



        // Receive mangaId from Intent
        int mangaId = getIntent().getIntExtra("MANGA_ID", -1); // Get mangaId passed from the previous screen
        Log.d("MangaDetailsActivity", "Received mangaId: " + mangaId);

        // Fetch manga details and chapters if mangaId is valid
        if (mangaId != -1) {
            fetchMangaDetails(mangaId);
        } else {
            Log.e("MangaDetailsActivity", "No mangaId passed!");
        }
    }

    private void fetchMangaDetails(int mangaId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getMangaDetails(mangaId).enqueue(new Callback<MangaDetailsDTO>() {
            @Override
            public void onResponse(Call<MangaDetailsDTO> call, Response<MangaDetailsDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MangaDetailsDTO mangaDetails = response.body();
                    Log.d("MangaDetailsActivity", "Received Manga: " + mangaDetails.toString());

                    // Check if chapters data is received correctly
                    if (mangaDetails.getChapters() != null) {
                        Log.d("MangaDetailsActivity", "Chapters: " + mangaDetails.getChapters().toString());
                    } else {
                        Log.e("MangaDetailsActivity", "Chapters are null!");
                    }

                    // Display manga details
                    mangaTitle.setText(mangaDetails.getTitle());

                    // Set chapters list to RecyclerView
                    if (mangaDetails.getChapters() != null && !mangaDetails.getChapters().isEmpty()) {
                        chapterList.clear();
                        chapterList.addAll(mangaDetails.getChapters());
                        chapterAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("MangaDetailsActivity", "No chapters available.");
                    }
                }
            }

            @Override
            public void onFailure(Call<MangaDetailsDTO> call, Throwable t) {
                Log.e("MangaDetailsActivity", "API request failed: " + t.getMessage());
            }
        });
    }



}
