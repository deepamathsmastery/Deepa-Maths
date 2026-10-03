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

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etUsername, etEmail, etPassword, etRePassword, 
                     etMobileNo, etWhatsappNo, etArea, etBoard, etClassGrade;
    private Button btnRegisterSubmit;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);

        // UI கூறுகளை இணைத்தல்
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etRegUsername);
        etEmail = findViewById(R.id.etRegEmail);
        etPassword = findViewById(R.id.etRegPassword);
        etRePassword = findViewById(R.id.etRePassword);
        etMobileNo = findViewById(R.id.etMobileNo);
        etWhatsappNo = findViewById(R.id.etWhatsappNo);
        etArea = findViewById(R.id.etArea);
        etBoard = findViewById(R.id.etBoard);
        etClassGrade = findViewById(R.id.etClassGrade);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);

        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String rePassword = etRePassword.getText().toString().trim();
                String mobile = etMobileNo.getText().toString().trim();
                String whatsapp = etWhatsappNo.getText().toString().trim();
                String area = etArea.getText().toString().trim();
                String board = etBoard.getText().toString().trim();
                String classGrade = etClassGrade.getText().toString().trim();

                // காலி புலங்கள் உள்ளதா என சோதித்தல்
                if (firstName.isEmpty() || username.isEmpty() || password.isEmpty() || mobile.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "தயவுசெய்து அவசியமான விவரங்களை நிரப்பவும்", Toast.LENGTH_SHORT).show();
                    return;
                }

                // பாஸ்வேர்ட் சரியாகப் பொருந்துகிறதா என சோதித்தல்
                if (!password.equals(rePassword)) {
                    Toast.makeText(RegisterActivity.this, "பாஸ்வேர்ட் பொருந்தவில்லை!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // SharedPreferences-ல் விவரங்களைச் சேமித்தல் (Register)
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("savedUsername", username);
                editor.putString("savedPassword", password);
                editor.putString("studentName", firstName + " " + lastName);
                editor.apply();

                Toast.makeText(RegisterActivity.this, "பதிவு வெற்றிகரமாக முடிந்தது! இப்போது லாகின் செய்யவும்.", Toast.LENGTH_LONG).show();

                // லாகின் பக்கத்திற்குத் திரும்புதல்
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
