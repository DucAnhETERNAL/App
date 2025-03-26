package com.example.readinglmao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.readinglmao.R;
import com.example.readinglmao.model.AddMangaRequestDTO;
import com.example.readinglmao.model.Genre;
import com.example.readinglmao.model.MangaDTO;
import com.example.readinglmao.repository.AdminMangaRepository;
import com.example.readinglmao.utils.GenreUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.stream.Collectors;

public class AddMangaActivity extends AppCompatActivity {

    private Spinner spinnerGenre, spinnerStatus;
    private EditText edtTitle, edtDescription, edtAuthor, edtType;
    private Button btnAddManga;

    private AdminMangaRepository mangaRepository;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manga);

        edtTitle = findViewById(R.id.editTextTitle);
        edtDescription = findViewById(R.id.editTextDescription);
        edtAuthor = findViewById(R.id.editTextAuthor);
        edtType = findViewById(R.id.editTextType);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnAddManga = findViewById(R.id.btnAddManga);



        // Lấy danh sách Genre từ JSON
        List<Genre> genres = GenreUtils.getGenres(this);

        // Chuyển danh sách Genre thành danh sách tên
        List<String> genreNames = genres.stream().map(Genre::getName).collect(Collectors.toList());

        // Tạo ArrayAdapter để hiển thị danh sách trong Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genreNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        // Khởi tạo Repository
        mangaRepository = new AdminMangaRepository();
        // Bắt sự kiện khi bấm nút Add
        btnAddManga.setOnClickListener(view -> addManga());

        bottomNavigationView = findViewById(R.id.admin_bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    private void addManga() {
        String title = edtTitle.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String author = edtAuthor.getText().toString().trim();
        String type = edtType.getText().toString().trim();
        int genreId = spinnerGenre.getSelectedItemPosition() + 1;  // Lấy ID thể loại từ Spinner
        String status = spinnerStatus.getSelectedItem().toString();

        // Tạo request
        AddMangaRequestDTO mangaRequest = new AddMangaRequestDTO(title, description, author, type, genreId, status);

        // Gọi API qua Repository
        mangaRepository.addManga(mangaRequest, new AdminMangaRepository.AddMangaCallback() {
            @Override
            public void onSuccess(MangaDTO manga) {
                Toast.makeText(AddMangaActivity.this, "Manga added successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(AddMangaActivity.this, "Failed to add manga: " + errorMessage, Toast.LENGTH_SHORT).show();
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