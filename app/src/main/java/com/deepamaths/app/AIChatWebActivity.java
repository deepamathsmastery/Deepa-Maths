package com.deepamaths.app;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class AIChatWebActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // எளிதாக WebView-ஐ நேரடியாக உருவாக்கலாம் அல்லது layout பயன்படுத்தி கொள்ளலாம்
        webView = new WebView(this);
        setContentView(webView);

        // Web Settings அமைத்தல்
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        // உங்களது AI சாட் அல்லது வெப் தளத்தின் முகவரியை இங்கே கொடுக்கவும்
        // உதாரணமாக உங்கள் Deepa Maths வலைத்தளம் அல்லது AI சாட் URL:
        webView.loadUrl("https://your-deepamaths-ai-url.com"); // அல்லது உங்கள் தளத்தின் லிங்க்
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
