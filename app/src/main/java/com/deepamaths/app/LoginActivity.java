package com.deepamaths.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etStudentName, etPassword;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);
        
        // ஏற்கனவே லாகின் செய்திருந்தால் நேராக MainActivity-க்குச் செல்ல
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        etStudentName = findViewById(R.id.etStudentName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUser = etStudentName.getText().toString().trim();
                String inputPass = etPassword.getText().toString().trim();

                // சேமிக்கப்பட்ட யூசர்நேம் மற்றும் பாஸ்வேர்டை எடுத்தல்
                String registeredUser = sharedPreferences.getString("savedUsername", "");
                String registeredPass = sharedPreferences.getString("savedPassword", "");

                if (inputUser.isEmpty() || inputPass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "யூசர்நேம் மற்றும் பாஸ்வேர்ட் உள்ளிடவும்", Toast.LENGTH_SHORT).show();
                } else if (inputUser.equals(registeredUser) && inputPass.equals(registeredPass)) {
                    // லாகின் வெற்றி
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "உள்நுழைவு வெற்றி!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "தவறான யூசர்நேம் அல்லது பாஸ்வேர்ட்!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
