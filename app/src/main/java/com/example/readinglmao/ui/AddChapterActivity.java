package com.example.readinglmao.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.readinglmao.R;
import com.example.readinglmao.adapter.MangaAdapter1;
import com.example.readinglmao.model.ChapterRequest;
import com.example.readinglmao.model.ChapterResponse;
import com.example.readinglmao.model.MangaListDTO;
import com.example.readinglmao.repository.AdminChapterRepository;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChapterActivity extends AppCompatActivity {

    private EditText edtChapterName, edtContent;
    private Button btnSubmit, btnBack;
    private AdminChapterRepository adminChapterRepository;

    private MangaAdapter1 mangaAdapter;
    private Spinner spinnerManga;
    private int selectedMangaId = -1;
    private List<MangaListDTO> mangaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);

        edtChapterName = findViewById(R.id.edtChapterName);
        edtContent = findViewById(R.id.edtContent);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);
        spinnerManga = findViewById(R.id.spinnerManga);

        // Khởi tạo repository
        adminChapterRepository = AdminChapterRepository.getInstance();

        fetchMangaList();

        btnSubmit.setOnClickListener(v -> addChapter());
        btnBack.setOnClickListener(v -> finish());
    }

    private void fetchMangaList() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getAllManga().enqueue(new Callback<List<MangaListDTO>>() {
            @Override
            public void onResponse(Call<List<MangaListDTO>> call, Response<List<MangaListDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mangaList = response.body(); // Lưu danh sách manga

                    // Tạo danh sách tên manga để hiển thị trên Spinner
                    List<String> mangaNames = new ArrayList<>();
                    for (MangaListDTO manga : mangaList) {
                        mangaNames.add(manga.getTitle()); // Giả sử `getTitle()` trả về tên manga
                    }

                    // Cấu hình Adapter cho Spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddChapterActivity.this,
                            android.R.layout.simple_spinner_item, mangaNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerManga.setAdapter(adapter);

                    // Sự kiện chọn manga
                    spinnerManga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedMangaId = mangaList.get(position).getId(); // Lấy ID manga
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            selectedMangaId = -1; // Không chọn gì cả
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<MangaListDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void addChapter() {
        String name = edtChapterName.getText().toString().trim();
        String content = edtContent.getText().toString().trim();
        boolean status = true;

        if (name.isEmpty() || content.isEmpty() || selectedMangaId == -1) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin và chọn manga", Toast.LENGTH_SHORT).show();
            return;
        }

        ChapterRequest request = new ChapterRequest(selectedMangaId, name, status, content);
        adminChapterRepository.addChapter(request, new AdminChapterRepository.RepositoryCallback<ChapterResponse>() {
            @Override
            public void onSuccess(ChapterResponse result) {
                Toast.makeText(AddChapterActivity.this, "Thêm chương thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(AddChapterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


}