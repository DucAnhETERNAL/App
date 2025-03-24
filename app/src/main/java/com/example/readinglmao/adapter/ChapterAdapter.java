// ChapterAdapter.java
package com.example.readinglmao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.readinglmao.R;
import com.example.readinglmao.model.Chapter;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {
    private List<Chapter> chapterList;
    private Context context;

    public ChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_item, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        Log.d("ChapterAdapter", "Binding chapter: " + chapter.getName());  // Log the chapter name
        holder.tvChapterName.setText(chapter.getName());
        holder.tvViewCount.setText("Views: " + chapter.getViewCount());
    }


    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    static class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvChapterName, tvViewCount;

        ChapterViewHolder(View itemView) {
            super(itemView);
            tvChapterName = itemView.findViewById(R.id.tvChapterName);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
        }
    }
}
