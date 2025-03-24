package com.example.readinglmao.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.readinglmao.R;
import com.example.readinglmao.model.LoginRequest;
import com.example.readinglmao.model.LoginResponse;
import com.example.readinglmao.repository.AuthCallback;
import com.example.readinglmao.repository.AuthRepository;
import com.example.readinglmao.ui.AdminActivity;
import com.example.readinglmao.ui.MainActivity;

public class LoginFragment extends Fragment {

    private EditText edtUserName, edtPassword;
    private Button btnLogin, btnGoToRegister;
    private AuthRepository authRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        edtUserName = view.findViewById(R.id.edtUserName);
        edtPassword = view.findViewById(R.id.edtPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnGoToRegister = view.findViewById(R.id.btnGoToRegister);
        authRepository = new AuthRepository();

        btnLogin.setOnClickListener(v -> loginUser(view));

        btnGoToRegister.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        return view;
    }

    private void loginUser(View view) {
        String userNameOrEmail = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (userNameOrEmail.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập tên người dùng và mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest request = new LoginRequest(userNameOrEmail, password);

        authRepository.login(request, new AuthCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                if (getActivity() == null) return;

                LoginResponse.UserDTO userDTO = response.getUserDTO();
                if (userDTO == null) {
                    Log.e("LOGIN_DEBUG", "UserDTO is null");
                    return;
                }

                String role = userDTO.getRole();
                int userId = userDTO.getId();
                String userName = userDTO.getUserName();
                Log.d("LOGIN_DEBUG", "User Role: " + role);

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("userId", userId);
                editor.putString("username", userName);
                editor.apply();



                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    // Kiểm tra role và chuyển màn hình
                    if ("Admin".equalsIgnoreCase(response.getUserDTO().getRole())) {
                        startActivity(new Intent(getActivity(), AdminActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }

                    getActivity().finish(); // Đóng LoginFragment sau khi chuyển màn hình
                });
            }

            @Override
            public void onError(String error) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Lỗi đăng nhập: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
