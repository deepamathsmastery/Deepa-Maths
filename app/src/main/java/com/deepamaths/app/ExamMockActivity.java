package com.deepamaths.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ExamMockActivity extends AppCompatActivity {

    private TextView tvTimer, tvExamQuestion;
    private RadioGroup rgExamOptions;
    private RadioButton rbExamOpt1, rbExamOpt2, rbExamOpt3, rbExamOpt4;
    private Button btnExamNext, btnExamBack;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 120000; // 2 நிமிடங்கள் (120 விநாடிகள்)

    private String[] questions = {
            "1. தீர்க்க: 2x - 4 = 10 எனில் x இன் மதிப்பு என்ன?",
            "2. ஒரு வட்டத்தின் ஆரம் 7 செ.மீ எனில் அதன் சுற்றளவு என்ன? (π = 22/7)",
            "3. 3, 6, 9, 12 என்ற தொடரின் அடுத்த எண் என்ன?"
    };

    private String[][] options = {
            {"5", "7", "9", "6"},
            {"22 செ.மீ", "44 செ.மீ", "14 செ.மீ", "88 செ.மீ"},
            {"14", "16", "15", "18"}
    };

    private int[] correctAnswers = {1, 1, 2}; // சரியான பதில்களின் இன்டெக்ஸ்
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean isExamFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_mock);

        tvTimer = findViewById(R.id.tvTimer);
        tvExamQuestion = findViewById(R.id.tvExamQuestion);
        rgExamOptions = findViewById(R.id.rgExamOptions);
        rbExamOpt1 = findViewById(R.id.rbExamOpt1);
        rbExamOpt2 = findViewById(R.id.rbExamOpt2);
        rbExamOpt3 = findViewById(R.id.rbExamOpt3);
        rbExamOpt4 = findViewById(R.id.rbExamOpt4);
        btnExamNext = findViewById(R.id.btnExamNext);
        btnExamBack = findViewById(R.id.btnExamBack);

        startTimer();
        loadQuestion();

        btnExamNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExamFinished) {
                    finish();
                    return;
                }

                int selectedId = rgExamOptions.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(ExamMockActivity.this, "தயவுசெய்து ஒரு விடையைத் தேர்ந்தெடுக்கவும்!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRb = findViewById(selectedId);
                int ansIndex = rgExamOptions.indexOfChild(selectedRb);

                if (ansIndex == correctAnswers[currentQuestionIndex]) {
                    score += 10;
                }

                rgExamOptions.clearCheck();
                currentQuestionIndex++;

                if (currentQuestionIndex < questions.length) {
                    loadQuestion();
                } else {
                    finishExam();
                }
            }
        });

        btnExamBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                finish();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int minutes = (int) (timeLeftInMillis / 1000) / 60;
                int seconds = (int) (timeLeftInMillis / 1000) % 60;
                tvTimer.setText(String.format("மீதமுள்ள நேரம்: %02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                tvTimer.setText("நேரம் முடிந்தது! (Time's up!)");
                finishExam();
            }
        }.start();
    }

    private void loadQuestion() {
        tvExamQuestion.setText(questions[currentQuestionIndex]);
        rbExamOpt1.setText(options[currentQuestionIndex][0]);
        rbExamOpt2.setText(options[currentQuestionIndex][1]);
        rbExamOpt3.setText(options[currentQuestionIndex][2]);
        rbExamOpt4.setText(options[currentQuestionIndex][3]);
    }

    private void finishExam() {
        isExamFinished = true;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        tvExamQuestion.setText("📝 தேர்வு முடிவு (Exam Completed)!\n\nஉங்கள் மொத்த மதிப்பெண்: " + score + " / 30");
        rgExamOptions.setVisibility(View.GONE);
        btnExamNext.setText("முகப்புக்குச் செல் (Finish & Exit)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
