package com.example.readinglmao.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.readinglmao.R;
import com.example.readinglmao.model.Chapter;
import com.example.readinglmao.model.MangaDetailsDTO; // Import MangaDetailsDTO

public class MangaDetailsActivity extends AppCompatActivity {

    private TextView mangaTitle, mangaDescription, mangaAuthor, mangaType, mangaStatus, chapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_details);

        // Initialize TextViews
        mangaTitle = findViewById(R.id.mangaTitle);
        mangaDescription = findViewById(R.id.mangaDescription);
        mangaAuthor = findViewById(R.id.mangaAuthor);
        mangaType = findViewById(R.id.mangaType);
        mangaStatus = findViewById(R.id.mangaStatus);
        chapterList = findViewById(R.id.chapterList);

        // Nhận MangaDetailsDTO từ Intent
        MangaDetailsDTO mangaDetails = (MangaDetailsDTO) getIntent().getSerializableExtra("mangaDetails");

        // Hiển thị dữ liệu từ MangaDetailsDTO
        if (mangaDetails != null) {
            mangaTitle.setText(mangaDetails.getTitle());
            mangaDescription.setText(mangaDetails.getDescription());
            mangaAuthor.setText(mangaDetails.getAuthor());
            mangaType.setText(mangaDetails.getType());
            mangaStatus.setText(mangaDetails.getStatus());

            if (mangaDetails.getChapters() != null && !mangaDetails.getChapters().isEmpty()) {
                StringBuilder chapters = new StringBuilder();
                for (Chapter chapter : mangaDetails.getChapters()) {
                    chapters.append(chapter.getName()).append("\n");
                }
                chapterList.setText(chapters.toString());
            } else {
                chapterList.setText("No chapters available");
            }
        }
    }
}
