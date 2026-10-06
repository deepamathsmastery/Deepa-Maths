package com.deepamaths.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webView);
        
        if (webView != null) {
            // மொபைல் வியூவிற்கான முக்கியமான செட்டிங்ஸ்
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true); 
            webSettings.setDomStorageEnabled(true); 
            webSettings.setLoadWithOverviewMode(true); 
            webSettings.setUseWideViewPort(true); 
            webSettings.setSupportZoom(true); 
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false); 
            
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            webSettings.setLoadsImagesAutomatically(true);

            // Login & Logout Status-ஐ கண்காணிக்க
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    checkLoginStatusFromUrl(url);
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    checkLoginStatusFromUrl(url);
                }
            });

            // MainActivity-ல் இருந்து அனுப்பப்பட்ட URL-ஐ வாங்குதல்
            String url = getIntent().getStringExtra("url");
            if (url == null || url.isEmpty()) {
                url = "https://deepamaths.com";
            }
            
            webView.loadUrl(url);
        }

        // பேக் பட்டன் ஹேண்ட்லர்
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView != null && webView.canGoBack()) {
                    webView.goBack();
                } else {
                    setEnabled(false);
                    onBackPressed();
                }
            }
        });
    }

    // வெப்சைட்டின் URL-ஐ வைத்து லாகின்/லாக் அவுட்டை ஆப் புரிந்து கொள்ளும் முறை
    private void checkLoginStatusFromUrl(String url) {
        if (url == null) return;

        SharedPreferences sharedPreferences = getSharedPreferences("MathsAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 1. டேஷ்போர்டுக்குச் சென்றால் லாகின் ஆனதாகக் கொள்ளும்
        if (url.contains("student-dashboard") && !url.contains("login") && !url.contains("logout")) {
            editor.putBoolean("isLoggedIn", true);
            editor.apply();
        } 
        // 2. வேர்ட்பிரஸ் லாக் அவுட் ஆனதை அடையாளம் காணுதல்
        else if (url.contains("action=logout") || url.contains("loggedout=true") || url.contains("wp-login.php?action=logout")) {
            editor.putBoolean("isLoggedIn", false);
            editor.remove("studentName");
            editor.apply();
        }
    }
}
