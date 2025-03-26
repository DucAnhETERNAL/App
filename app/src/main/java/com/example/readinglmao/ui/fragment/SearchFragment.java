package com.example.readinglmao.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readinglmao.R;
import com.example.readinglmao.adapter.MangaAdapter;
import com.example.readinglmao.model.MangaDTO;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private SearchView searchTitle, searchGenre;
    private RecyclerView recyclerViewResults;
    private MangaAdapter mangaAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize views
        searchTitle = rootView.findViewById(R.id.searchTitle);
        searchGenre = rootView.findViewById(R.id.searchGenre);
        recyclerViewResults = rootView.findViewById(R.id.recyclerViewResults);
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter for displaying results
        mangaAdapter = new MangaAdapter(getContext(), null);
        recyclerViewResults.setAdapter(mangaAdapter);

        // Set listeners on search fields to trigger search
        setupSearchView(searchTitle);
        setupSearchView(searchGenre);

        return rootView;
    }

    private void setupSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Call search method when user submits the search
                searchMangas();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // You can implement live filtering here if needed
                return false;
            }
        });
    }

    private void searchMangas() {
        String titleQuery = searchTitle.getQuery().toString().trim();
        String genreQuery = searchGenre.getQuery().toString().trim();

        if (TextUtils.isEmpty(titleQuery) && TextUtils.isEmpty(genreQuery)) {
            Toast.makeText(getContext(), "Please enter a search query", Toast.LENGTH_SHORT).show();
            return;
        }

        // Combine both title and genre filters
        String filterQuery = "";

        if (!TextUtils.isEmpty(titleQuery)) {
            filterQuery += "contains(title, '" + titleQuery + "')";
        }

        if (!TextUtils.isEmpty(genreQuery)) {
            if (!filterQuery.isEmpty()) {
                filterQuery += " and ";
            }
            filterQuery += "genreName eq '" + genreQuery + "'";
        }

        // Make API request with combined filter query
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getMangasByCombinedFilter(filterQuery).enqueue(new Callback<List<MangaDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<MangaDTO>> call, @NonNull Response<List<MangaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MangaDTO> mangas = response.body();
                    // Update RecyclerView with the results
                    mangaAdapter.updateMangaList(mangas);
                } else {
                    Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MangaDTO>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
