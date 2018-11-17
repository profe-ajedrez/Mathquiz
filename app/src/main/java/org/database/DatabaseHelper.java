package org.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.config.Config;

/**
 *
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "settings_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Config.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insert(Config config) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Config.C_TOKEN, config.getUserToken());
        values.put(Config.C_SONIDO, config.isSoundActive());
        values.put(Config.C_ANIMACION, config.isAnimationActive());
        values.put(Config.C_OPERACION, config.getOperacionActiva());
        values.put(Config.C_P_SUMA, config.getPuntosSuma());
        values.put(Config.C_P_RESTA, config.getPuntosResta());
        values.put(Config.C_P_MULTI, config.getPuntosMulti());
        values.put(Config.C_P_DIVI, config.getPuntosDiv());
        values.put(Config.C_F_SUMA, config.getPuntosSuma());
        values.put(Config.C_F_RESTA, config.getPuntosResta());
        values.put(Config.C_F_MULTI, config.getPuntosMulti());
        values.put(Config.C_F_DIVI, config.getPuntosDiv());
        values.put(Config.C_DIFICULTAD, config.getDificultad());
        // insert row
        long id = db.insert(Config.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public Config getConfig(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Config.TABLE_NAME,
                new String[]{ Config.C_ID,
                              Config.C_TOKEN,
                              Config.C_SONIDO,
                              Config.C_ANIMACION,
                              Config.C_OPERACION,
                              Config.C_P_SUMA,
                              Config.C_P_RESTA,
                              Config.C_P_MULTI,
                              Config.C_P_DIVI,
                              Config.C_F_SUMA,
                              Config.C_F_RESTA,
                              Config.C_F_MULTI,
                              Config.C_F_DIVI,
                              Config.C_DIFICULTAD
                            },
                Config.C_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Config config = new Config(
                cursor.getInt(cursor.getColumnIndex(Config.C_ID)),
                cursor.getString(cursor.getColumnIndex(Config.C_TOKEN)),
                cursor.getInt(cursor.getColumnIndex(Config.C_SONIDO)) == 1,
                cursor.getInt(cursor.getColumnIndex(Config.C_ANIMACION)) == 1,
                cursor.getInt(cursor.getColumnIndex(Config.C_OPERACION)),
                cursor.getInt(cursor.getColumnIndex(Config.C_P_SUMA)),
                cursor.getInt(cursor.getColumnIndex(Config.C_P_RESTA)),
                cursor.getInt(cursor.getColumnIndex(Config.C_P_MULTI)),
                cursor.getInt(cursor.getColumnIndex(Config.C_P_DIVI)),
                cursor.getInt(cursor.getColumnIndex(Config.C_F_SUMA)),
                cursor.getInt(cursor.getColumnIndex(Config.C_F_RESTA)),
                cursor.getInt(cursor.getColumnIndex(Config.C_F_MULTI)),
                cursor.getInt(cursor.getColumnIndex(Config.C_F_DIVI)),
                cursor.getInt(cursor.getColumnIndex(Config.C_DIFICULTAD)));

        // close the db connection
        cursor.close();

        return config;
    }

    public Config getConfig(String token) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Config.TABLE_NAME,
                new String[]{ Config.C_ID,
                        Config.C_TOKEN,
                        Config.C_SONIDO,
                        Config.C_ANIMACION,
                        Config.C_OPERACION,
                        Config.C_P_SUMA,
                        Config.C_P_RESTA,
                        Config.C_P_MULTI,
                        Config.C_P_DIVI,
                        Config.C_F_SUMA,
                        Config.C_F_RESTA,
                        Config.C_F_MULTI,
                        Config.C_F_DIVI,
                        Config.C_DIFICULTAD
                            },
                Config.C_TOKEN + "=?",
                new String[]{ token.trim() }, null, null, null, null);

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        try {
            // prepare note object
            Config config = new Config(
                    cursor.getInt(cursor.getColumnIndex(Config.C_ID)),
                    cursor.getString(cursor.getColumnIndex(Config.C_TOKEN)),
                    cursor.getInt(cursor.getColumnIndex(Config.C_SONIDO)) == 1,
                    cursor.getInt(cursor.getColumnIndex(Config.C_ANIMACION)) == 1,
                    cursor.getInt(cursor.getColumnIndex(Config.C_OPERACION)),
                    cursor.getInt(cursor.getColumnIndex(Config.C_P_SUMA)),
                    cursor.getInt(cursor.getColumnIndex(Config.C_P_RESTA)),
                    cursor.getInt(cursor.getColumnIndex(Config.C_P_MULTI)),
                    cursor.getInt(cursor.getColumnIndex(Config.C_P_DIVI)),
                    cursor.getInt(cursor.getColumnIndex(Config.C_F_SUMA)),
                    cursor.getInt(cursor.getColumnIndex(Config.C_F_RESTA)),
                    cursor.getInt(cursor.getColumnIndex(Config.C_F_MULTI)),
                    cursor.getInt(cursor.getColumnIndex(Config.C_F_DIVI)),
                    cursor.getInt(cursor.getColumnIndex(Config.C_DIFICULTAD)));

            // close the db connection
            cursor.close();

            return config;
        } catch ( CursorIndexOutOfBoundsException e ) {
            return null;
        }
    }

    public int update(Config config) {
        SQLiteDatabase db = super.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Config.C_TOKEN, config.getUserToken());
        values.put(Config.C_SONIDO, config.isSoundActive());
        values.put(Config.C_ANIMACION, config.isAnimationActive());
        values.put(Config.C_OPERACION, config.getOperacionActiva());
        values.put(Config.C_P_SUMA, config.getPuntosSuma());
        values.put(Config.C_P_RESTA, config.getPuntosResta());
        values.put(Config.C_P_MULTI, config.getPuntosMulti());
        values.put(Config.C_P_DIVI, config.getPuntosDiv());
        values.put(Config.C_F_SUMA, config.getPuntosSuma());
        values.put(Config.C_F_RESTA, config.getPuntosResta());
        values.put(Config.C_F_MULTI, config.getPuntosMulti());
        values.put(Config.C_F_DIVI, config.getPuntosDiv());
        values.put(Config.C_DIFICULTAD, config.getDificultad());

        if (config.getUserToken() == null || config.getUserToken().isEmpty()) {
            // updating row
            return db.update(Config.TABLE_NAME, values, Config.C_ID + " = ?",
                    new String[]{String.valueOf(config.getId())});
        } else {
            return db.update(Config.TABLE_NAME, values, Config.C_TOKEN + " = ?",
                    new String[]{ config.getUserToken()});
        }

    }

}
