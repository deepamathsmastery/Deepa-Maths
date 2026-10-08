package com.deepamaths.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
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

            // --- EACH CARD WITH ITS OWN CORRECT UNIQUE URL & ANIMATION ---

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
            setupCardWithAnimation(R.id.btnCalculator, v -> openWebView("https://deepamaths.com/maths-magic-lab"));

            // --- FORMULAS & SHORTCUTS ACTIVITIES ---
            setupCardWithAnimation(R.id.btnAlgebra, v -> openWebView("https://deepamaths.com/algebra"));
            setupCardWithAnimation(R.id.btnTrigonometry, v -> openWebView("https://deepamaths.com/trigonometry"));
            setupCardWithAnimation(R.id.btnCalculus, v -> openWebView("https://deepamaths.com/calculus"));
            setupCardWithAnimation(R.id.btnVideoTutorials, v -> openWebView("https://www.youtube.com/channel/UCzRuYY0ufMb0noxwzN-vL1w"));

            // WhatsApp Doubt
            setupCardWithAnimation(R.id.btnWhatsapp, v -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919345934899?text=Hello%20Teacher,%20I%20have%20a%20maths%20doubt."));
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

    private boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                return capabilities != null && (
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void openWebView(String url) {
        try {
            if (isNetworkAvailable()) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            } else {
                Toast.makeText(this, "இணைய இணைப்பு இல்லை! தயவுசெய்து இன்டர்நெட்டை ஆன் செய்யவும்.", Toast.LENGTH_LONG).show();
            }
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

    private void updateLoginLogoutButton() {
        try {
            View btnLoginLogout = findViewById(R.id.btnLogout);
            if (btnLoginLogout != null) {
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                
                if (isLoggedIn) {
                    if (btnLoginLogout instanceof Button) {
                        Button button = (Button) btnLoginLogout;
                        button.setText("Logout");
                        button.setBackgroundColor(Color.parseColor("#F44336")); 
                    }
                    btnLoginLogout.setOnClickListener(v -> playClickAnimationAndRun(v, () -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", false);
                        editor.remove("studentName");
                        editor.apply();

                        Toast.makeText(MainActivity.this, "Successfully Logged Out!", Toast.LENGTH_SHORT).show();
                        openWebView("https://deepamaths.com/wp-login.php?action=logout");
                        recreate();
                    }));
                } else {
                    if (btnLoginLogout instanceof Button) {
                        Button button = (Button) btnLoginLogout;
                        button.setText("Student Dashboard");
                        button.setBackgroundColor(Color.parseColor("#4F46E5")); 
                    }
                    btnLoginLogout.setOnClickListener(v -> playClickAnimationAndRun(v, () -> {
                        openWebView("https://deepamaths.com/student-dashboard");
                    }));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Smooth Click Animation + Action Runner Method
    private void setupCardWithAnimation(int cardId, View.OnClickListener action) {
        View card = findViewById(cardId);
        if (card != null) {
            card.setOnClickListener(v -> {
                v.performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY);
                v.animate().scaleX(0.92f).scaleY(0.92f).setDuration(80).withEndAction(() -> {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(80).withEndAction(() -> {
                        action.onClick(v);
                    }).start();
                }).start();
            });
        }
    }
}
