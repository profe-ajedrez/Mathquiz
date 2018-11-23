package org.mathquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.bindings.AudioInterface;
import org.bindings.SettingsInterface;
import org.config.Config;
import org.database.FireStore_Adapter;

public class WebView_MathQuiz_Activity extends AppCompatActivity {

    private static final String BASE_URL = "file:///android_asset/mathquizz/";
    private static final int MAX_SELF_INSTANCES = 1;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private Config cfg;
    private WebView wView;
    private static int selfInstance = 0;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIfLoged();


        cfg = FireStore_Adapter.loadConfig(FirebaseFirestore.getInstance(), mAuth);
        // cfg = FireStore_Adapter.loadConfig(FirebaseFirestore.getInstance(), mAuth );
        gettingData();

        prepareWebView();
        bindJavascriptInterfaces();
        activarPermisosWebView();
        overrideUrls();

        //Cargar html5 App
        wView.loadUrl(BASE_URL + "index.html");

    }

    private void gettingData() {

        cfg = FireStore_Adapter.retrieve(FirebaseFirestore.getInstance(), mAuth);
    }


    private void checkIfLoged() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(WebView_MathQuiz_Activity.this, SigInActivity.class));
                    finish();
                }
            }
        };

    }


    private void prepareWebView() {
        this.wView = findViewById(R.id.activity_main_webview);
        this.wView.getSettings().setJavaScriptEnabled(true);
        this.wView.getSettings().setAppCacheEnabled(true);
        this.wView.setWebChromeClient(new WebChromeClient());
        this.wView.getSettings().setDomStorageEnabled(true);     //DomStorageEnabled = true;
    }


    private void bindJavascriptInterfaces() {
        wView.addJavascriptInterface(new AudioInterface(this), "AndAud");
        wView.addJavascriptInterface(new SettingsInterface( cfg, getApplicationContext() ), "SettingsManager");
    }


    private void activarPermisosWebView() {
        //Activar acceso a archivos locales
      //  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            this.wView.getSettings().setAllowUniversalAccessFromFileURLs(true);
            this.wView.getSettings().setAllowFileAccessFromFileURLs(true);
       // }

        //Activar depuración remota
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }


    private void overrideUrls() {
        this.wView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading( WebView v, String url )
            {

                // Si en WebView se hizo click en enlace settings, iniciar actividad Settings
                if ( url.equalsIgnoreCase(BASE_URL + "settings") )  {
                    Intent intent = new Intent( getApplicationContext(), SettingsActivity.class );
                    startActivity(intent);
                    wView.loadUrl( BASE_URL + "index.html");
                    return true;
                }

                if ( url.equalsIgnoreCase(BASE_URL + "logout") )  {
                    logout();
                    wView.loadUrl( BASE_URL + "index.html");
                    return true;
                }

                if ( url.equalsIgnoreCase(BASE_URL + "profile") ) {
                    Intent intent = new Intent( getApplicationContext(), ProfileActivity.class );
                    startActivity(intent);
                    wView.loadUrl( BASE_URL + "index.html");
                    return true;
                }

                Log.e("URL", "URL: " + url);
                return false;
            }

        });
    }


    private void logout() {
       // mAuth.signOut();
        AuthUI.getInstance().signOut(getApplicationContext());
    }
}
