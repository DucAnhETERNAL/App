package com.example.readinglmao.repository;

import com.example.readinglmao.model.Chapter;
import com.example.readinglmao.model.ChapterEditDTO;
import com.example.readinglmao.model.ChapterRequest;
import com.example.readinglmao.model.ChapterResponse;
import com.example.readinglmao.model.ChapterText;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminChapterRepository {
    private static AdminChapterRepository instance;
    private final ApiService apiService;


    private AdminChapterRepository() {
        this.apiService = RetrofitClient.getApiService();
    }

    // Phương thức getInstance để lấy instance duy nhất
    public static synchronized AdminChapterRepository getInstance() {
        if (instance == null) {
            instance = new AdminChapterRepository();
        }
        return instance;
    }

    // Thêm chương
    public void addChapter(ChapterRequest request, RepositoryCallback<ChapterResponse> callback) {
        apiService.addChapter(request).enqueue(new Callback<ChapterResponse>() {
            @Override
            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Lỗi khi thêm chương: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ChapterResponse> call, Throwable t) {
                callback.onError("Lỗi kết nối API: " + t.getMessage());
            }
        });
    }
    // Lấy thông tin chương theo ID
    public void getChapterById(int chapterId, RepositoryCallback<Chapter> callback) {
        apiService.getChapterById(chapterId).enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> Chapter) {
                if (Chapter.isSuccessful() && Chapter.body() != null) {
                    callback.onSuccess(Chapter.body());
                } else {
                    callback.onError("Không tìm thấy chương: " + Chapter.message());
                }
            }

            @Override
            public void onFailure(Call<Chapter> call, Throwable t) {
                callback.onError("Lỗi kết nối API: " + t.getMessage());
            }
        });
    }

    public void getChapterTextById(int chapterId, RepositoryCallback<ChapterText> callback) {
        apiService.getChapterTextById(chapterId).enqueue(new Callback<ChapterText>() {
            @Override
            public void onResponse(Call<ChapterText> call, Response<ChapterText> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Không tìm thấy nội dung chương: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ChapterText> call, Throwable t) {
                callback.onError("Lỗi kết nối API: " + t.getMessage());
            }
        });
    }

    public void updateChapter(int chapterId, ChapterEditDTO updatedChapter, RepositoryCallback<ChapterEditDTO> callback) {
        apiService.updateChapter(chapterId, updatedChapter).enqueue(new Callback<ChapterEditDTO>() {
            @Override
            public void onResponse(Call<ChapterEditDTO> call, Response<ChapterEditDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Lỗi khi cập nhật chương: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ChapterEditDTO> call, Throwable t) {
                callback.onError("Lỗi kết nối API: " + t.getMessage());
            }
        });
    }




    // Giao diện callback để xử lý kết quả API
    public interface RepositoryCallback<T> {
        void onSuccess(T result);
        void onError(String errorMessage);
    }
}
