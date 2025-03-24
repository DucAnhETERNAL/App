package com.example.readinglmao.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.readinglmao.R;
import com.example.readinglmao.model.ChapterText;
import com.example.readinglmao.model.Comment;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;
import com.example.readinglmao.adapter.CommentAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class UserReadingFragment extends Fragment {

    private TextView tvChapterContent, tvCommentsLabel;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_reading, container, false);

        // Initialize views
        tvChapterContent = view.findViewById(R.id.tvChapterContent);
        tvCommentsLabel = view.findViewById(R.id.tvCommentsLabel);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerViewComments = view.findViewById(R.id.recyclerViewComments);

        // Set up RecyclerView for comments
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(getContext(), commentList);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewComments.setAdapter(commentAdapter);

        // Get chapterId from arguments passed to the fragment
        int chapterId = getArguments().getInt("chapterId", -1);

        // Fetch chapter content and comments if chapterId is valid
        if (chapterId != -1) {
            fetchChapterContentAndComments(chapterId);
        } else {
            tvChapterContent.setText("Invalid chapter.");
        }

        return view;
    }

    private void fetchChapterContentAndComments(int chapterId) {
        // Show the progress bar while fetching the content
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        // Fetch chapter content
        apiService.getChapterContent(chapterId).enqueue(new Callback<ChapterText>() {
            @Override
            public void onResponse(Call<ChapterText> call, Response<ChapterText> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Hide the progress bar
                    progressBar.setVisibility(View.GONE);

                    ChapterText chapterText = response.body();
                    String content = chapterText.getContent();

                    // Limit the content length if it exceeds a certain number of characters
                    int maxContentLength = 1000;
                    if (content.length() > maxContentLength) {
                        content = content.substring(0, maxContentLength) + "...";
                    }

                    // Set chapter content
                    tvChapterContent.setText(content);

                    // Set the comments section
                    List<Comment> comments = chapterText.getComments();
                    if (comments != null && !comments.isEmpty()) {
                        commentList.clear();
                        commentList.addAll(comments);
                        commentAdapter.notifyDataSetChanged();
                    }
                } else {
                    // Handle the case when the chapter content is not fetched successfully
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Failed to load chapter content.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChapterText> call, Throwable t) {
                // Handle failure (e.g., no internet connection)
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
