package com.example.readinglmao.repository;

import com.example.readinglmao.model.LoginRequest;
import com.example.readinglmao.model.LoginResponse;
import com.example.readinglmao.model.RegisterRequest;
import com.example.readinglmao.model.RegisterResponse;
import com.example.readinglmao.service.ApiService;
import com.example.readinglmao.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private ApiService apiService;

    public AuthRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public void register(RegisterRequest request, final AuthCallback<RegisterResponse> callback) {
        apiService.register(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Đăng ký thất bại!");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.onError("Lỗi kết nối!");
            }
        });
    }

    public void login(LoginRequest request, final AuthCallback<LoginResponse> callback) {
        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Sai userName hoặc mật khẩu!");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError("Lỗi kết nối!");
            }
        });
    }
}