package com.deepamaths.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MathCalculatorActivity extends AppCompatActivity {

    private EditText etNum1, etNum2;
    private Button btnLcmGcd, btnSquareRoot, btnCircleArea, btnCalcBack;
    private TextView tvCalcResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_calculator);

        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        btnLcmGcd = findViewById(R.id.btnLcmGcd);
        btnSquareRoot = findViewById(R.id.btnSquareRoot);
        btnCircleArea = findViewById(R.id.btnCircleArea);
        btnCalcBack = findViewById(R.id.btnCalcBack);
        tvCalcResult = findViewById(R.id.tvCalcResult);

        // 1. LCM & GCD கணக்கிடுதல்
        btnLcmGcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n1Str = etNum1.getText().toString().trim();
                String n2Str = etNum2.getText().toString().trim();

                if (n1Str.isEmpty() || n2Str.isEmpty()) {
                    Toast.makeText(MathCalculatorActivity.this, "தயவுசெய்து இரண்டு எண்களையும் உள்ளிடவும்!", Toast.LENGTH_SHORT).show();
                    return;
                }

                long a = Long.parseLong(n1Str);
                long b = Long.parseLong(n2Str);

                long gcd = findGcd(a, b);
                long lcm = (a * b) / gcd;

                tvCalcResult.setText("மீ.பொ.வ (GCD): " + gcd + "\nமீ.பொ.ம (LCM): " + lcm);
            }
        });

        // 2. Square Root கணக்கிடுதல்
        btnSquareRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(V v) {
                String n1Str = etNum1.getText().toString().trim();

                if (n1Str.isEmpty()) {
                    Toast.makeText(MathCalculatorActivity.this, "தயவுசெய்து எண் 1-ஐ உள்ளிடவும்!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double num = Double.parseDouble(n1Str);
                double sqrt = Math.sqrt(num);

                tvCalcResult.setText("வர்க்கமூலம் (Square Root of " + num + ") = " + sqrt);
            }
        });

        // 3. Circle Area கணக்கிடுதல் (πr²)
        btnCircleArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n1Str = etNum1.getText().toString().trim();

                if (n1Str.isEmpty()) {
                    Toast.makeText(MathCalculatorActivity.this, "தயவுசெய்து ஆரம் (Radius) மதிப்பை உள்ளிடவும்!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double radius = Double.parseDouble(n1Str);
                double area = Math.PI * radius * radius;

                tvCalcResult.setText("வட்டத்தின் பரப்பளவு (Area) = " + String.format("%.2f", area) + " sq.units");
            }
        });

        // பின் செல்ல (Back)
        btnCalcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // GCD கண்டறியும் முறை (Euclidean algorithm)
    private long findGcd(long a, long b) {
        if (b == 0) return a;
        return findGcd(b, a % b);
    }
}
