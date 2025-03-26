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
import com.example.readinglmao.model.MangaListDTO;
import com.example.readinglmao.ui.AdminChapterDetailActivity;
import com.example.readinglmao.ui.AdminMangaDetailActivity;
import com.example.readinglmao.ui.ManageChapterActivity;
import com.example.readinglmao.ui.MangaDetailsActivity;

import java.util.List;

public class    MangaAdapter1 extends RecyclerView.Adapter<MangaAdapter1.ViewHolder> {
    private Context context;
    private List<MangaListDTO> mangaList;
    private String sourceActivity;

    public MangaAdapter1(Context context, List<MangaListDTO> mangaList,String sourceActivity) {
        this.context = context;
        this.mangaList = mangaList;
        this.sourceActivity = sourceActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manga, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MangaListDTO manga = mangaList.get(position);
        holder.tvTitle.setText(manga.getTitle());
        holder.tvGenre.setText("Thể loại: " + manga.getGenreName());
        holder.tvRating.setText("Đánh giá: " + manga.getAverageRating());

        // Bắt sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            if ("AdminActivity".equals(sourceActivity)) {
                intent = new Intent(context, AdminMangaDetailActivity.class);
            } else {
                intent = new Intent(context, AdminChapterDetailActivity.class);
            }
            intent.putExtra("MANGA_ID", manga.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvGenre, tvRating;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}
