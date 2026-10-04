package com.deepamaths.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SwitchCompat switchDarkMode;

    // கார்டைக் கிளிக் செய்யும்போது 'துள்ளி வரும்' (Bounce) அனிமேஷனை இயக்குவதற்கான முறை
    private void playClickAnimationAndRun(View view, Runnable action) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.card_bounce);
        view.startAnimation(animation);
        
        view.postDelayed(() -> {
            if (action != null) {
                action.run();
            }
        }, 300);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // 1. Home Button -> Opens Website
        setupCardWithAnimation(R.id.btnHome, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com");
            startActivity(intent);
        });

        // 2. Dashboard / Login Button
        setupCardWithAnimation(R.id.btnDashboard, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/student-dashboard");
            startActivity(intent);
        });

        // 3. Homework Portal
        setupCardWithAnimation(R.id.btnHomework, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/homework");
            startActivity(intent);
        });

        // 4. Student Math Pad
        setupCardWithAnimation(R.id.btnMathPad, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/math-pad");
            startActivity(intent);
        });

        // 5. AI Lady Teacher
        setupCardWithAnimation(R.id.btnAiTeacher, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/ai-teacher");
            startActivity(intent);
        });

        // 6. Quiz Game
        setupCardWithAnimation(R.id.btnQuiz, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/quiz");
            startActivity(intent);
        });

        // 7. Voice Doubt Support
        setupCardWithAnimation(R.id.btnVoiceDoubt, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/voice-doubt-php");
            startActivity(intent);
        });

        // 8. Offline Mode
        setupCardWithAnimation(R.id.btnOffline, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/offline-mode");
            startActivity(intent);
        });

        // 9. Progress Tracker
        setupCardWithAnimation(R.id.btnProgress, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/progress");
            startActivity(intent);
        });

        // 10. Badges & Achievements
        setupCardWithAnimation(R.id.btnBadges, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/badges");
            startActivity(intent);
        });

        // 11. Daily Challenge
        setupCardWithAnimation(R.id.btnDailyChallenge, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/daily-challenge");
            startActivity(intent);
        });

        // 12. Export PDF Notes
        setupCardWithAnimation(R.id.btnExportPdf, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/export-pdf");
            startActivity(intent);
        });

        // 13. Instant Math Calculator
        setupCardWithAnimation(R.id.btnCalculator, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/calculator");
            startActivity(intent);
        });

        // 14. Exam Mock Test Mode
        setupCardWithAnimation(R.id.btnExamMock, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/maths-mock-test");
            startActivity(intent);
        });

        // --- FORMULAS & SHORTCUTS ACTIVITIES ---
        setupCardWithAnimation(R.id.btnAlgebra, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/algebra");
            startActivity(intent);
        });

        setupCardWithAnimation(R.id.btnTrigonometry, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/trigonometry");
            startActivity(intent);
        });

        setupCardWithAnimation(R.id.btnCalculus, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/calculus");
            startActivity(intent);
        });

        setupCardWithAnimation(R.id.btnCoordinate, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/coordinate-geometry");
            startActivity(intent);
        });

        setupCardWithAnimation(R.id.btnVideoTutorials, v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://deepamaths.com/video-tutorials");
            startActivity(intent);
        });

        // WhatsApp Doubt
        setupCardWithAnimation(R.id.btnWhatsapp, v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919876543210?text=Hello%20Teacher,%20I%20have%20a%20maths%20doubt."));
            startActivity(intent);
        });

        // Call Teacher
        setupCardWithAnimation(R.id.btnCall, v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919345934899"));
            startActivity(intent);
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

        // Dynamic Login / Logout Button Click Listener
        setupCardWithAnimation(R.id.btnLogout, v -> {
            CookieManager cookieManager = CookieManager.getInstance();
            String cookies = cookieManager.getCookie("https://deepamaths.com/");
            boolean isWebLoggedIn = cookies != null && !cookies.isEmpty();

            if (isWebLoggedIn) {
                cookieManager.removeAllCookies(null);
                cookieManager.flush();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", "https://deepamaths.com/student-dashboard");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLoginLogoutButton();
    }

    private void updateLoginLogoutButton() {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie("https://deepamaths.com/");
        boolean isWebLoggedIn = cookies != null && !cookies.isEmpty();

        View btnLoginLogout = findViewById(R.id.btnLogout);
        if (btnLoginLogout != null) {
            if (btnLoginLogout instanceof Button) {
                Button button = (Button) btnLoginLogout;
                if (isWebLoggedIn) {
                    button.setBackgroundColor(Color.parseColor("#D32F2F"));
                    button.setText("Logout");
                } else {
                    button.setBackgroundColor(Color.parseColor("#4CAF50"));
                    button.setText("Login");
                }
            } else if (btnLoginLogout instanceof TextView) {
                TextView textView = (TextView) btnLoginLogout;
                if (isWebLoggedIn) {
                    textView.setText("Logout");
                } else {
                    textView.setText("Login");
                }
            }
        }
    }

    private void setupCardWithAnimation(int viewId, View.OnClickListener actionListener) {
        View view = findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(v -> playClickAnimationAndRun(v, () -> actionListener.onClick(v)));
        }
    }
}
