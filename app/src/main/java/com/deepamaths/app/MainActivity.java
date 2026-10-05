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
        try {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.card_bounce);
            view.startAnimation(animation);
        } catch (Exception e) {
            // அனிமேஷன் ஃபைலில் பிழை இருந்தாலும் ஆப் நிக்காது
        }
        
        view.postDelayed(() -> {
            if (action != null) {
                action.run();
            }
        }, 300);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            setContentView(R.layout.activity_main);

            sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);
            boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);

            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

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

            // --- EACH CARD WITH ITS OWN CORRECT UNIQUE URL ---

            setupCardWithAnimation(R.id.btnHome, v -> openWebView("https://deepamaths.com"));
            setupCardWithAnimation(R.id.btnDashboard, v -> openWebView("https://deepamaths.com/student-dashboard"));
            setupCardWithAnimation(R.id.btnHomework, v -> openWebView("https://deepamaths.com/homework"));
            setupCardWithAnimation(R.id.btnMathPad, v -> openWebView("https://deepamaths.com/math-pad"));
            setupCardWithAnimation(R.id.btnAiTeacher, v -> openWebView("https://deepamaths.com/ai-teacher"));
            setupCardWithAnimation(R.id.btnQuiz, v -> openWebView("https://deepamaths.com/quiz"));
            setupCardWithAnimation(R.id.btnVoiceDoubt, v -> openWebView("https://deepamaths.com/voice-doubt-php"));
            setupCardWithAnimation(R.id.btnOffline, v -> openWebView("https://deepamaths.com/offline-mode"));
            setupCardWithAnimation(R.id.btnProgress, v -> openWebView("https://deepamaths.com/progress"));
            setupCardWithAnimation(R.id.btnBadges, v -> openWebView("https://deepamaths.com/badges"));
            setupCardWithAnimation(R.id.btnDailyChallenge, v -> openWebView("https://deepamaths.com/daily-challenge"));
            setupCardWithAnimation(R.id.btnExportPdf, v -> openWebView("https://deepamaths.com/export-pdf"));
            setupCardWithAnimation(R.id.btnCalculator, v -> openWebView("https://deepamaths.com/calculator"));
            setupCardWithAnimation(R.id.btnExamMock, v -> openWebView("https://deepamaths.com/maths-mock-test"));

            // --- FORMULAS & SHORTCUTS ACTIVITIES ---
            setupCardWithAnimation(R.id.btnAlgebra, v -> openWebView("https://deepamaths.com/algebra"));
            setupCardWithAnimation(R.id.btnTrigonometry, v -> openWebView("https://deepamaths.com/trigonometry"));
            setupCardWithAnimation(R.id.btnCalculus, v -> openWebView("https://deepamaths.com/calculus"));
            setupCardWithAnimation(R.id.btnCoordinate, v -> openWebView("https://deepamaths.com/coordinate-geometry"));
            setupCardWithAnimation(R.id.btnVideoTutorials, v -> openWebView("https://deepamaths.com/video-tutorials"));

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

            // Bottom Button -> Dashboard
            setupCardWithAnimation(R.id.btnLogout, v -> openWebView("https://deepamaths.com/student-dashboard"));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in MainActivity: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void openWebView(String url) {
        try {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLoginLogoutButton();
    }

    private void updateLoginLogoutButton() {
        try {
            View btnLoginLogout = findViewById(R.id.btnLogout);
            if (btnLoginLogout != null) {
                if (btnLoginLogout instanceof Button) {
                    Button button = (Button) btnLoginLogout;
                    button.setBackgroundColor(Color.parseColor("#4CAF50"));
                    button.setText("Student Dashboard");
                } else if (btnLoginLogout instanceof TextView) {
                    TextView textView = (TextView) btnLoginLogout;
                    textView.setText("Student Dashboard");
                }
            }
        } catch (Exception e) {
            // பிழை வராமல் தவிர்க்க
        }
    }

    private void setupCardWithAnimation(int viewId, View.OnClickListener actionListener) {
        try {
            View view = findViewById(viewId);
            if (view != null) {
                view.setOnClickListener(v -> playClickAnimationAndRun(v, () -> actionListener.onClick(v)));
            }
        } catch (Exception e) {
            // பிழைகளைக் கையாளுதல்
        }
    }
}
