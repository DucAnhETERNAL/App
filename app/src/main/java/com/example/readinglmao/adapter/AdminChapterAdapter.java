package com.example.readinglmao.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readinglmao.R;
import com.example.readinglmao.model.Chapter;
import com.example.readinglmao.ui.ChapterAllDetailActivity;

import java.util.List;

public class AdminChapterAdapter extends RecyclerView.Adapter<AdminChapterAdapter.ChapterViewHolder> {
    private List<Chapter> chapterList;
    private Context context;


    public AdminChapterAdapter(List<Chapter> chapterList, Context context) {
        this.chapterList = chapterList;
        this.context = context;

    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manga_chapter, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        holder.textView.setText(chapter.getName());

        holder.itemView.setOnClickListener(v -> {
            Log.d("ChapterClick", "Clicked on Chapter ID: " + chapter.getId());


            // Chuyển sang AllChapterDetailActivity và truyền ChapterId
            Intent intent = new Intent(context, ChapterAllDetailActivity.class);
            intent.putExtra("CHAPTER_ID", chapter.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView textView, tvViewCount;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvChapterName);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
        }
    }
}
