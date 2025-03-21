package com.example.readinglmao.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.readinglmao.ui.fragment.LoginRegisterActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.readinglmao.databinding.ActivitySplashBinding;

import com.example.readinglmao.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000; // Thời gian hiển thị (3 giây)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Chờ 3 giây rồi chuyển sang LoginActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginRegisterActivity.class);
            startActivity(intent);
            finish(); // Đóng SplashActivity
        }, SPLASH_TIME_OUT);
    }
}