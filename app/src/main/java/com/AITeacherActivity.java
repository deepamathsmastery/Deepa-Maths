package com.deepamaths.app;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class AITeacherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_teacher);

        WebView webView = findViewById(R.id.webviewAiTeacher);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // உங்களது AI Teacher வெப்சைட் அல்லது டூல் லிங்கை இங்கே கொடுக்கவும்
        webView.loadUrl("https://deepamaths.com/ai-teacher"); 
    }
}
