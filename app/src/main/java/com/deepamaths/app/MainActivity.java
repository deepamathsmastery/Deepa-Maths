package com.deepamaths.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Switch switchDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 1. ஆப் தொடங்கும் முன்பே சேமிக்கப்பட்ட தீம் மோடை அமைத்தல்
        sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Dark Mode Switch Setup
        switchDarkMode = findViewById(R.id.switchDarkMode);
        if (switchDarkMode != null) {
            switchDarkMode.setChecked(isDarkMode);

            switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isDarkMode", isChecked);
                editor.apply();

                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            });
        }

        // 3. Welcome Message Setup
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

        // --- ALL BUTTON CLICK LISTENERS ---

        // Home Button
        findViewById(R.id.btnHome).setOnClickListener(v -> 
            Toast.makeText(this, "Home Page Opened", Toast.LENGTH_SHORT).show());

        // Dashboard -> Opens Web URL (student-dashboard)
        findViewById(R.id.btnDashboard).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://deepamaths.com/student-dashboard/"));
                startActivity(browserIntent);
            } else {
                redirectToLogin();
            }
        });

        // Student Math Pad
        findViewById(R.id.btnMathPad).setOnClickListener(v -> 
            checkLoginAndOpen(MathPadActivity.class));

        // AI Lady Teacher (Web Chat)
        findViewById(R.id.btnAiTeacher).setOnClickListener(v -> 
            checkLoginAndOpen(AIChatWebActivity.class));

        // Quiz Game
        findViewById(R.id.btnQuiz).setOnClickListener(v -> 
            checkLoginAndOpen(QuizActivity.class));

        // Voice Doubt Support
        findViewById(R.id.btnVoiceDoubt).setOnClickListener(v -> 
            checkLoginAndOpen(VoiceDoubtActivity.class));

        // Offline Mode
        findViewById(R.id.btnOffline).setOnClickListener(v -> 
            startActivity(new Intent(MainActivity.this, OfflineModeActivity.class)));

        // Progress Tracker
        findViewById(R.id.btnProgress).setOnClickListener(v -> 
            checkLoginAndOpen(ProgressTrackerActivity.class));

        // Badges & Achievements
        findViewById(R.id.btnBadges).setOnClickListener(v -> 
            checkLoginAndOpen(BadgesActivity.class));

        // Daily Challenge
        findViewById(R.id.btnDailyChallenge).setOnClickListener(v -> 
            checkLoginAndOpen(DailyChallengeActivity.class));

        // Export PDF Notes
        findViewById(R.id.btnExportPdf).setOnClickListener(v -> 
            checkLoginAndOpen(ExportPdfActivity.class));

        // Instant Math Calculator
        findViewById(R.id.btnCalculator).setOnClickListener(v -> 
            checkLoginAndOpen(MathCalculatorActivity.class));

        // Exam Mock Test Mode
        findViewById(R.id.btnExamMock).setOnClickListener(v -> 
            checkLoginAndOpen(ExamMockActivity.class));

        // WhatsApp Doubt
        findViewById(R.id.btnWhatsapp).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919876543210?text=Hello%20Teacher,%20I%20have%20a%20maths%20doubt."));
                startActivity(intent);
            } else {
                redirectToLogin();
            }
        });

        // Call Teacher
        findViewById(R.id.btnCall).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9876543210"));
                startActivity(intent);
            } else {
                redirectToLogin();
            }
        });

        // Share App
        findViewById(R.id.btnShare).setOnClickListener(v -> {
            String shareMessage = "Deepa Maths ஆப் மூலம் எளிதாக கணிதத்தைக் கற்றுக்கொள்ளுங்கள்! மாணவர்களுக்கான சிறந்த செயலி.\n\n" +
                    "டவுன்லோட் செய்ய லிங்க்:\n" +
                    "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(intent, "Deepa Maths-ஐப் பகிர (Share via):"));
        });

        // Logout Button (சரியான முறையில் இயங்கும் வகையில் அமைக்கப்பட்டுள்ளது)
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
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
        Toast..makeText(MainActivity.this, "தயவுசெய்து முதலில் Login செய்யவும்!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
