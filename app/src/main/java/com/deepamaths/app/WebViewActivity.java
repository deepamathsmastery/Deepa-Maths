package com.deepamaths.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    
    // ஃபைல் அப்லோட்டிற்கான மாறிகள் (Variables for File Upload)
    private ValueCallback<Uri[]> uploadMessage;
    private final static int FILE_CHOOSER_RESULT_CODE = 1;

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
            webSettings.setAllowFileAccess(true); // ஃபைல் அணுகலை அனுமதிக்க
            
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

            // "Choose Files" வேலை செய்ய WebChromeClient அவசியம்
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                    if (uploadMessage != null) {
                        uploadMessage.onReceiveValue(null);
                        uploadMessage = null;
                    }
                    uploadMessage = filePathCallback;

                    Intent intent = fileChooserParams.createIntent();
                    try {
                        startActivityForResult(intent, FILE_CHOOSER_RESULT_CODE);
                    } catch (Exception e) {
                        uploadMessage = null;
                        return false;
                    }
                    return true;
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

    // போனில் இருந்து ஃபைலைத் தேர்ந்தெடுத்த பிறகு அதை WebView-க்கு அனுப்பும் முறை
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (uploadMessage == null) return;
            Uri[] results = null;
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    String dataString = intent.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    } else if (intent.getClipData() != null) {
                        int count = intent.getClipData().getItemCount();
                        results = new Uri[count];
                        for (int i = 0; i < count; i++) {
                            results[i] = intent.getClipData().getItemAt(i).getUri();
                        }
                    }
                }
            }
            uploadMessage.onReceiveValue(results);
            uploadMessage = null;
        }
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
