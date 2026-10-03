package com.deepamaths.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore;
    private RadioGroup radioGroupOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private Button btnSubmitAnswer, btnQuizBack;

    // வினாடி வினா கேள்விகள் மற்றும் பதில்கள்
    private String[] questions = {
            "1. 5 + 3 × 2 இன் மதிப்பு என்ன?",
            "2. 12 இன் வர்க்கமூலம் (Square Root) என்ன?",
            "3. ஒரு முக்கோணத்தின் மூன்று கோணங்களின் கூடுதல் என்ன?"
    };

    private String[][] options = {
            {"16", "11", "10", "13"},
            {"4", "6", "3", "5"},
            {"90°", "180°", "360°", "270°"}
    };

    // சரியான பதில்களின் இன்டெக்ஸ் (0 முதல் தொடங்குகிறது)
    private int[] correctAnswers = {1, 1, 1}; // முறையே 11, 6, 180°

    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        rbOption4 = findViewById(R.id.rbOption4);
        btnSubmitAnswer = findViewById(R.id.btnSubmitAnswer);
        btnQuizBack = findViewById(R.id.btnQuizBack);

        loadQuestion();

        btnSubmitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupOptions.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(QuizActivity.this, "தயவுசெய்து ஒரு விடையைத் தேர்ந்தெடுக்கவும்!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = findViewById(selectedId);
                int answerIndex = radioGroupOptions.indexOfChild(selectedRadioButton);

                // சரியான பதிலா எனச் சோதித்தல்
                if (answerIndex == correctAnswers[currentQuestionIndex]) {
                    score += 10;
                    Toast.makeText(QuizActivity.this, "சரியான விடை! (+10 புள்ளிகள்)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QuizActivity.this, "தவறான விடை!", Toast.LENGTH_SHORT).show();
                }

                tvScore.setText("மதிப்பெண் (Score): " + score);
                radioGroupOptions.clearCheck();

                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    loadQuestion();
                } else {
                    // வினாடி வினா நிறைவு பெற்றது
                    tvQuestion.setText("🎉 வாழ்த்துகள்! வினாடி வினா முடிந்தது.");
                    radioGroupOptions.setVisibility(View.GONE);
                    btnSubmitAnswer.setEnabled(false);
                    Toast.makeText(QuizActivity.this, "உங்கள் மொத்த மதிப்பெண்: " + score, Toast.LENGTH_LONG).show();
                }
            }
        });

        btnQuizBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadQuestion() {
        tvQuestion.setText(questions[currentQuestionIndex]);
        rbOption1.setText(options[currentQuestionIndex][0]);
        rbOption2.setText(options[currentQuestionIndex][1]);
        rbOption3.setText(options[currentQuestionIndex][2]);
        rbOption4.setText(options[currentQuestionIndex][3]);
    }
}
