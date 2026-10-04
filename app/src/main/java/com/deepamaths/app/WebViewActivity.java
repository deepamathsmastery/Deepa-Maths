package com.deepamaths.app;

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
            webSettings.setJavaScriptEnabled(true); // ஜாவாஸ்கிரிப்ட் இயங்க
            webSettings.setDomStorageEnabled(true); // லோக்கல் ஸ்டோரேஜ் வேலை செய்ய
            webSettings.setLoadWithOverviewMode(true); 
            webSettings.setUseWideViewPort(true); // மொபைல் ஸ்கிரீனுக்கு ஏற்ப வெப்சைட் அட்ஜஸ்ட் ஆக
            webSettings.setSupportZoom(true); // ஜூம் செய்யும் வசதி
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false); // ஜூம் பட்டன்களை மறைக்க
            
            // கேச் மற்றும் நெட்வொர்க் செட்டிங்ஸ் (பாதுகாப்பிற்காக)
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            webSettings.setLoadsImagesAutomatically(true);

            webView.setWebViewClient(new WebViewClient());

            // MainActivity-ல் இருந்து அனுப்பப்பட்ட URL-ஐ வாங்குதல் (இல்லாவிட்டால் முகவரிக்குச் செல்லும்)
            String url = getIntent().getStringExtra("url");
            if (url == null || url.isEmpty()) {
                url = "https://deepamaths.com";
            }
            
            webView.loadUrl(url);
        }

        // நவீன ஆண்ட்ராய்டு வெர்ஷன்களுக்கான பேக் (Back) பட்டன் ஹேண்ட்லர்
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
}
