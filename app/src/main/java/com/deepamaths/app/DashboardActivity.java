package com.example.deepamaths; // உங்கள் ஆப் பேக்கேஜ் பெயரை இங்கே மாற்றிக் கொள்ளவும்

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        webView = findViewById(R.id.webViewDashboard);
        WebSettings webSettings = webView.getSettings();
        
        // அவசியமான வெப் செட்டிங்ஸ்
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // லாகின் செஷன் (Session & Cookies) மாறாமல் இருக்க
        CookieManager.getInstance().setAcceptCookie(true);

        webView.setWebViewClient(new WebViewClient());
        
        // உங்களுடைய வெப்சைட் லிங்க்
        webView.loadUrl("https://deepamaths.com/student-dashboard/");
    }

    // போனில் Back பட்டன் அழுத்தும்போது முந்தைய வெப் பேஜூக்கு செல்ல
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
