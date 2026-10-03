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

        // --- BUTTON CLICK LISTENERS (ሁሉም onCreate-க்குள் இருக்க வேண்டும்) ---

        // Home Button
        findViewById(R.id.btnHome).setOnClickListener(v -> 
            Toast.makeText(this, "Home Page Opened", Toast.LENGTH_SHORT).show());

        // Dashboard / AI Teacher
        findViewById(R.id.btnDashboard).setOnClickListener(v -> 
            checkLoginAndOpen(AITeacherActivity.class));

        // Student Math Pad
        findViewById(R.id.btnMathPad).setOnClickListener(v -> 
            checkLoginAndOpen(MathPadActivity.class));

        // AI Lady Teacher
        findViewById(R.id.btnAiTeacher).setOnClickListener(v -> 
            checkLoginAndOpen(AITeacherActivity.class));

        // Quiz Game
        findViewById(R.id.btnQuiz).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
            } else {
                redirectToLogin();
            }
        });

        // Voice Doubt Support
        findViewById(R.id.btnVoiceDoubt).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                startActivity(new Intent(MainActivity.this, VoiceDoubtActivity.class));
            } else {
                redirectToLogin();
            }
        });

        // Offline Mode
        findViewById(R.id.btnOffline).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, OfflineModeActivity.class));
        });

        // Progress Tracker
        findViewById(R.id.btnProgress).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                startActivity(new Intent(MainActivity.this, ProgressTrackerActivity.class));
            } else {
                redirectToLogin();
            }
        });

        // Badges & Achievements
        findViewById(R.id.btnBadges).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                startActivity(new Intent(MainActivity.this, BadgesActivity.class));
            } else {
                redirectToLogin();
            }
        });

        // Daily Challenge
        findViewById(R.id.btnDailyChallenge).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                startActivity(new Intent(MainActivity.this, DailyChallengeActivity.class));
            } else {
                redirectToLogin();
            }
        });

        // Export PDF Notes
        findViewById(R.id.btnExportPdf).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                startActivity(new Intent(MainActivity.this, ExportPdfActivity.class));
            } else {
                redirectToLogin();
            }
        });

        // Instant Math Calculator
        findViewById(R.id.btnCalculator).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                startActivity(new Intent(MainActivity.this, MathCalculatorActivity.class));
            } else {
                redirectToLogin();
            }
        });

        // Exam Mock Test Mode
        findViewById(R.id.btnExamMock).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                startActivity(new Intent(MainActivity.this, ExamMockActivity.class));
            } else {
                redirectToLogin();
            }
        });

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
            String shareMessage = "Deepa Maths ஆப் மூலம் எளிதாக கணிதத்தைக் கற்றுக்கொள்ளுங்கள்!\n\n" +
                    "டவுன்லோட் செய்ய லிங்க்:\n" +
                    "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(intent, "Deepa Maths-ஐப் பகிர (Share via):"));
        });

        // Logout Button
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
}package com.deepamaths.app;

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
    private Switch switchDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ஆப் தொடங்கும் முன்பே சேமிக்கப்பட்ட தீம் மோடை அமைத்தல்
        sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dark Mode Switch Setup
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

        // 8. Share App to WhatsApp, Facebook, Telegram, X, etc.
        findViewById(R.id.btnShare).setOnClickListener(v -> {
            String shareMessage = "Deepa Maths ஆப் மூலம் எளிதாக கணிதத்தைக் கற்றுக்கொள்ளுங்கள்! மாணவர்களுக்கான சிறந்த செயலி.\n\n" +
                    "டவுன்லோட் செய்ய லிங்க்:\n" +
                    "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            
            // இது பயனர் போனில் உள்ள WhatsApp, Telegram, Facebook, X, Reddit போன்ற அனைத்து ஆப்ஸையும் காட்டும்
            startActivity(Intent.createChooser(intent, "Deepa Maths-ஐப் பகிர (Share via):"));
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
    findViewById(R.id.btnBadges).setOnClickListener(v -> {
    if (sharedPreferences.getBoolean("isLoggedIn", false)) {
        startActivity(new Intent(MainActivity.this, BadgesActivity.java));
    } else {
        redirectToLogin();
    }
});
    findViewById(R.id.btnDailyChallenge).setOnClickListener(v -> {
    if (sharedPreferences.getBoolean("isLoggedIn", false)) {
        startActivity(new Intent(MainActivity.this, DailyChallengeActivity.java));
    } else {
        redirectToLogin();
    }
});
findViewById(R.id.btnExportPdf).setOnClickListener(v -> {
    if (sharedPreferences.getBoolean("isLoggedIn", false)) {
        startActivity(new Intent(MainActivity.this, ExportPdfActivity.class));
    } else {
        redirectToLogin();
    }
});
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
findViewById(R.id.btnOffline).setOnClickListener(v -> {
    startActivity(new Intent(MainActivity.this, OfflineModeActivity.java));
});
findViewById(R.id.btnVoiceDoubt).setOnClickListener(v -> {
    if (sharedPreferences.getBoolean("isLoggedIn", false)) {
        startActivity(new Intent(MainActivity.this, VoiceDoubtActivity.class));
    } else {
        redirectToLogin();
    }
});
findViewById(R.id.btnQuiz).setOnClickListener(v -> {
    if (sharedPreferences.getBoolean("isLoggedIn", false)) {
        startActivity(new Intent(MainActivity.this, QuizActivity.class));
    } else {
        redirectToLogin();
    }
});
findViewById(R.id.btnProgress).setOnClickListener(v -> {
    if (sharedPreferences.getBoolean("isLoggedIn", false)) {
        startActivity(new Intent(MainActivity.this, ProgressTrackerActivity.class));
    } else {
        redirectToLogin();
    }
});
findViewById(R.id.btnCalculator).setOnClickListener(v -> {
    if (sharedPreferences.getBoolean("isLoggedIn", false)) {
        startActivity(new Intent(MainActivity.this, MathCalculatorActivity.class));
    } else {
        redirectToLogin();
    }
});
findViewById(R.id.btnExamMock).setOnClickListener(v -> {
    if (sharedPreferences.getBoolean("isLoggedIn", false)) {
        startActivity(new Intent(MainActivity.this, ExamMockActivity.class));
    } else {
        redirectToLogin();
    }
});
