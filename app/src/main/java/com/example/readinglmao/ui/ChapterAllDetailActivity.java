package com.example.readinglmao.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.readinglmao.R;
import com.example.readinglmao.model.Chapter;
import com.example.readinglmao.model.ChapterText;
import com.example.readinglmao.repository.AdminChapterRepository;
import com.example.readinglmao.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterAllDetailActivity extends AppCompatActivity {

    private TextView chapterTitleTextView, chapterContentTextView, viewCountTextView, statusTextView;
    private AdminChapterRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_all_detail);

        chapterTitleTextView = findViewById(R.id.chapterTitle);
        chapterContentTextView = findViewById(R.id.chapterContent);
        viewCountTextView = findViewById(R.id.viewCount);
        statusTextView = findViewById(R.id.status);

        int chapterId = getIntent().getIntExtra("CHAPTER_ID", -1);
        if (chapterId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy chương!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        repository = AdminChapterRepository.getInstance();
        loadChapterDetails(chapterId);
        loadChapterText(chapterId);
    }

    private void loadChapterDetails(int chapterId) {
        repository.getChapterById(chapterId, new AdminChapterRepository.RepositoryCallback<Chapter>() {
            @Override
            public void onSuccess(Chapter chapter) {
                chapterTitleTextView.setText(chapter.getName());
                viewCountTextView.setText("Lượt xem: " + chapter.getViewCount());
                statusTextView.setText(chapter.isStatus() ? "Trạng thái: Công khai" : "Trạng thái: Ẩn");
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(ChapterAllDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadChapterText(int chapterId) {
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

}