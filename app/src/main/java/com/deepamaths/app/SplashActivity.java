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
        
        try {
            setContentView(R.layout.activity_splash);

            ImageView imgLogo = findViewById(R.id.imgLogo);
            TextView tvAppName = findViewById(R.id.tvAppName);

            // அனிமேஷன் பாதுகாப்புடன் வழங்கப்படுகிறது
            try {
                Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                fadeIn.setDuration(1500);

                if (imgLogo != null) {
                    imgLogo.startAnimation(fadeIn);
                }
                if (tvAppName != null) {
                    tvAppName.startAnimation(fadeIn);
                }
            } catch (Exception e) {
                // அனிமேஷனில் பிழை இருந்தாலும் ஆப் நிக்காது
            }

        } catch (Exception e) {
            // லேஅவுட்டில் பிழை இருந்தால் நேரடியாக MainActivity-க்குத் தாவுவதற்கு
            navigateToMain();
            return;
        }

        // 2.5 விநாடிகள் கழித்து MainActivity-க்குச் செல்லுதல்
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            navigateToMain();
        }, 2500);
    }

    private void navigateToMain() {
        try {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
