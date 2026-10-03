package com.deepamaths.app;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webView);
        
        // மொபைல் வியூவிற்கான முக்கியமான செட்டிங்ஸ்
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // ஜாவாஸ்கிரிப்ட் இயங்க
        webSettings.setDomStorageEnabled(true); // லோக்கல் ஸ்டோரேஜ் வேலை செய்ய
        webSettings.setLoadWithOverviewMode(true); 
        webSettings.setUseWideViewPort(true); // மொபைல் ஸ்கிரீனுக்கு ஏற்ப வெப்சைட் அட்ஜஸ்ட் ஆக
        webSettings.setSupportZoom(true); // ஜூம் செய்யும் வசதி
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false); // ஜூம் பட்டன்களை மறைக்க (நேரடியாக ஸ்கிரீனில் ஜூம் செய்யலாம்)

        webView.setWebViewClient(new WebViewClient());
        
        // உங்கள் மாணவர் டாஷ்போர்டு லிங்க்
        webView.loadUrl("https://deepamaths.com/student-dashboard");
    }

    // மொபைலில் பேக் (Back) பட்டனை அழுத்தும்போது வெப்சைட்டில் உள்ள முந்தைய பக்கத்திற்குச் செல்ல
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
