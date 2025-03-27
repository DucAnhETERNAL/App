package com.example.readinglmao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.readinglmao.R;
import com.example.readinglmao.model.Chapter;
import com.example.readinglmao.model.ChapterText;
import com.example.readinglmao.repository.AdminChapterRepository;

public class ChapterAllDetailActivity extends AppCompatActivity {

    private TextView chapterTitleTextView, chapterContentTextView;
    private Button btnBack, btnUpdate;
    private AdminChapterRepository repository;
    private int chapterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_all_detail);

        chapterTitleTextView = findViewById(R.id.chapterTitle);
        chapterContentTextView = findViewById(R.id.chapterContent);
        btnBack = findViewById(R.id.btnBack);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnBack.setOnClickListener(v -> finish());

        chapterId = getIntent().getIntExtra("CHAPTER_ID", -1);
        if (chapterId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy chương!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        repository = AdminChapterRepository.getInstance();
        loadChapterDetails();
        loadChapterText();

        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(ChapterAllDetailActivity.this, UpdateChapterActivity.class);
            intent.putExtra("CHAPTER_ID", chapterId);
            startActivityForResult(intent, 1);
        });
    }

    private void loadChapterDetails() {
        repository.getChapterById(chapterId, new AdminChapterRepository.RepositoryCallback<Chapter>() {
            @Override
            public void onSuccess(Chapter chapter) {
                chapterTitleTextView.setText(chapter.getName());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(ChapterAllDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadChapterText() {
        repository.getChapterTextById(chapterId, new AdminChapterRepository.RepositoryCallback<ChapterText>() {
            @Override
            public void onSuccess(ChapterText chapterText) {
                chapterContentTextView.setText(chapterText.getContent());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(ChapterAllDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            boolean updated = data.getBooleanExtra("UPDATED_CHAPTER", false);
            if (updated) {
                loadChapterDetails();
                loadChapterText();
            }
        }
    }
}
