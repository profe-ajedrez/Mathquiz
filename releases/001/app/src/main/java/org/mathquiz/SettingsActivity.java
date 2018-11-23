package org.mathquiz;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.config.Config;
import org.database.FireStore_Adapter;


public class SettingsActivity extends AppCompatActivity {

    private Switch sonido;
    private Switch animacion;
    private RadioGroup operaciones;
    private Button grabar;
    private FirebaseAuth mAuth;
    private Config cfg;
    private SeekBar difi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        cfg = FireStore_Adapter.loadConfig(FirebaseFirestore.getInstance(), mAuth);
        bindUiControls();
        bindListener();
    }


    private void bindUiControls() {
        sonido = findViewById( R.id.sw_sonido );
        animacion = findViewById( R.id.sw_animaciones );
        operaciones = findViewById( R.id.rd_group );
        grabar = findViewById( R.id.btn_grabar );
        difi = findViewById(R.id.dificultad);

        sonido.setChecked( cfg.isSoundActive() );
        animacion.setChecked( cfg.isAnimationActive() );
        difi.setProgress( cfg.getDificultad() );

        TextView t = findViewById(R.id.txt_dificultad);
        t.setText( getDificultadToString(cfg.getDificultad()));

        RadioButton sm = findViewById( R.id.rd_suma );
        RadioButton rs = findViewById( R.id.rd_resta );
        RadioButton mp = findViewById( R.id.rd_multiplicacion );
        RadioButton dv = findViewById( R.id.rd_division );

        sm.setChecked( cfg.getOperacionActiva() == Config.OP_SUMA );
        rs.setChecked( cfg.getOperacionActiva() == Config.OP_RESTA );
        mp.setChecked( cfg.getOperacionActiva() == Config.OP_MULTIPLICACION );
        dv.setChecked( cfg.getOperacionActiva() == Config.OP_DIVISION );
    }


    private void bindListener() {

        difi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                TextView t = findViewById(R.id.txt_dificultad);
                t.setText( getDificultadToString(progress));
                //Toast.makeText(getApplicationContext(), "" + progress, Toast.LENGTH_LONG ).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   Config cfg = new Config();

                cfg.setSoundActive( sonido.isChecked() );
                cfg.setAnimationActive( animacion.isChecked() );

                switch( operaciones.getCheckedRadioButtonId() ) {

                    case R.id.rd_suma:
                        cfg.setOperacionActiva( Config.OP_SUMA );
                        break;
                    case R.id.rd_resta:
                        cfg.setOperacionActiva( Config.OP_RESTA );
                        break;
                    case R.id.rd_multiplicacion:
                        cfg.setOperacionActiva( Config.OP_MULTIPLICACION );
                        break;
                    case R.id.rd_division:
                        cfg.setOperacionActiva( Config.OP_DIVISION );
                        break;
                    default:
                        cfg.setOperacionActiva( Config.OP_SUMA );
                }

                cfg.setDificultad(difi.getProgress());
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                cfg.setUserToken(mAuth.getCurrentUser().getEmail());
                FireStore_Adapter.writeConfig(db, cfg);

                Intent i = new Intent(getApplicationContext(), WebView_MathQuiz_Activity.class);
                startActivity(i);
                finish();
            }
        });








    }

    private String getDificultadToString( int difi ) {
        return new String[]{ getResources().getString(R.string.facil),
                getResources().getString(R.string.normal),
                getResources().getString(R.string.dificil),
                getResources().getString(R.string.master),
                getResources().getString(R.string.nerd),
                getResources().getString(R.string.mazo) }[ difi ];
    }

}