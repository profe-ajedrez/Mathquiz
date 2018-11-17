/*
package org.mathquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import org.config.Config;
import org.database.DatabaseHelper;

public class WebView_MathQuiz_Activity_backup extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private Config cfg;
    private DatabaseHelper db;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button =  (Button) findViewById(R.id.signout);
        mAuth = FirebaseAuth.getInstance();

        db = new DatabaseHelper(this);
        cfg = db.getConfig( mAuth.getCurrentUser().getEmail() );

        if (cfg == null) {
            cfg = new Config();
            cfg.setUserToken( mAuth.getCurrentUser().getEmail() );
            db.insert( cfg );
        }

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(WebView_MathQuiz_Activity_backup.this, SigInActivity.class));
                }
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                AuthUI.getInstance().signOut(getApplicationContext());


            }
        });

        TextView sonido = findViewById(R.id.sonido);
        TextView anim   = findViewById(R.id.anim);
        TextView ope    = findViewById(R.id.op);

        Button sett = findViewById(R.id.setting);

        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });

        if (cfg.isSoundActive()) {
            sonido.setText("sonido encendido");
        } else {
            sonido.setText("sonido apagado");
        }

        if (cfg.isAnimationActive()) {
            anim.setText("anim encendido");
        } else {
            anim.setText("anim apagado");
        }

        ope.setText( cfg.operacionActivaToString() );

    }
}
*/