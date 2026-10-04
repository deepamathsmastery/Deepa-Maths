package com.deepamaths.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        TextView tvAppName = findViewById(R.id.tvAppName);

        // ஃபேட்-இன் (Fade-in) அனிமேஷனை லோகோ மற்றும் பெயருக்கு வழங்குதல்
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(1500); // 1.5 விநாடிகள் அனிமேஷன் நேரம்

        // Null Check: ஐடி சரியாக இருந்தால் மட்டும் அனிமேஷன் நடக்கும் (Crash தவிர்க்கப்படும்)
        if (imgLogo != null) {
            imgLogo.startAnimation(fadeIn);
        }
        if (tvAppName != null) {
            tvAppName.startAnimation(fadeIn);
        }

        // 2.5 விநாடிகள் கழித்து நேராக MainActivity-க்குச் செல்லுதல்
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // இந்த ஆக்டிவிட்டியை மூடிவிடுவது
        }, 2500);
    }
}
