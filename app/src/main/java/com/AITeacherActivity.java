package com.deepamaths.app;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class AITeacherActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_teacher);

        webView = findViewById(R.id.webviewAiTeacher);
        WebSettings webSettings = webView.getSettings();
        
        // அடிப்படை அமைப்புகள் (Basic Settings)
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // HTTP மற்றும் HTTPS கலந்திருக்கும் போது உதவும்

        // WebViewClient அமைத்தல் (ஆப்பிற்குள்ளேயே லிங்க்குகள் திறக்கப்பட)
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // உங்களது AI Teacher வெப்சைட் அல்லது டூல் லிங்க்
        webView.loadUrl("https://deepamaths.com/ai-teacher"); 
    }

    // பேக் பட்டனை (Back Button) அழுத்தும்போது முந்தைய பக்கத்திற்குச் செல்ல
    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
