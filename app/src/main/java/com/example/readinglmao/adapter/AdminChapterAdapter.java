package com.example.readinglmao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readinglmao.R;
import com.example.readinglmao.model.ChapterListDTO;
import com.example.readinglmao.ui.ManageChapterActivity;

import java.util.List;

public class AdminChapterAdapter extends RecyclerView.Adapter<AdminChapterAdapter.ViewHolder> {
    private Context context;
    private List<ChapterListDTO> chapterList;

    public AdminChapterAdapter(Context context, List<ChapterListDTO> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChapterListDTO chapter = chapterList.get(position);
        holder.chapterTitle.setText(chapter.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ManageChapterActivity.class);
            intent.putExtra("CHAPTER_ID", chapter.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chapterTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterTitle = itemView.findViewById(R.id.chapterTitle);
        }
    }
}
