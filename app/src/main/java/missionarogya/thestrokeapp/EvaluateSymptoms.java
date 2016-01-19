package missionarogya.thestrokeapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class EvaluateSymptoms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_symptoms);
        String url = "file:///android_asset/ACTFAST/index.html";
        WebView webView = (WebView) findViewById(R.id.webviewInterviewQuestionnaire);
        WebSettings settings = webView.getSettings();
        webView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        webView.addJavascriptInterface(new JSBridge(EvaluateSymptoms.this), "JSBridge");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluate_symptoms, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(EvaluateSymptoms.this, "You cannot go back from here.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class JSBridge{
    private Activity currentActivity;

    JSBridge(Activity currentActivity){
        this.currentActivity = currentActivity;
    }

    @android.webkit.JavascriptInterface
    public void showHospitalInformation() {
        Intent intent = new Intent(currentActivity, Diagnosis.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    @android.webkit.JavascriptInterface
    public void showAlert(String message){
        Toast.makeText(currentActivity, message, Toast.LENGTH_SHORT).show();
    }
}