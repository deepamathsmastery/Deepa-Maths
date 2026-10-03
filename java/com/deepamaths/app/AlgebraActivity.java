package com.deepamaths.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AlgebraActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algebra);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Algebra & Complex Numbers");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
