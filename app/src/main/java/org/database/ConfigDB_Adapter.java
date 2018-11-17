package org.database;

import com.google.firebase.auth.FirebaseAuth;
import org.config.Config;

/**
 *
 */
public class ConfigDB_Adapter {

    public static Config loadConfig(DatabaseHelper db, FirebaseAuth mAuth ) {

        Config cfg = db.getConfig( mAuth.getCurrentUser().getEmail() );

        if (cfg == null) {
            cfg = new Config();
            cfg.setUserToken( mAuth.getCurrentUser().getEmail() );
            db.insert( cfg );
        }

        return cfg;
    }

    public static void writeConfig( DatabaseHelper db, Config config ) {

        db.update( config );
    }
}
