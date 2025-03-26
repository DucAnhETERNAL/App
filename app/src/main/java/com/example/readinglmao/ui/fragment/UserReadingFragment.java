package com.example.readinglmao.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.readinglmao.R;
import com.example.readinglmao.adapter.ChapterTextAdapter;
import com.example.readinglmao.adapter.CommentAdapter;
import com.example.readinglmao.model.ChapterText;
import com.example.readinglmao.model.Comment;
import com.example.readinglmao.model.CommentRequest;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserReadingFragment extends Fragment {
    private RecyclerView recyclerViewChapter, recyclerViewComments;
    private ChapterTextAdapter chapterAdapter;
    private CommentAdapter commentAdapter;
    private ProgressBar progressBar;
    private EditText etComment;
    private Button btnSendComment;
    private List<String> chapterParts;
    private List<Comment> commentList;
    private boolean isLoading = false;
    private int chapterId;
    private int currentPage = 0;
    private static final int PAGE_SIZE = 500;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_reading, container, false);
        recyclerViewChapter = view.findViewById(R.id.recyclerViewChapter);
        recyclerViewComments = view.findViewById(R.id.recyclerViewComments);
        progressBar = view.findViewById(R.id.progressBar);
        etComment = view.findViewById(R.id.etComment);
        btnSendComment = view.findViewById(R.id.btnSendComment);

        chapterParts = new ArrayList<>();
        commentList = new ArrayList<>();

        chapterAdapter = new ChapterTextAdapter(getContext(), chapterParts);
        recyclerViewChapter.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewChapter.setAdapter(chapterAdapter);

        commentAdapter = new CommentAdapter(getContext(), commentList);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewComments.setAdapter(commentAdapter);

        chapterId = getArguments().getInt("chapterId", -1);
        if (chapterId != -1) {
            fetchChapterContentAndComments();
        }

        btnSendComment.setOnClickListener(v -> {
            String commentContent = etComment.getText().toString().trim();
            if (!commentContent.isEmpty()) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("userId", -1);
                if (userId != -1) {
                    String createdAt = getCurrentDateTime();
                    sendCommentToApi(new CommentRequest(userId, chapterId, commentContent, createdAt));
                } else {
                    Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter a comment", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void fetchChapterContentAndComments() {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getChapterContent(chapterId).enqueue(new Callback<ChapterText>() {
            @Override
            public void onResponse(Call<ChapterText> call, Response<ChapterText> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    ChapterText chapterText = response.body();
                    String content = chapterText.getContent();
                    for (int i = 0; i < content.length(); i += PAGE_SIZE) {
                        chapterParts.add(content.substring(i, Math.min(i + PAGE_SIZE, content.length())));
                    }
                    chapterAdapter.notifyDataSetChanged();
                    if (chapterText.getComments() != null) {
                        commentList.addAll(chapterText.getComments());
                        commentAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load chapter content.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChapterText> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendCommentToApi(CommentRequest commentRequest) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.addComment(commentRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Comment added successfully
                    Toast.makeText(getContext(), "Comment added", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "Unknown User");
                    // Optionally update the comments list or refresh the UI
                    Comment newComment = new Comment();
                    newComment.setUserId(commentRequest.getUserId());
                    newComment.setChapterId(commentRequest.getChapterId());
                    newComment.setContent(commentRequest.getContent());
                    newComment.setCreatedAt(commentRequest.getCreatedAt());
                    newComment.setUserName(username);

                    // Thêm comment mới vào danh sách comment
                    commentList.add(0, newComment); // Thêm vào đầu danh sách để nó hiển thị đầu tiên
                    commentAdapter.notifyItemInserted(0); // Cập nhật RecyclerView

                    // Cuộn RecyclerView tới đầu danh sách
                    recyclerViewComments.scrollToPosition(0);
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Error adding comment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(new Date());
    }
}
