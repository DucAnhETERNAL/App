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
import com.example.readinglmao.model.MangaDetailsDTO;
import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {
    private List<MangaDTO> mangaList;
    private Context context;

    public MangaAdapter(Context context, List<MangaDTO> mangaList) {
        this.context = context;
        this.mangaList = mangaList;
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

        // Chuyển MangaDTO thành MangaDetailsDTO
        MangaDetailsDTO mangaDetails = new MangaDetailsDTO();
        mangaDetails.setTitle(manga.getTitle());
        mangaDetails.setDescription(manga.getDescription());
        mangaDetails.setAuthor(manga.getAuthor());
        mangaDetails.setType(manga.getType());
        mangaDetails.setStatus(manga.getStatus());
        mangaDetails.setChapters(manga.getChapters());

        // Set a click listener on the item view
        holder.itemView.setOnClickListener(v -> {
            // Create an Intent to start MangaDetailsActivity
            Intent intent = new Intent(context, MangaDetailsActivity.class);
            intent.putExtra("mangaDetails", mangaDetails); // Truyền MangaDetailsDTO
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

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
