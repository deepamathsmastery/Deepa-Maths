package com.deepamaths.app;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MathPadActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_pad);

        webView = findViewById(R.id.webviewMathPad);
        WebSettings webSettings = webView.getSettings();
        
        // அடிப்படை மற்றும் நவீன வெப் அமைப்புகள்
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // HTTP/HTTPS பாதுகாப்பு அனுமதி

        // WebViewClient அமைத்தல் (வெளி பிரவுசருக்குச் செல்லாமல் ஆப்பிற்குள்ளேயே லிங்க் திறக்கப்பட)
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // உங்களது Math Pad வெப்சைட் லிங்க்
        webView.loadUrl("https://deepamaths.com/math-pad"); 
    }

    // பேக் பட்டனை அழுத்தும்போது ஆப் மூடப்படாமல் முந்தைய வெப் பக்கத்திற்குச் செல்ல
    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
