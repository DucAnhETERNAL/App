package com.example.readinglmao.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readinglmao.R;
import com.example.readinglmao.adapter.MangaAdapter;
import com.example.readinglmao.model.MangaDTO;
import com.example.readinglmao.model.MangaListFavoriteDTO;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private MangaAdapter mangaAdapter;
    private List<MangaDTO> favoriteMangaList;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        // Initialize RecyclerView and adapter
        recyclerView = rootView.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        favoriteMangaList = new ArrayList<>();
        mangaAdapter = new MangaAdapter(getContext(), favoriteMangaList);
        recyclerView.setAdapter(mangaAdapter);

        fetchUserFavorites();

        return rootView;
    }

    private void fetchUserFavorites() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(getContext(), "User ID not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getUserFavorites(userId).enqueue(new Callback<List<MangaListFavoriteDTO>>() {
            @Override
            public void onResponse(Call<List<MangaListFavoriteDTO>> call, Response<List<MangaListFavoriteDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MangaListFavoriteDTO> favoriteList = response.body();
                    Set<Integer> favoriteMangaIds = new HashSet<>();
                    for (MangaListFavoriteDTO favorite : favoriteList) {
                        favoriteMangaIds.add(favorite.getMangaId());
                    }
                    fetchMangas(favoriteMangaIds);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch favorite mangas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MangaListFavoriteDTO>> call, Throwable t) {
                Log.e("FavoriteFragment", "API request failed: " + t.getMessage());
            }
        });
    }

    private void fetchMangas(Set<Integer> favoriteMangaIds) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getMangas().enqueue(new Callback<List<MangaDTO>>() {
            @Override
            public void onResponse(Call<List<MangaDTO>> call, Response<List<MangaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    favoriteMangaList.clear();
                    for (MangaDTO manga : response.body()) {
                        if (favoriteMangaIds.contains(manga.getId())) {
                            favoriteMangaList.add(manga);
                        }
                    }
                    mangaAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch manga list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MangaDTO>> call, Throwable t) {
                Log.e("FavoriteFragment", "API request failed: " + t.getMessage());
            }
        });
    }
}
