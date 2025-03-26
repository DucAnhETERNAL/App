package com.example.readinglmao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.readinglmao.R;
import com.example.readinglmao.model.MangaDTO;
import com.example.readinglmao.ui.MangaDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {
    private List<MangaDTO> mangaList;
    private Context context;

    public MangaAdapter(Context context, List<MangaDTO> mangaList) {
        this.context = context;
        this.mangaList = (mangaList != null) ? mangaList : new ArrayList<>();
    }

    @Override
    public MangaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manga_item, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MangaViewHolder holder, int position) {
        MangaDTO manga = mangaList.get(position);
        holder.title.setText(manga.getTitle());
        holder.genreName.setText(manga.getGenreName());
        holder.averageRating.setText(String.valueOf(manga.getAverageRating()));

        // Set a click listener on the item view
        holder.itemView.setOnClickListener(v -> {
            // Create an Intent to start MangaDetailsActivity
            Intent intent = new Intent(context, MangaDetailsActivity.class);

            // Pass the mangaId to MangaDetailsActivity
            intent.putExtra("mangaId", manga.getId()); // Pass the mangaId

            // Check if chapters list is not null and not empty
            if (manga.getChapters() != null && !manga.getChapters().isEmpty()) {
                // Use the first chapter's ID
                int chapterId = manga.getChapters().get(0).getId();
                intent.putExtra("chapterId", chapterId);  // Pass the chapterId (optional)
            }

            // Start the activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (mangaList == null) {
            return 0;
        }

        return mangaList.size();
    }

    // Update the manga list with new data
    public void updateMangaList(List<MangaDTO> newMangas) {
        mangaList.clear();
        mangaList.addAll(newMangas);
        notifyDataSetChanged();
    }

    public static class MangaViewHolder extends RecyclerView.ViewHolder {
        TextView title, genreName, averageRating;

        public MangaViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            genreName = itemView.findViewById(R.id.genreName);
            averageRating = itemView.findViewById(R.id.averageRating);
        }
    }
}
