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

        // 1. லாகின் பாதுகாப்பு சரிபார்ப்பு (Login Check before opening MainActivity)
        sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // 2. மாணவரின் பெயரை வரவேற்பு பகுதியில் காட்ட
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        if (tvWelcome != null) {
            String studentName = sharedPreferences.getString("studentName", "Student");
            tvWelcome.setText("Welcome, " + studentName + "!");
        }

        // 3. AI Teacher (நேரடியாக WebView பக்கத்திற்குச் செல்ல)
        findViewById(R.id.btnAiTeacher).setOnClickListener(v -> {
            checkLoginAndOpen(AITeacherActivity.class);
        });

        // 4. Student Math Pad (நேரடியாக WebView பக்கத்திற்குச் செல்ல)
        findViewById(R.id.btnMathPad).setOnClickListener(v -> {
            checkLoginAndOpen(MathPadActivity.class);
        });

        // 5. Dashboard / Home Buttons
        findViewById(R.id.btnHome).setOnClickListener(v -> 
            Toast.makeText(this, "Home Page Opened", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnDashboard).setOnClickListener(v -> 
            checkLoginAndOpen(AITeacherActivity.class)); // தேவைக்கேற்ப மாற்றிக்கொள்ளலாம்

        // 6. Quiz Game
        findViewById(R.id.btnQuiz).setOnClickListener(v -> 
            Toast.makeText(this, "Quiz Game Started", Toast.LENGTH_SHORT).show());

        // 7. WhatsApp Doubt
        findViewById(R.id.btnWhatsapp).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919876543210?text=Hello%20Teacher,%20I%20have%20a%20maths%20doubt."));
                startActivity(intent);
            } else {
                redirectToLogin();
            }
        });

        // 8. Call Teacher
        findViewById(R.id.btnCall).setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9876543210"));
                startActivity(intent);
            } else {
                redirectToLogin();
            }
        });

        // 9. Share App (அனைவரும் ஷேர் செய்யலாம்)
        findViewById(R.id.btnShare).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Download Deepa Maths Mastery App for best online classes!");
            startActivity(Intent.createChooser(intent, "Share Via"));
        });

        // 10. Logout
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.On("Logged out successfully", Toast.LENGTH_SHORT).show(); // Fixed to Toast.makeText
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.java));
            finish();
        });
    }

    // லாகின் செய்துள்ளாரா எனச் சரிபார்த்து திறக்கும் பொதுவான முறை (Method)
    private void checkLoginAndOpen(Class<?> targetActivityClass) {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, targetActivityClass);
            startActivity(intent);
        } else {
            redirectToLogin();
        }
    }

    // லாகின் செய்யவில்லை என்றால் காட்டும் பொதுவான முறை
    private void redirectToLogin() {
        Toast.makeText(MainActivity.this, "தயவுசெய்து முதலில் Login செய்யவும்!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
