package com.deepamaths.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class CalculusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Calculus Formulas");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
