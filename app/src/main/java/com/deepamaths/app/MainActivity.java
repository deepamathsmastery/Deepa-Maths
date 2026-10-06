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

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    // கார்டைக் கிளிக் செய்யும்போது 'துள்ளி வரும்' (Bounce) அனிமேஷனை இயக்குவதற்கான முறை
    private void playClickAnimationAndRun(View view, Runnable action) {
        try {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.card_bounce);
            if (view != null && animation != null) {
                view.startAnimation(animation);
            }
        } catch (Exception e) {
            // அனிமேஷன் ஃபைலில் பிழை இருந்தாலும் ஆப் நிக்காது
        }
        
        View targetView = (view != null) ? view : getWindow().getDecorView();
        targetView.postDelayed(() -> {
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

            // 2. Safe Dark Mode Switch Setup
            View switchView = findViewById(R.id.switchDarkMode);
            if (switchView != null) {
                if (switchView instanceof androidx.appcompat.widget.SwitchCompat) {
                    androidx.appcompat.widget.SwitchCompat switchCompat = (androidx.appcompat.widget.SwitchCompat) switchView;
                    switchCompat.setChecked(isDarkMode);
                    switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> handleDarkModeChange(isChecked));
                } else if (switchView instanceof android.widget.Switch) {
                    android.widget.Switch standardSwitch = (android.widget.Switch) switchView;
                    standardSwitch.setChecked(isDarkMode);
                    standardSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> handleDarkModeChange(isChecked));
                }
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
            setupCardWithAnimation(R.id.btnCalculator, v -> openWebView("https://deepamaths.com/maths-magic-lab"));
            setupCardWithAnimation(R.id.btnExamMock, v -> openWebView("https://deepamaths.com/maths-mock-test"));

            // --- FORMULAS & SHORTCUTS ACTIVITIES ---
            setupCardWithAnimation(R.id.btnAlgebra, v -> openWebView("https://deepamaths.com/algebra"));
            setupCardWithAnimation(R.id.btnTrigonometry, v -> openWebView("https://deepamaths.com/trigonometry"));
            setupCardWithAnimation(R.id.btnCalculus, v -> openWebView("https://deepamaths.com/calculus"));
            setupCardWithAnimation(R.id.btnCoordinate, v -> openWebView("https://deepamaths.com/coordinate-geometry"));
            setupCardWithAnimation(R.id.btnVideoTutorials, v -> openWebView("https://deepamaths.com/video-tutorials"));

            // WhatsApp Doubt
            setupCardWithAnimation(R.id.btnWhatsapp, v -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919876543210?text=Hello%20Teacher,%20I%20have%20a%20maths%20doubt."));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
                }
            });

            // Call Teacher
            setupCardWithAnimation(R.id.btnCall, v -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919345934899"));
                    startActivity(intent);
                } catch (Exception e) {}
            });

            // Share App
            setupCardWithAnimation(R.id.btnShare, v -> {
                try {
                    String shareMessage = "Deepa Maths ஆப் மூலம் எளிதாக கணிதத்தைக் கற்றுக்கொள்ளுங்கள்! மாணவர்களுக்கான சிறந்த செயலி.\n\n" +
                            "டவுன்லோட் செய்ய லிங்க்:\n" +
                            "https://play.google.com/store/apps/details?id=com.deepamaths.app";
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(intent, "Deepa Maths-ஐப் பகிர (Share via):"));
                } catch (Exception e) {}
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in MainActivity: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleDarkModeChange(boolean isChecked) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isDarkMode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        } catch (Exception ignored) {}
    }

    private void openWebView(String url) {
        try {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLoginLogoutButton();
    }

    // Dynamic Login / Logout Button Handler
    private void updateLoginLogoutButton() {
        try {
            View btnLoginLogout = findViewById(R.id.btnLogout);
            if (btnLoginLogout != null) {
                
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                
                if (isLoggedIn) {
                    // 1. ஏற்கெனவே லாகின் செய்திருந்தால் -> "Logout" பட்டனாக மாற்றும் (Red Color)
                    if (btnLoginLogout instanceof Button) {
                        Button button = (Button) btnLoginLogout;
                        button.setText("Logout");
                        button.setBackgroundColor(Color.parseColor("#F44336")); 
                    } else if (btnLoginLogout instanceof TextView) {
                        TextView textView = (TextView) btnLoginLogout;
                        textView.setText("Logout");
                    }
                    
                    // Logout கிளிக் செய்தால் செஷனை கிளியர் செய்துவிட்டு வெப்சைட் logout URL-ஐ திறக்கும்
                    btnLoginLogout.setOnClickListener(v -> playClickAnimationAndRun(v, () -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", false);
                        editor.remove("studentName");
                        editor.apply();

                        Toast.makeText(MainActivity.this, "Successfully Logged Out!", Toast.LENGTH_SHORT).show();
                        
                        // வெப்சைட்டிலும் லாகின் கலைக்க logout URL-ஐ திறக்கவும்
                        openWebView("https://deepamaths.com/logout");
                        
                        // UI-ஐ உடனே ரெப்ரெஷ் செய்ய
                        recreate();
                    }));

                } else {
                    // 2. லாகின் செய்யவில்லை என்றால் -> "Student Dashboard" (Login) பட்டனாக காட்டும் (Blue Color)
                    if (btnLoginLogout instanceof Button) {
                        Button button = (Button) btnLoginLogout;
                        button.setText("Student Dashboard");
                        button.setBackgroundColor(Color.parseColor("#3F51B5")); 
                    } else if (btnLoginLogout instanceof TextView) {
                        TextView textView = (TextView) btnLoginLogout;
                        textView.setText("Student Dashboard");
                    }
                    
                    // கிளிக் செய்தால் டேஷ்போர்டு / லாகின் பக்கத்தை திறக்கும்
                    btnLoginLogout.setOnClickListener(v -> playClickAnimationAndRun(v, () -> {
                        openWebView("https://deepamaths.com/student-dashboard");
                    }));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
