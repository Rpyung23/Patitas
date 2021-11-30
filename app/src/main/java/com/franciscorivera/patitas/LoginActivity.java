package com.franciscorivera.patitas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.franciscorivera.patitas.view.HomeActivity;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity
{
    private Handler oHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.oHandler = new Handler();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.oHandler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent oI = new Intent(LoginActivity.this, HomeActivity.class);
                oI.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(oI);
            }
        },3000);
    }
}