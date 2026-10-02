package com.deepamaths.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // லாகின் பாதுகாப்பு சரிபார்ப்பு
        SharedPreferences sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.java));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // மாணவரின் பெயரை வரவேற்பு பகுதியில் காட்ட
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        if (tvWelcome != null) {
            String studentName = sharedPreferences.getString("studentName", "Student");
            tvWelcome.setText("Welcome, " + studentName + "!");
        }

        // 1. AI Teacher (WebView பக்கத்திற்குச் செல்ல)
        findViewById(R.id.btnAiTeacher).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AITeacherActivity.class));
        });

        // 2. Student Math Pad (WebView பக்கத்திற்குச் செல்ல)
        findViewById(R.id.btnMathPad).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MathPadActivity.class));
        });

        // 3. Home / Dashboard
        findViewById(R.id.btnHome).setOnClickListener(v -> 
            Toast.makeText(this, "Home Page Opened", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnDashboard).setOnClickListener(v -> 
            Toast.makeText(this, "Dashboard Opened", Toast.LENGTH_SHORT).show());

        // 4. Quiz Game
        findViewById(R.id.btnQuiz).setOnClickListener(v -> 
            Toast.makeText(this, "Quiz Game Started", Toast.LENGTH_SHORT).show());

        // 5. WhatsApp Doubt
        findViewById(R.id.btnWhatsapp).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919876543210?text=Hello%20Teacher,%20I%20have%20a%20maths%20doubt."));
            startActivity(intent);
        });

        // 6. Call Teacher
        findViewById(R.id.btnCall).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9876543210"));
            startActivity(intent);
        });

        // 7. Share App
        findViewById(R.id.btnShare).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Download Deepa Maths Mastery App for best online classes!");
            startActivity(Intent.createChooser(intent, "Share Via"));
        });

        // 8. Logout
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}

