package com.example.readinglmao.repository;

import android.util.Log;

import com.example.readinglmao.model.AddMangaRequestDTO;
import com.example.readinglmao.model.AdminMangaDetailsDTO;
import com.example.readinglmao.model.MangaDTO;
import com.example.readinglmao.model.MangaEditDTO;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMangaRepository {
    private final ApiService apiService;

    public AdminMangaRepository() {
        this.apiService = RetrofitClient.getApiService();
    }

    public void fetchMangaDetail(int mangaId, MangaDetailCallback callback) {
        apiService.getAdminMangaDetails(mangaId).enqueue(new Callback<AdminMangaDetailsDTO>() {
            @Override
            public void onResponse(Call<AdminMangaDetailsDTO> call, Response<AdminMangaDetailsDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    Log.e("API_ERROR", "Error: " + response.code());
                    callback.onFailure("Failed to fetch manga details.");
                }
            }

            @Override
            public void onFailure(Call<AdminMangaDetailsDTO> call, Throwable t) {
                Log.e("API_FAILURE", "Network error: " + t.getMessage());
                callback.onFailure("Network error. Please try again.");
            }
        });
    }

    public void updateMangaDetail(int mangaId, MangaEditDTO mangaEditDTO, UpdateMangaCallback callback) {
        apiService.updateManga(mangaId, mangaEditDTO).enqueue(new Callback<MangaEditDTO>() {
            @Override
            public void onResponse(Call<MangaEditDTO> call, Response<MangaEditDTO> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Manga updated successfully!");
                } else {
                    Log.e("API_ERROR", "Update failed: " + response.code());
                    callback.onFailure("Failed to update manga.");
                }
            }

            @Override
            public void onFailure(Call<MangaEditDTO> call, Throwable t) {
                Log.e("API_FAILURE", "Update error: " + t.getMessage());
                callback.onFailure("Network error. Please try again.");
            }
        });
    }

    public void addManga(AddMangaRequestDTO mangaRequest, AddMangaCallback callback) {
        apiService.addManga(mangaRequest).enqueue(new Callback<MangaDTO>() {
            @Override
            public void onResponse(Call<MangaDTO> call, Response<MangaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    Log.e("API_ERROR", "Add failed: " + response.code());
                    callback.onFailure("Failed to add manga.");
                }
            }

            @Override
            public void onFailure(Call<MangaDTO> call, Throwable t) {
                Log.e("API_FAILURE", "Add error: " + t.getMessage());
                callback.onFailure("Network error. Please try again.");
            }
        });
    }


    public interface MangaDetailCallback {
        void onSuccess(AdminMangaDetailsDTO manga);  // Đã sửa kiểu dữ liệu
        void onFailure(String errorMessage);
    }
    public interface UpdateMangaCallback {
        void onSuccess(String successMessage);
        void onFailure(String errorMessage);
    }
    public interface AddMangaCallback {
        void onSuccess(MangaDTO manga);
        void onFailure(String errorMessage);
    }
}