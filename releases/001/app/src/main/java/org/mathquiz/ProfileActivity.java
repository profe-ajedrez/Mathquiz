package org.mathquiz;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.config.Config;
import org.database.FireStore_Adapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        FirebaseApp.initializeApp(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        //check the current user
        if (mAuth.getCurrentUser() != null) {

            Uri pico = mAuth.getCurrentUser().getPhotoUrl();
            TextView tvUser = findViewById(R.id.user_profile_name);
            tvUser.setText( mAuth.getCurrentUser().getDisplayName() );

            CircleImageView imUser = findViewById(R.id.user_profile_photo);
            Glide.with(this).load( mAuth.getCurrentUser().getPhotoUrl() ).into( imUser );

            Config cfg = FireStore_Adapter.loadConfig(FirebaseFirestore.getInstance(), mAuth);

            TextView sumas = findViewById(R.id.sumas_text);
            TextView restas = findViewById(R.id.restas_text);
            TextView product = findViewById(R.id.productos_text);
            TextView divis = findViewById(R.id.divois_text);


            sumas.setText(cfg.getPuntosSuma() + " sumas exitosas " + cfg.getFallosSuma() + " sumas fallidas");
            restas.setText(cfg.getPuntosResta() + " restas exitosas " + cfg.getFallosResta() + " restas fallidas");
            product.setText(cfg.getPuntosMulti() + " multiplicaciones exitosas " + cfg.getFallosMulti() + " multiplicacioness fallidas");
            divis.setText(cfg.getPuntosDiv() + " divisiones exitosas " + cfg.getFallosDiv() + " divisiones fallidas");
        }

        Button volver = findViewById(R.id.volver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent (getApplicationContext(), WebView_MathQuiz_Activity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
