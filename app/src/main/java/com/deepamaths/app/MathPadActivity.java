package com.deepamaths.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MathPadActivity extends AppCompatActivity {

    private EditText etProblem, etStep1, etStep2, etFinalAnswer;
    private Button btnCheckAnswer, btnClearPad, btnMathPadBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_pad);

        etProblem = findViewById(R.id.etProblem);
        etStep1 = findViewById(R.id.etStep1);
        etStep2 = findViewById(R.id.etStep2);
        etFinalAnswer = findViewById(R.id.etFinalAnswer);
        btnCheckAnswer = findViewById(R.id.btnCheckAnswer);
        btnClearPad = findViewById(R.id.btnClearPad);
        btnMathPadBack = findViewById(R.id.btnMathPadBack);

        // பதிலைச் சரிபார்க்கும் பட்டன்
        btnCheckAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String problem = etProblem.getText().toString().trim();
                String step1 = etStep1.getText().toString().trim();
                String answer = etFinalAnswer.getText().toString().trim();

                if (problem.isEmpty() || answer.isEmpty()) {
                    Toast.makeText(MathPadActivity.this, "தயவுசெய்து கணக்கையும் விடையையும் நிரப்பவும்!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MathPadActivity.this, "அருமை! உங்களது கணக்கு முறை சேமிக்கப்பட்டது.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // ஃபார்மை அழிக்க (Clear)
        btnClearPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etProblem.setText("");
                etStep1.setText("");
                etStep2.setText("");
                etFinalAnswer.setText("");
                Toast.makeText(MathPadActivity.this, "Math Pad அழிக்கப்பட்டது!", Toast.LENGTH_SHORT).show();
            }
        });

        // பின் செல்ல (Back)
        btnMathPadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
