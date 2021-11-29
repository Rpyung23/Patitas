package com.franciscorivera.patitas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.franciscorivera.patitas.view.HomeActivity;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity
{
    private MaterialButton oMaterialButtonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.oMaterialButtonLogin = findViewById(R.id.btnLogin_);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.oMaterialButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent oI = new Intent(LoginActivity.this, HomeActivity.class);
                oI.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(oI);
            }
        });
    }
}