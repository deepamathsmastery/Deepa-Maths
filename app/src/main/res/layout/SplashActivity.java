package com.deepamaths.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 2 விநாடிகள் கழித்து லாகின் அல்லது மெயின் பக்கத்திற்குச் செல்ல
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.java);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
