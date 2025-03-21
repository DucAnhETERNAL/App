package com.example.readinglmao.ui.fragment;

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
import com.example.readinglmao.model.RegisterRequest;
import com.example.readinglmao.model.RegisterResponse;
import com.example.readinglmao.repository.AuthCallback;
import com.example.readinglmao.repository.AuthRepository;
import com.google.gson.Gson;

public class RegisterFragment extends Fragment {

    private EditText edtName, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnRegister, btnGoToLogin;
    private AuthRepository authRepository;
    private static final String TAG = "RegisterFragment"; // Tag để debug dễ dàng hơn

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        edtName = view.findViewById(R.id.edtName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnGoToLogin = view.findViewById(R.id.btnGoToLogin);
        edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword);
        authRepository = new AuthRepository();

        btnRegister.setOnClickListener(v -> registerUser(view));

        btnGoToLogin.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
        });

        return view;
    }

    private void registerUser(View view) {
        String username = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim(); // Lấy giá trị confirmPassword

        // Kiểm tra nhập đầy đủ thông tin
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu có trùng khớp không
        if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest request = new RegisterRequest(email, password, username, confirmPassword);

        authRepository.register(request, new AuthCallback<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse response) {
                if (getActivity() == null) return;

                Log.d("LOGIN_DEBUG", "API Response: " + response.toString());

                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
                });
            }

            @Override
            public void onError(String error) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Lỗi đăng ký: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}