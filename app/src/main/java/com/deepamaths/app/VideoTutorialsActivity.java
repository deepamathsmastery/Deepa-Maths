package com.deepamaths.app;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class VideoTutorialsActivity extends AppCompatActivity {

    private WebView webViewVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_tutorials);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Video Tutorials");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        webViewVideos = findViewById(R.id.webViewVideos);
        WebSettings webSettings = webViewVideos.getSettings();
        webSettings.setJavaScriptEnabled(true); // YouTube வீடியோக்கள் இயங்க ஜாவாஸ்கிரிப்ட் அவசியம்
        webSettings.setDomStorageEnabled(true);

        webViewVideos.setWebViewClient(new WebViewClient());

        // உங்கள் யூடியூப் சேனல் அல்லது மேத்ஸ் கிளாஸ் பிளேலிஸ்ட் லிங்கை இங்கே மாற்றிக் கொள்ளவும்
        webViewVideos.loadUrl("https://www.youtube.com/@YourYouTubeChannelName"); 
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // போனில் பேக் பட்டன் அழுத்தினால் பிரவுசரில் பின்னால் செல்வதற்கு
    @Override
    public void onBackPressed() {
        if (webViewVideos.canGoBack()) {
            webViewVideos.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
