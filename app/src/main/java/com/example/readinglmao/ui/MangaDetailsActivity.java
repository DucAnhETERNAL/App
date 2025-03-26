package com.example.readinglmao.ui;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readinglmao.R;
import com.example.readinglmao.adapter.ChapterAdapter;
import com.example.readinglmao.model.Chapter;
import com.example.readinglmao.model.MangaDetailsDTO; // Import MangaDetailsDTO
import com.example.readinglmao.model.MangaFavoriteDTO;
import com.example.readinglmao.model.MangaListFavoriteDTO;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Context;


public class MangaDetailsActivity extends AppCompatActivity {
    private int mangaId;

    private TextView mangaTitle, mangaDescription, mangaAuthor, mangaType, mangaStatus;
    private RecyclerView recyclerViewChapters;
    private ChapterAdapter chapterAdapter;
    private List<Chapter> chapterList;
    private ImageButton addToFavoritesButton;
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
        addToFavoritesButton = findViewById(R.id.addToFavoritesButton);
        // Initialize chapter list and adapter
        chapterList = new ArrayList<>();
        chapterAdapter = new ChapterAdapter(this, chapterList);
        recyclerViewChapters.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChapters.setAdapter(chapterAdapter);

        // Receive mangaId from Intent
        mangaId = getIntent().getIntExtra("mangaId", -1);
        int chapterId = getIntent().getIntExtra("chapterId", -1); // Get mangaId passed from the previous screen
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
        addToFavoritesButton.setOnClickListener(v -> {
            // Action to add the manga to the favorites
            SharedPreferences sharedPreferences = MangaDetailsActivity.this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", -1); // Get userId from SharedPreferences
            if (userId != -1 && mangaId != -1) {
                // Gọi hàm fetchFavoriteList để kiểm tra trạng thái yêu thích
                fetchFavoriteList(userId);
            } else {
                // Nếu userId hoặc mangaId không hợp lệ
                Toast.makeText(MangaDetailsActivity.this, "Error: Invalid user or manga", Toast.LENGTH_SHORT).show();
            }
        });
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
    private void sendFavoriteToApi(MangaFavoriteDTO mangaFavoriteDTO) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.addToFavorites(mangaFavoriteDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Manga added to favorites successfully

                    // Optionally, you can update the UI to reflect the favorite status
                    // For example, update a button to show that the manga is in favorites
                    // favoriteButton.setText("Remove from Favorites"); // Example action

                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
            }
        });
    }
    private void fetchFavoriteList(int userId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getUserFavorites(userId).enqueue(new Callback<List<MangaListFavoriteDTO>>() {
            @Override
            public void onResponse(Call<List<MangaListFavoriteDTO>> call, Response<List<MangaListFavoriteDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MangaListFavoriteDTO> favoriteList = response.body();

                    // Check if mangaId is in the favorites list
                    boolean isFavorite = false;
                    int userMangaListId = -1;
                    for (MangaListFavoriteDTO manga : favoriteList) {
                        if (manga.getMangaId() == mangaId) {
                            isFavorite = true;
                            userMangaListId = manga.getId();  // Get the list ID for the manga
                            break;
                        }
                    }

                    // Nếu manga chưa có trong danh sách yêu thích, thêm vào
                    if (isFavorite) {
                        removeMangaFromFavorites(userMangaListId);
                        Toast.makeText(MangaDetailsActivity.this, "Removed from Favorites!", Toast.LENGTH_SHORT).show();
                    } else {
                        // If manga is not in favorites, add it
                        MangaFavoriteDTO mangaFavoriteDTO = new MangaFavoriteDTO(userId, mangaId, true);
                        sendFavoriteToApi(mangaFavoriteDTO);
                        Toast.makeText(MangaDetailsActivity.this, "Added to Favorites!", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(MangaDetailsActivity.this, "Failed to fetch favorites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MangaListFavoriteDTO>> call, Throwable t) {
                Toast.makeText(MangaDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void removeMangaFromFavorites(int userMangaListId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.removeFromFavorites(userMangaListId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle success (you can update the UI here if needed)
                } else {
                    // Handle failure (you can show an error message if needed)
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure (you can show an error message if needed)
            }
        });
    }





}
