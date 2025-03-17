package com.example.readinglmao.service;

import com.example.readinglmao.model.MangaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface ApiService {
    @GET("api/manga") // Endpoint API bạn muốn gọi
    Call<List<MangaDTO>> getMangas();
}
