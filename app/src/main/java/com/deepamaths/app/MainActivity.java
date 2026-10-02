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

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        if (tvWelcome != null) {
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
            if (isLoggedIn) {
                String studentName = sharedPreferences.getString("studentName", "Student");
                tvWelcome.setText("Welcome, " + studentName + "!");
            } else {
                tvWelcome.setText("Welcome, Guest!");
            }
        }

        // 1. Home Button
        findViewById(R.id.btnHome).setOnClickListener(v -> 
            Toast.makeText(this, "Home Page Opened", Toast.LENGTH_SHORT).show());

        // 2. Dashboard / AI Teacher
        findViewById(R.id.btnDashboard).setOnClickListener(v -> 
            checkLoginAndOpen(AITeacherActivity.class));

        // 3. Student Math Pad
        findViewById(R.id.btnMathPad).setOnClickListener(v -> 
            checkLoginAndOpen(MathPadActivity.class));

        // 4. AI Lady Teacher
        findViewById(R.id.btnAiTeacher).setOnClickListener(v -> 
            checkLoginAndOpen(AITeacherActivity.class));

        // 5. Quiz Game
        findViewById(R.id.btnQuiz).setOnClickListener(v -> 
            Toast.makeText(this, "Quiz Game Started", Toast.LENGTH_SHORT).show());

        // 6. WhatsApp Doubt
        findViewById(R.id.btnWhatsapp).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919876543210?text=Hello%20Teacher,%20I%20have%20a%20maths%20doubt."));
                startActivity(intent);
            } else {
                redirectToLogin();
            }
        });

        // 7. Call Teacher
        findViewById(R.id.btnCall).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9876543210"));
                startActivity(intent);
            } else {
                redirectToLogin();
            }
        });

        // 8. Share App
        findViewById(R.id.btnShare).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Download Deepa Maths Mastery App for best online classes!");
            startActivity(Intent.createChooser(intent, "Share Via"));
        });

        // 9. Logout Button
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void checkLoginAndOpen(Class<?> targetActivityClass) {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, targetActivityClass);
            startActivity(intent);
        } else {
            redirectToLogin();
        }
    }

    private void redirectToLogin() {
        Toast.makeText(MainActivity.this, "தயவுசெய்து முதலில் Login செய்யவும்!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
