package com.example.readinglmao.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MangaAdapter mangaAdapter;
    private List<MangaDTO> mangaList;

    public HomeFragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the manga list and RecyclerView
        mangaList = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recyclerView);  // Get the RecyclerView from the layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));  // Set the layout manager

        // Initialize the adapter
        mangaAdapter = new MangaAdapter(getContext(), mangaList); // Pass context to the adapter
        recyclerView.setAdapter(mangaAdapter);

        // You can fetch data from an API here or use dummy data
        fetchMangas();

        return rootView;
    }
    private void fetchMangas() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getMangas().enqueue(new Callback<List<MangaDTO>>() {
            @Override
            public void onResponse(Call<List<MangaDTO>> call, Response<List<MangaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Clear the existing list and add the new active manga data
                    mangaList.clear();
                    mangaList.addAll(response.body());
                    // Notify the adapter to refresh the data
                    mangaAdapter.notifyDataSetChanged();
                } else {
                    // Handle error
                    Log.e("HomeFragment", "API Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<MangaDTO>> call, Throwable t) {
                // Handle failure
                Log.e("HomeFragment", "API request failed: " + t.getMessage());
            }
        });
    }
}