package com.deepamaths.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OfflineModeActivity extends AppCompatActivity {

    private TextView tvNetworkStatus, tvOfflineContent;
    private Button btnOfflineBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode);

        tvNetworkStatus = findViewById(R.id.tvNetworkStatus);
        tvOfflineContent = findViewById(R.id.tvOfflineContent);
        btnOfflineBack = findViewById(R.id.btnOfflineBack);

        // நெட்வொர்க் நிலையைச் சோதித்தல்
        checkNetworkConnection();

        // ஆஃப்லைனில் படிக்கக்கூடிய கணிதக் குறிப்புகள் & பார்முலாக்கள்
        String offlineFormulas = 
                "1. இயற்கணிதம் (Algebra):\n" +
                "• (a + b)² = a² + 2ab + b²\n" +
                "• (a - b)² = a² - 2ab + b²\n" +
                "• a² - b² = (a + b)(a - b)\n\n" +

                "2. வடிவியல் (Geometry):\n" +
                "• வட்டத்தின் பரப்பளவு (Area of Circle) = πr²\n" +
                "• வட்டத்தின் சுற்றளவு (Circumference) = 2πr\n" +
                "• செவ்வகத்தின் பரப்பளவு = நீளம் × அகலம்\n\n" +

                "3. முக்கோணவியல் (Trigonometry):\n" +
                "• sin²θ + cos²θ = 1\n" +
                "• 1 + tan²θ = sec²θ\n\n" +
                "*(குறிப்பு: இந்தத் தரவுகள் ஆப்பிலேயே சேமிக்கப்பட்டுள்ளதால் இன்டர்நெட் இல்லாமலும் படிக்கலாம்!)*";

        tvOfflineContent.setText(offlineFormulas);

        // பின் செல்ல (Back)
        btnOfflineBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            tvNetworkStatus.setText("🟢 இணைய இணைப்பு உள்ளது (Online Mode)");
            tvNetworkStatus.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            tvNetworkStatus.setText("🔴 இணைய இணைப்பு இல்லை (Offline Mode Active)");
            tvNetworkStatus.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }
}
