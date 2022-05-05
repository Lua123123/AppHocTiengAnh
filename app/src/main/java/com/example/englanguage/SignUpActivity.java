package com.example.englanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.englanguage.databinding.ActivitySignUpBinding;
import com.example.englanguage.viewmodel.LoginViewModel;
import com.example.englanguage.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    Context context = SignUpActivity.this;
    private String email;
    private String password;
    private String conformPassword;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActivitySignUpBinding activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        activitySignUpBinding.btnPostSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = activitySignUpBinding.edtEmail.getText().toString().trim();
                password = activitySignUpBinding.edtPassword.getText().toString().trim();
                conformPassword = activitySignUpBinding.edtConformPassword.getText().toString().trim();
                name = activitySignUpBinding.edtName.getText().toString().trim();
                SignUpViewModel signUpViewModel = new SignUpViewModel(email, password, name, conformPassword, context);
                signUpViewModel.clickSignUp();
            }
        });

    }
}