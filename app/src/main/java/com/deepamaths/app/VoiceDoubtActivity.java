package com.deepamaths.app;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceDoubtActivity extends AppCompatActivity {

    private TextView tvVoiceOutput;
    private Button btnSpeak, btnVoiceBack;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_doubt);

        tvVoiceOutput = findViewById(R.id.tvVoiceOutput);
        btnSpeak = findViewById(R.id.btnSpeak);
        btnVoiceBack = findViewById(R.id.btnVoiceBack);

        // மைக் பட்டனை கிளிக் செய்யும்போது
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakNow();
            }
        });

        // பின் செல்ல (Back)
        btnVoiceBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void speakNow() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        
        // தமிழ் மற்றும் ஆங்கிலத்தில் பேச (நீங்கள் விரும்பும் மொழியை மாற்றிக் கொள்ளலாம்)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ta-IN"); 
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "உங்கள் கணிதச் சந்தேகத்தைக் கூறவும்...");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "உங்கள் போனில் வாய்ஸ் சர்ப்போர்ட் இல்லை!", Toast.LENGTH_SHORT).show();
        }
    }

    // பேசியதை டெக்ஸ்டாக மாற்றுதல்
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null && !result.isEmpty()) {
                    String spokenText = result.get(0);
                    tvVoiceOutput.setText("கேள்வி: " + spokenText + "\n\n(AI உங்கள் சந்தேகத்திற்கு விரைவில் பதிலளிக்கும்!)");
                }
            }
        }
    }
}
