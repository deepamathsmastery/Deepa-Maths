package com.deepamaths.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class TrigonometryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigonometry);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Trigonometry Formulas");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
