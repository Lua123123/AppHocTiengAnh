package com.example.englanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.englanguage.databinding.ActivityLoginBinding;
import com.example.englanguage.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    Context context = LoginActivity.this;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        email = bundle.getString("email");
        password = bundle.getString("password");
        activityLoginBinding.tvEmail.setText(email);
        activityLoginBinding.tvPassword.setText(password);

        activityLoginBinding.btnGetLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = activityLoginBinding.tvEmail.getText().toString().trim();
                password = activityLoginBinding.tvPassword.getText().toString().trim();
                LoginViewModel loginViewModel = new LoginViewModel(context, email, password);

                if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
                    loginViewModel.clickLogin();
                } else {
                    Toast toast = Toast.makeText(context, "EMAIL OR PASSWORD IS EMPTY", Toast.LENGTH_SHORT);
                    loginViewModel.customToast(toast);
                    return;
                }
            }
        });

    }
}