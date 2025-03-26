package com.example.readinglmao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.readinglmao.R;
import java.util.List;

public class ChapterTextAdapter extends RecyclerView.Adapter<ChapterTextAdapter.ViewHolder> {
    private Context context;
    private List<String> chapterParts;

    public ChapterTextAdapter(Context context, List<String> chapterParts) {
        this.context = context;
        this.chapterParts = chapterParts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvChapterPart.setText(chapterParts.get(position));
    }

    @Override
    public int getItemCount() {
        return chapterParts.size();
    }

    public void addMoreParts(List<String> newParts) {
        int startPos = chapterParts.size();
        chapterParts.addAll(newParts);
        notifyItemRangeInserted(startPos, newParts.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChapterPart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChapterPart = itemView.findViewById(R.id.tvChapterPart);
        }
    }
}
