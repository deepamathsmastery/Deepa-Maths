package com.deepamaths.app;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MathPadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_pad);

        WebView webView = findViewById(R.id.webviewMathPad);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        
        // உங்களது Math Pad வெப்சைட் அல்லது ஆன்லைன் டூல் லிங்கை இங்கே கொடுக்கவும்
        webView.loadUrl("https://www.google.com"); 
    }
}
