package com.deepamaths.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AITeacherActivity extends AppCompatActivity {

    private EditText etMathQuery;
    private Button btnSolve, btnBack;
    private TextView tvAiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_teacher);

        etMathQuery = findViewById(R.id.etMathQuery);
        btnSolve = findViewById(R.id.btnSolve);
        btnBack = findViewById(R.id.btnBack);
        tvAiResponse = findViewById(R.id.tvAiResponse);

        // AI தீர்வு காணும் பகுதி
        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etMathQuery.getText().toString().trim();

                if (query.isEmpty()) {
                    Toast.makeText(AITeacherActivity.this, "தயவுசெய்து கேள்வியை உள்ளிடவும்!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // AI மாதிரி பதில் (Mock Response)
                String responseText = "AI ஆய்வு செய்கிறது...\n\n" +
                        "கேள்வி: " + query + "\n\n" +
                        "படி 1: கொடுக்கப்பட்ட சமன்பாட்டைச் சரிபார்க்கவும்.\n" +
                        "படி 2: விடையைக் கண்டறிய கணக்கீடுகளைச் செய்யவும்.\n\n" +
                        "(குறிப்பு: முழுமையான AI API அல்லது Backend சேவையை இணைக்கும்போது உண்மையான பதில்கள் கிடைக்கும்.)";

                tvAiResponse.setText(responseText);
                Toast.makeText(AITeacherActivity.this, "தீர்வு பெறப்பட்டது!", Toast.LENGTH_SHORT).show();
            }
        });

        // Back பட்டனுக்கான செயல்பாடு
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // இந்தப் பக்கத்தை மூடிவிட்டு முந்தைய பக்கத்திற்குச் செல்லும்
            }
        });
    }
}
