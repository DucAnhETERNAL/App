package com.example.readinglmao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readinglmao.R;
import com.example.readinglmao.model.AdminMangaDetailsDTO;
import com.example.readinglmao.model.MangaEditDTO;
import com.example.readinglmao.repository.AdminMangaRepository;

public class AdminMangaDetailActivity extends AppCompatActivity {
    private LinearLayout viewModeLayout, editModeLayout, editButtonsLayout;
    private TextView textViewTitle, textViewAuthor, textViewType, textViewStatus, textViewDescription;
    private EditText editTextTitle, editTextDescription;
    private Button btnEdit, btnSave, btnCancel, btnBack;
    private Spinner spinnerStatus;

    private int mangaId;
    private AdminMangaRepository mangaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manga_detail);

        spinnerStatus = findViewById(R.id.spinnerStatus);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);


        viewModeLayout = findViewById(R.id.viewModeLayout);
        editModeLayout = findViewById(R.id.editModeLayout);
        editButtonsLayout = findViewById(R.id.editButtonsLayout);
        // Ánh xạ view
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewAuthor = findViewById(R.id.textViewAuthor);
        textViewType = findViewById(R.id.textViewType);
        textViewStatus = findViewById(R.id.textViewStatus);
        textViewDescription = findViewById(R.id.textViewDescription);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);

        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnBack = findViewById(R.id.btnBack);

        mangaRepository = new AdminMangaRepository();

        // Lấy mangaId từ Intent
        mangaId = getIntent().getIntExtra("MANGA_ID", -1);
        mangaRepository = new AdminMangaRepository();

        fetchMangaDetail(mangaId);

        btnEdit.setOnClickListener(view -> enableEditMode(true));
        btnCancel.setOnClickListener(view -> enableEditMode(false));
        btnSave.setOnClickListener(view -> updateMangaDetail());
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
            finish(); // Đóng Activity hiện tại
        });
    }

    private void fetchMangaDetail(int mangaId) {
        mangaRepository.fetchMangaDetail(mangaId, new AdminMangaRepository.MangaDetailCallback() {
            @Override
            public void onSuccess(AdminMangaDetailsDTO manga) {
                textViewTitle.setText(manga.getTitle());
                textViewAuthor.setText(manga.getAuthor());
                textViewType.setText(manga.getType());
                textViewStatus.setText(manga.getStatus());
                textViewDescription.setText(manga.getDescription());

                editTextTitle.setText(manga.getTitle());
                if (manga.getStatus().equalsIgnoreCase("Active")) {
                    spinnerStatus.setSelection(0);
                } else {
                    spinnerStatus.setSelection(1);
                }
                editTextDescription.setText(manga.getDescription());
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("API_ERROR", errorMessage);
                showToast(errorMessage);
            }
        });
    }

    private void enableEditMode(boolean isEditing) {
        if (isEditing) {
            viewModeLayout.setVisibility(View.GONE);
            editModeLayout.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.GONE);
            btnBack.setVisibility(View.GONE);
            editButtonsLayout.setVisibility(View.VISIBLE);
        } else {
            viewModeLayout.setVisibility(View.VISIBLE);
            editModeLayout.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.VISIBLE);
            editButtonsLayout.setVisibility(View.GONE);
        }
    }


    private void updateMangaDetail() {
        String title = editTextTitle.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty() || status.isEmpty() || description.isEmpty()) {
            showToast("Please fill all required fields.");
            return;
        }

        int genreId = 1; // Giá trị giả định

        MangaEditDTO updatedManga = new MangaEditDTO(title, description, genreId, status);

        mangaRepository.updateMangaDetail(mangaId, updatedManga, new AdminMangaRepository.UpdateMangaCallback() {
            @Override
            public void onSuccess(String successMessage) {
                showToast(successMessage);
                fetchMangaDetail(mangaId); // Load lại dữ liệu
                enableEditMode(false);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("API_ERROR", errorMessage);
                showToast(errorMessage);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}