package com.example.readinglmao.repository;

public interface AuthCallback<T> {
    void onSuccess(T response);
    void onError(String error);
}