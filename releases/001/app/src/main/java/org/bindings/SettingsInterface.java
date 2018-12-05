package org.bindings;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.google.firebase.firestore.FirebaseFirestore;

import org.config.Config;
import org.database.ConfigDB_Adapter;
import org.database.DatabaseHelper;
import org.database.FireStore_Adapter;

public class SettingsInterface {

    private static Config settings;
    private static Context context;

    public SettingsInterface(Config settings, Context c) {
        SettingsInterface.settings = settings;
        context = c;
    }


    @JavascriptInterface
    public int getOperacionActiva() {
        return settings.getOperacionActiva();
    }


    @JavascriptInterface
    public String operacionActivaToString() {
        return settings.operacionActivaToString();
    }

    @JavascriptInterface
    public boolean isSoundActive() {
        return settings.isSoundActive();
    }

    @JavascriptInterface
    public boolean isAnimationActive() {
        return settings.isAnimationActive();
    }

    @JavascriptInterface
    public int getPuntosSuma() {
        return settings.getPuntosSuma();
    }

    @JavascriptInterface
    public void setPuntosSuma(int puntosSuma) {
        settings.setPuntosSuma(puntosSuma);
    }

    @JavascriptInterface
    public int getPuntosResta() {
        return settings.getPuntosResta();
    }

    @JavascriptInterface
    public void setPuntosResta(int puntosResta) {
        settings.setPuntosResta(puntosResta);
    }

    @JavascriptInterface
    public int getPuntosMulti() {
        return settings.getPuntosMulti();
    }

    @JavascriptInterface
    public void setPuntosMulti(int puntosMulti) {
        settings.setPuntosMulti(puntosMulti);
    }

    @JavascriptInterface
    public int getPuntosDiv() {
        return settings.getPuntosDiv();
    }

    @JavascriptInterface
    public void setPuntosDiv(int puntosDiv) {
        settings.setPuntosDiv(puntosDiv);
    }

    @JavascriptInterface
    public int getFallosSuma() {
        return settings.getFallosSuma();
    }

    @JavascriptInterface
    public void setFallosSuma(int fallosSuma) {
        settings.setFallosSuma(fallosSuma);
    }

    @JavascriptInterface
    public int getFallosResta() {
        return settings.getFallosResta();
    }

    @JavascriptInterface
    public void setFallosResta(int fallosResta) {
        settings.setFallosResta( fallosResta );
    }

    @JavascriptInterface
    public int getFallosMulti() {
        return settings.getFallosMulti();
    }

    @JavascriptInterface
    public void setFallosMulti(int fallosMulti) {
        settings.setFallosMulti(fallosMulti);
    }

    @JavascriptInterface
    public int getFallosDiv() {
        return settings.getFallosDiv();
    }

    @JavascriptInterface
    public void setFallosDiv(int fallosDiv) {
        settings.setFallosDiv(fallosDiv);
    }

    @JavascriptInterface
    public int getDificultad() {
        return settings.getDificultad();
    }

    @JavascriptInterface
    public void setDificultad(int fallosDiv) {
        settings.setDificultad(fallosDiv);
    }

    @JavascriptInterface
    public void writeConfig() {

        FireStore_Adapter.writeConfig(FirebaseFirestore.getInstance(), settings);
        ConfigDB_Adapter.writeConfig(new DatabaseHelper(context), settings);
    }
}
