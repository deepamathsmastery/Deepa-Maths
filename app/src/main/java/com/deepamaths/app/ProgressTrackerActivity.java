package com.deepamaths.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProgressTrackerActivity extends AppCompatActivity {

    private TextView tvQuizScoreStats, tvMathPadStats, tvOverallPerformance;
    private Button btnResetProgress, btnProgressBack;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracker);

        tvQuizScoreStats = findViewById(R.id.tvQuizScoreStats);
        tvMathPadStats = findViewById(R.id.tvMathPadStats);
        tvOverallPerformance = findViewById(R.id.tvOverallPerformance);
        btnResetProgress = findViewById(R.id.btnResetProgress);
        btnProgressBack = findViewById(R.id.btnProgressBack);

        sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);

        // சேமிக்கப்பட்ட தரவுகளை ஏற்றுதல்
        loadProgressData();

        // மதிப்பெண்களை மீட்டமைக்க (Reset)
        btnResetProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("quizScore", 0);
                editor.putInt("solvedCount", 0);
                editor.apply();

                loadProgressData();
                Toast.makeText(ProgressTrackerActivity.this, "புள்ளிவிவரங்கள் அழிக்கப்பட்டன!", Toast.LENGTH_SHORT).show();
            }
        });

        // பின் செல்ல (Back)
        btnProgressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadProgressData() {
        // SharedPreferences-ல் இருந்து மதிப்பெண்களை எடுத்தல் (இல்லாவிட்டால் இயல்புநிலை 0)
        int quizScore = sharedPreferences.getInt("quizScore", 0);
        int solvedCount = sharedPreferences.getInt("solvedCount", 0);

        tvQuizScoreStats.setText("வினாடி வினா மதிப்பெண்: " + quizScore + " புள்ளிகள்");
        tvMathPadStats.setText("தீர்க்கப்பட்ட கணக்குகள்: " + solvedCount + " கணக்குகள்");

        if (quizScore >= 30) {
            tvOverallPerformance.setText("திறமை நிலை: மிகச் சிறப்பு (Expert)! 🌟");
        } else if (quizScore >= 10) {
            tvOverallPerformance.setText("திறமை நிலை: நன்று (Intermediate)! 👍");
        } else {
            tvOverallPerformance.setText("திறமை நிலை: தொடக்கநிலை (Beginner) 🌱");
        }
    }
}
