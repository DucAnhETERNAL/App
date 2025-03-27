package com.example.readinglmao.service;

import com.example.readinglmao.model.AddMangaRequestDTO;
import com.example.readinglmao.model.AdminMangaDetailsDTO;
import com.example.readinglmao.model.Chapter;

import com.example.readinglmao.model.CommentRequest;
import com.example.readinglmao.model.ChapterEditDTO;
import com.example.readinglmao.model.ChapterListDTO;
import com.example.readinglmao.model.ChapterRequest;
import com.example.readinglmao.model.ChapterResponse;
import com.example.readinglmao.model.ChapterText;
import com.example.readinglmao.model.LoginRequest;
import com.example.readinglmao.model.LoginResponse;
import com.example.readinglmao.model.MangaDTO;
import com.example.readinglmao.model.MangaDetailsDTO;
import com.example.readinglmao.model.MangaFavoriteDTO;
import com.example.readinglmao.model.MangaEditDTO;
import com.example.readinglmao.model.MangaListDTO;
import com.example.readinglmao.model.MangaListFavoriteDTO;
import com.example.readinglmao.model.RegisterRequest;
import com.example.readinglmao.model.RegisterResponse;
import com.example.readinglmao.model.User;
import com.example.readinglmao.ui.fragment.LoginFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    @GET("api/manga")
    Call<List<MangaDTO>> getMangasByCombinedFilter(@Query("$filter") String combinedFilter);

    @GET("api/manga/active") // Endpoint API bạn muốn gọi
    Call<List<MangaDTO>> getMangas();
    @GET("api/manga") // Endpoint API bạn muốn gọi
    Call<List<MangaListDTO>> getAllManga();
    @GET("api/ChapterText/{id}")
    Call<ChapterText> getChapterContent(@Path("id") int id);

    @POST("api/Register/register")  // Endpoint đăng ký
    Call<RegisterResponse> register(@Body RegisterRequest request);
    @GET("api/manga/{id}") // Endpoint lấy manga theo ID mà không cần userId
    Call<MangaDetailsDTO> getMangaDetails(@Path("id") int id);

    @GET("api/manga/{id}") // Endpoint lấy manga theo ID mà không cần userId
    Call<AdminMangaDetailsDTO> getAdminMangaDetails(@Path("id") int id);

    @GET("api/manga/{id}")
    Call<MangaListDTO> getAdminChapterDetails(@Path("id") int id);

    @PUT("api/Manga/{id}")
    Call<MangaEditDTO> updateManga(@Path("id") int id, @Body MangaEditDTO mangaEditDTO);

    @PUT("api/chapter/{id}")
    Call<ChapterEditDTO> updateChapter(@Path("id") int id, @Body ChapterEditDTO chapterEditDTO);

    @POST("api/Login/login")  // Endpoint đăng nhập
    Call<LoginResponse> login(@Body LoginRequest request);
    @POST("api/Comment/add")
    Call<Void> addComment(@Body CommentRequest commentRequest);
    @POST("api/Manga")
    Call<MangaDTO> addManga(@Body AddMangaRequestDTO mangaRequest);
    @POST("api/PersonalList/add")
    Call<Void> addToFavorites(@Body MangaFavoriteDTO manga);
    @GET("api/PersonalList/{userId}")
    Call<List<MangaListFavoriteDTO>> getUserFavorites(@Path("userId") int userId);
    @DELETE("api/PersonalList/remove/{userMangaListId}")
    Call<Void> removeFromFavorites(@Path("userMangaListId") int userMangaListId);

    @GET("api/manga/{id}")
    Call<List<Chapter>> getChapters(@Path("id") int mangaId);

    @POST("api/chapter")
    Call<ChapterResponse> addChapter(@Body ChapterRequest chapterRequest);

    @GET("api/chapter/{id}")
    Call<Chapter> getChapterById(@Path("id") int chapterId);

    @GET("api/ChapterText/{chapterId}")
    Call<ChapterText> getChapterTextById(@Path("chapterId") int chapterId);


}
