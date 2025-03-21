package com.example.readinglmao.ui;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readinglmao.R;
import com.example.readinglmao.adapter.MangaAdapter;
import com.example.readinglmao.model.MangaDTO;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MangaAdapter mangaAdapter;
    private List<MangaDTO> mangaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the manga list and RecyclerView
        mangaList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the adapter with context
        mangaAdapter = new MangaAdapter(this, mangaList); // Pass context (this) to the adapter
        recyclerView.setAdapter(mangaAdapter);

        // Fetch data from the API
        fetchMangas();
    }

    // Method to fetch manga data from the API
    private void fetchMangas() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getMangas().enqueue(new Callback<List<MangaDTO>>() {
            @Override
            public void onResponse(Call<List<MangaDTO>> call, Response<List<MangaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Clear the existing list and add the new manga data
                    mangaList.clear();
                    mangaList.addAll(response.body());
                    // Notify the adapter to refresh the data
                    mangaAdapter.notifyDataSetChanged();
                } else {
                    // Handle error
                    Log.e("MainActivity", "API Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<MangaDTO>> call, Throwable t) {
                // Handle failure
                Log.e("MainActivity", "API request failed: " + t.getMessage());
            }
        });
    }
}
