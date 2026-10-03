package com.deepamaths.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Switch switchDarkMode;

    // கார்டைக் கிளிக் செய்யும்போது 'துள்ளி வரும்' (Bounce) அனிமேஷனை இயக்குவதற்கான முறை
    private void playClickAnimationAndRun(View view, Runnable action) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.card_bounce);
        view.startAnimation(animation);
        
        // அனிமேஷன் முடியும் வரை (300ms) சிறிது தாமதித்துவிட்டு அடுத்த செயலைச் செய்தல்
        view.postDelayed(() -> {
            if (action != null) {
                action.run();
            }
        }, 300);
    }

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

        // --- ALL CARD / BUTTON CLICK LISTENERS WITH BOUNCE ANIMATION ---

        // Home Button
        setupCardWithAnimation(R.id.btnHome, v -> 
            Toast.makeText(this, "Home Page Opened", Toast.LENGTH_SHORT).show());

        // Dashboard -> Opens Web URL (student-dashboard)
        setupCardWithAnimation(R.id.btnDashboard, v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://deepamaths.com/student-dashboard/"));
                startActivity(browserIntent);
            } else {
                redirectToLogin();
            }
        });

        // Student Math Pad
        setupCardWithAnimation(R.id.btnMathPad, v -> checkLoginAndOpen(AITeacherActivity.class));

        // AI Lady Teacher (Web Chat)
        setupCardWithAnimation(R.id.btnAiTeacher, v -> checkLoginAndOpen(AIChatWebActivity.class));

        // Quiz Game
        setupCardWithAnimation(R.id.btnQuiz, v -> checkLoginAndOpen(QuizActivity.class));

        // Voice Doubt Support
        setupCardWithAnimation(R.id.btnVoiceDoubt, v -> checkLoginAndOpen(VoiceDoubtActivity.class));

        // Offline Mode
        setupCardWithAnimation(R.id.btnOffline, v -> startActivity(new Intent(MainActivity.this, OfflineModeActivity.class)));

        // Progress Tracker
        setupCardWithAnimation(R.id.btnProgress, v -> checkLoginAndOpen(ProgressTrackerActivity.class));

        // Badges & Achievements
        setupCardWithAnimation(R.id.btnBadges, v -> checkLoginAndOpen(BadgesActivity.class));

        // Daily Challenge
        setupCardWithAnimation(R.id.btnDailyChallenge, v -> checkLoginAndOpen(DailyChallengeActivity.class));

        // Export PDF Notes
        setupCardWithAnimation(R.id.btnExportPdf, v -> checkLoginAndOpen(ExportPdfActivity.class));

        // Instant Math Calculator
        setupCardWithAnimation(R.id.btnCalculator, v -> checkLoginAndOpen(MathCalculatorActivity.class));

        // Exam Mock Test Mode
        setupCardWithAnimation(R.id.btnExamMock, v -> checkLoginAndOpen(ExamMockActivity.class));

        // WhatsApp Doubt
        setupCardWithAnimation(R.id.btnWhatsapp, v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919876543210?text=Hello%20Teacher,%20I%20have%20a%20maths%20doubt."));
                startActivity(intent);
            } else {
                redirectToLogin();
            }
        });

        // Call Teacher
        setupCardWithAnimation(R.id.btnCall, v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9876543210"));
                startActivity(intent);
            } else {
                redirectToLogin();
            }
        });

        // Share App
        setupCardWithAnimation(R.id.btnShare, v -> {
            String shareMessage = "Deepa Maths ஆப் மூலம் எளிதாக கணிதத்தைக் கற்றுக்கொள்ளுங்கள்! மாணவர்களுக்கான சிறந்த செயலி.\n\n" +
                    "டவுன்லோட் செய்ய லிங்க்:\n" +
                    "https://play.google.com/store/apps/details?id=com.deepamaths.app";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(intent, "Deepa Maths-ஐப் பகிர (Share via):"));
        });

        // Logout Button
        setupCardWithAnimation(R.id.btnLogout, v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // எளிதாக அனிமேஷனை இணைக்க உதவும் சிறிய முறை (Helper Method)
    private void setupCardWithAnimation(int viewId, View.OnClickListener actionListener) {
        View view = findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(v -> playClickAnimationAndRun(v, () -> actionListener.onClick(v)));
        }
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
