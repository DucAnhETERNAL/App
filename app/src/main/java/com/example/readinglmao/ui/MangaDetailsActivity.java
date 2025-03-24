package com.example.readinglmao.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readinglmao.R;
import com.example.readinglmao.adapter.ChapterAdapter;
import com.example.readinglmao.model.Chapter;
import com.example.readinglmao.model.MangaDetailsDTO; // Import MangaDetailsDTO
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MangaDetailsActivity extends AppCompatActivity {

    private TextView mangaTitle, mangaDescription, mangaAuthor, mangaType, mangaStatus;
    private RecyclerView recyclerViewChapters;
    private ChapterAdapter chapterAdapter;
    private List<Chapter> chapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_details);

        // Initialize views
        mangaTitle = findViewById(R.id.mangaTitle);
        mangaDescription = findViewById(R.id.mangaDescription);
        mangaAuthor = findViewById(R.id.mangaAuthor);
        mangaType = findViewById(R.id.mangaType);
        mangaStatus = findViewById(R.id.mangaStatus);
        recyclerViewChapters = findViewById(R.id.recyclerViewChapters);

        // Initialize chapter list and adapter
        chapterList = new ArrayList<>();
        chapterAdapter = new ChapterAdapter(this, chapterList);
        recyclerViewChapters.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChapters.setAdapter(chapterAdapter);

        // Receive mangaId from Intent
        int mangaId = getIntent().getIntExtra("mangaId", -1);
        int chapterId = getIntent().getIntExtra("chapterId", -1); // Get mangaId passed from the previous screen
        Log.d("MangaDetailsActivity", "Received mangaId: " + mangaId);
        Log.d("MangaDetailsActivity", "Received chapterId: " + chapterId);
        // Fetch manga details and chapters if mangaId is valid
        if (mangaId != -1) {
            fetchMangaDetails(mangaId);
        } else {
            Log.e("MangaDetailsActivity", "No mangaId passed!");
        }
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
                    mangaDescription.setText(mangaDetails.getDescription());
                    mangaAuthor.setText(mangaDetails.getAuthor());
                    mangaType.setText(mangaDetails.getType());
                    mangaStatus.setText(mangaDetails.getStatus());

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
