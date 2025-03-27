package com.example.readinglmao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readinglmao.R;
import com.example.readinglmao.model.ChapterEditDTO;
import com.example.readinglmao.model.ChapterText;
import com.example.readinglmao.repository.AdminChapterRepository;

public class UpdateChapterActivity extends AppCompatActivity {

    private EditText etChapterName, etChapterContent;
    private Button btnUpdateChapter;
    private int chapterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chapter);

        etChapterName = findViewById(R.id.etChapterName);
        etChapterContent = findViewById(R.id.etChapterContent);
        btnUpdateChapter = findViewById(R.id.btnUpdateChapter);

        chapterId = getIntent().getIntExtra("CHAPTER_ID", -1);
        if (chapterId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy chương", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadChapterContent();

        btnUpdateChapter.setOnClickListener(view -> updateChapter());
    }

    private void loadChapterContent() {
        AdminChapterRepository.getInstance().getChapterTextById(chapterId, new AdminChapterRepository.RepositoryCallback<ChapterText>() {
            @Override
            public void onSuccess(ChapterText chapterText) {
                etChapterName.setText(chapterText.getName());
                etChapterContent.setText(chapterText.getContent());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(UpdateChapterActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateChapter() {
        String newName = etChapterName.getText().toString().trim();
        String newContent = etChapterContent.getText().toString().trim();

        if (newName.isEmpty() || newContent.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean status = true; // Hoặc có thể lấy từ UI nếu có

        ChapterEditDTO updatedChapter = new ChapterEditDTO(newName, status, newContent);

        AdminChapterRepository.getInstance().updateChapter(chapterId, updatedChapter, new AdminChapterRepository.RepositoryCallback<ChapterEditDTO>() {
            @Override
            public void onSuccess(ChapterEditDTO result) {
                Toast.makeText(UpdateChapterActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("UPDATED_CHAPTER", true);
                setResult(RESULT_OK, resultIntent);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(UpdateChapterActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
