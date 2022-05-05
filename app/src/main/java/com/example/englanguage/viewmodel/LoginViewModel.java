package com.example.englanguage.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.englanguage.MainActivity;
import com.example.englanguage.R;
import com.example.englanguage.model.login.Login;
import com.example.englanguage.model.login.UserLogin;
import com.example.englanguage.network.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private Boolean remember_me = true;
    private Context context;
    private String email;
    private String password;

    public LoginViewModel(Context context, String email, String password) {
        this.context = context;
        this.email = email;
        this.password = password;
    }

    public void clickLogin() {
        UserLogin userLogin = new UserLogin(email, password, remember_me);
        API.api.getUsers(userLogin).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                Toast toast = Toast.makeText(context, "LOGIN SUCCESSFULLY", Toast.LENGTH_SHORT);
                customToast(toast);
                Intent intent = new Intent(context, MainActivity.class);
                Bundle bundle = new Bundle();
                String tokenType = login.getData().getToken_type().toString().trim();
                String accessToken = login.getData().getAccess_token().toString().trim();
                String Authorization = tokenType + " " + accessToken;
                bundle.putString("Authorization", Authorization);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast toast = Toast.makeText(context, "LOGIN FAILED", Toast.LENGTH_SHORT);
                customToast(toast);
            }
        });
    }

    public void customToast(Toast toast) {
        View toastView = toast.getView();
        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(13);
        toastMessage.setTextColor(Color.YELLOW);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setCompoundDrawablePadding(4);
        toastView.setBackgroundColor(Color.BLACK);
        toastView.setBackgroundResource(R.drawable.bg_toast);
        toast.show();
    }
}
