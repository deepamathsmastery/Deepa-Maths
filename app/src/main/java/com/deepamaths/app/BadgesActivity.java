package com.deepamaths.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BadgesActivity extends AppCompatActivity {

    private TextView tvBadge1Status, tvBadge2Status, tvBadge3Status;
    private Button btnBadgeBack;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);

        tvBadge1Status = findViewById(R.id.tvBadge1Status);
        tvBadge2Status = findViewById(R.id.tvBadge2Status);
        tvBadge3Status = findViewById(R.id.tvBadge3Status);
        btnBadgeBack = findViewById(R.id.btnBadgeBack);

        sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);

        // மாணவரின் மதிப்பெண்களைச் சோதித்து பதக்கங்களைத் திறத்தல் (Unlock)
        int quizScore = sharedPreferences.getInt("quizScore", 0);

        // Badge 1: 0-க்கு மேல் இருந்தால் திறக்கப்படும்
        if (quizScore > 0) {
            tvBadge1Status.setText("நிலை: திறக்கப்பட்டது! (Unlocked 🏆)");
            tvBadge1Status.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }

        // Badge 2: 10 அல்லது அதற்கு மேல்
        if (quizScore >= 10) {
            tvBadge2Status.setText("நிலை: திறக்கப்பட்டது! (Unlocked 🏆)");
            tvBadge2Status.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }

        // Badge 3: 30 அல்லது அதற்கு மேல்
        if (quizScore >= 30) {
            tvBadge3Status.setText("நிலை: திறக்கப்பட்டது! (Unlocked 🏆)");
            tvBadge3Status.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }

        // பின் செல்ல (Back)
        btnBadgeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
