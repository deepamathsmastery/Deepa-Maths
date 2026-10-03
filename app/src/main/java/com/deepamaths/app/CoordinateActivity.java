package com.deepamaths.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class CoordinateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Coordinate Geometry");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
