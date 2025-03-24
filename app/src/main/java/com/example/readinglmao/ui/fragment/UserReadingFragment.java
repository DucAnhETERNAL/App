package com.example.readinglmao.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.readinglmao.R;
import com.example.readinglmao.model.ChapterText;
import com.example.readinglmao.model.Comment;
import com.example.readinglmao.model.CommentRequest;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;
import com.example.readinglmao.adapter.CommentAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserReadingFragment extends Fragment {

    private TextView tvChapterContent, tvCommentsLabel;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private EditText etComment;
    private Button btnSendComment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_reading, container, false);

        // Initialize views
        tvChapterContent = view.findViewById(R.id.tvChapterContent);
        tvCommentsLabel = view.findViewById(R.id.tvCommentsLabel);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerViewComments = view.findViewById(R.id.recyclerViewComments);
        etComment = view.findViewById(R.id.etComment);
        btnSendComment = view.findViewById(R.id.btnSendComment);

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

        btnSendComment.setOnClickListener(v -> {
            String commentContent = etComment.getText().toString().trim();
            if (!commentContent.isEmpty()) {
                // Get userId from SharedPreferences
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("userId", -1); // Get userId


                if (userId != -1) {
                    String createdAt = getCurrentDateTime(); // Get current datetime

                    CommentRequest commentRequest = new CommentRequest(userId, chapterId, commentContent, createdAt);

                    // Call the API to send the comment
                    sendCommentToApi(commentRequest);
                } else {
                    Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter a comment", Toast.LENGTH_SHORT).show();
            }
        });


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

    // Utility method to get current datetime
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        return sdf.format(new Date());
    }

}
