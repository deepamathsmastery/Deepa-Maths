package com.deepamaths.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DailyChallengeActivity extends AppCompatActivity {

    private TextView tvDailyQuestion;
    private EditText etDailyAnswer;
    private Button btnSubmitDaily, btnDailyBack;
    private SharedPreferences sharedPreferences;

    // தினசரி கேள்விகள் மற்றும் பதில்கள் பட்டியல்
    private String[] challenges = {
            "12 இன் 25% மதிப்பு என்ன?",
            "ஒரு செவ்வகத்தின் நீளம் 10cm, அகலம் 5cm எனில் பரப்பளவு என்ன? (எண் மட்டும்)",
            "7 இன் வர்க்கம் (Square) என்ன?",
            "100-ஐ 5ஆல் வகுத்தால் வரும் ஈவு (Quotient) என்ன?"
    };

    private String[] answers = {
            "3", "50", "49", "20"
    };

    private int todayIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_challenge);

        tvDailyQuestion = findViewById(R.id.tvDailyQuestion);
        etDailyAnswer = findViewById(R.id.etDailyAnswer);
        btnSubmitDaily = findViewById(R.id.btnSubmitDaily);
        btnDailyBack = findViewById(R.id.btnDailyBack);

        sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);

        // இன்றைய நாளின் அடிப்படையில் ஒரு கேள்வியைத் தேர்ந்தெடுத்தல் (Day of the year)
        int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        todayIndex = dayOfYear % challenges.length;

        tvDailyQuestion.setText(challenges[todayIndex]);

        // பதிலைச் சமர்ப்பித்தல்
        btnSubmitDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = etDailyAnswer.getText().toString().trim();

                if (userAnswer.isEmpty()) {
                    Toast.makeText(DailyChallengeActivity.this, "தயவுசெய்து விடையை உள்ளிடவும்!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userAnswer.equals(answers[todayIndex])) {
                    Toast.makeText(DailyChallengeActivity.this, "🎉 சரியான விடை! வாழ்த்துகள்!", Toast.LENGTH_LONG).show();
                    
                    // புள்ளிகளைச் சேமித்தல்
                    int currentScore = sharedPreferences.getInt("quizScore", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("quizScore", currentScore + 15); // சவாலுக்கு 15 புள்ளிகள்
                    editor.apply();

                    finish();
                } else {
                    Toast.makeText(DailyChallengeActivity.this, "❌ தவறான விடை. மீண்டும் முயற்சிக்கவும்!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // பின் செல்ல (Back)
        btnDailyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
