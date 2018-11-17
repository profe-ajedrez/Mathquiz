package org.config;


public class Config {

    public static final int OP_SUMA  = 0;
    public static final int OP_RESTA = 1;
    public static final int OP_MULTIPLICACION = 2;
    public static final int OP_DIVISION       = 3;
    public static final String TABLE_NAME = "settings";

    public static final String C_ID        = "id";
    public static final String C_TOKEN     = "token";
    public static final String C_SONIDO    = "sonido";
    public static final String C_ANIMACION = "animacion";
    public static final String C_OPERACION = "operacion";
    public static final String C_P_SUMA    = "puntos_suma";
    public static final String C_P_RESTA   = "puntos_resta";
    public static final String C_P_MULTI   = "puntos_multi";
    public static final String C_P_DIVI    = "puntos_divi";
    public static final String C_F_SUMA    = "fallos_suma";
    public static final String C_F_RESTA   = "fallos_resta";
    public static final String C_F_MULTI   = "fallos_multi";
    public static final String C_F_DIVI    = "fallos_divi";
    public static final String C_DIFICULTAD = "dificultad";

    public static final String CREATE_TABLE = "CREATE TABLE " +
                                              TABLE_NAME +
                                              "(" +C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                              C_TOKEN + " TEXT, " +
                                              C_SONIDO + " NUMERIC, " +
                                              C_ANIMACION + " NUMERIC, " +
                                              C_OPERACION + " NUMERIC, " +
                                              C_P_SUMA     + " NUMERIC, " +
                                              C_P_RESTA    + " NUMERIC, " +
                                              C_P_MULTI    + " NUMERIC, " +
                                              C_P_DIVI     + " NUMERIC, " +
                                              C_F_SUMA     + " NUMERIC, " +
                                              C_F_RESTA    + " NUMERIC, " +
                                              C_F_MULTI    + " NUMERIC, " +
                                              C_F_DIVI     + " NUMERIC, " +
                                              C_DIFICULTAD + ")";

    private int id;
    private String  userToken = "";
    private boolean soundActive = true;
    private boolean animationActive = true;
    private int     operacionActiva = OP_SUMA;
    private int     puntosSuma = 0;
    private int     puntosResta = 0;
    private int     puntosMulti = 0;
    private int     puntosDiv = 0;
    private int     fallosSuma = 0;
    private int     fallosResta = 0;
    private int     fallosMulti = 0;
    private int     fallosDiv = 0;
    private int     dificultad = 1;

    public Config() { }

    public Config(int id, String userToken, boolean soundActive, boolean animationActive, int operacionActiva) {
        this.id = id;
        this.userToken = userToken;
        this.soundActive = soundActive;
        this.animationActive = animationActive;
        this.operacionActiva = operacionActiva;
    }

    public Config(String userToken, boolean soundActive, boolean animationActive, int operacionActiva) {
        this.userToken = userToken;
        this.soundActive = soundActive;
        this.animationActive = animationActive;
        this.operacionActiva = operacionActiva;
    }


    public Config(int id, String userToken, boolean soundActive, boolean animationActive,
                  int operacionActiva, int puntosSuma, int puntosResta, int puntosMulti,
                  int puntosDiv, int fallosSuma, int fallosResta,
                  int fallosMulti, int fallosDiv, int difi) {
        this.id = id;
        this.userToken = userToken;
        this.soundActive = soundActive;
        this.animationActive = animationActive;
        this.operacionActiva = operacionActiva;
        this.puntosSuma = puntosSuma;
        this.puntosResta = puntosResta;
        this.puntosMulti = puntosMulti;
        this.puntosDiv = puntosDiv;
        this.fallosSuma = fallosSuma;
        this.fallosResta = fallosResta;
        this.fallosMulti = fallosMulti;
        this.fallosDiv = fallosDiv;
        this.dificultad = difi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public boolean isSoundActive() {
        return soundActive;
    }

    public void setSoundActive(boolean soundActive) {
        this.soundActive = soundActive;
    }

    public boolean isAnimationActive() {
        return animationActive;
    }

    public void setAnimationActive(boolean animationActive) {
        this.animationActive = animationActive;
    }

    public void setOperacionActiva(int operacionActiva) {
        this.operacionActiva = operacionActiva;
    }


    public int getPuntosSuma() {
        return puntosSuma;
    }

    public void setPuntosSuma(int puntosSuma) {
        this.puntosSuma = puntosSuma;
    }

    public int getPuntosResta() {
        return puntosResta;
    }

    public void setPuntosResta(int puntosResta) {
        this.puntosResta = puntosResta;
    }

    public int getPuntosMulti() {
        return puntosMulti;
    }

    public void setPuntosMulti(int puntosMulti) {
        this.puntosMulti = puntosMulti;
    }

    public int getPuntosDiv() {
        return puntosDiv;
    }

    public void setPuntosDiv(int puntosDiv) {
        this.puntosDiv = puntosDiv;
    }

    public int getFallosSuma() {
        return fallosSuma;
    }

    public void setFallosSuma(int fallosSuma) {
        this.fallosSuma = fallosSuma;
    }

    public int getFallosResta() {
        return fallosResta;
    }

    public void setFallosResta(int fallosResta) {
        this.fallosResta = fallosResta;
    }

    public int getFallosMulti() {
        return fallosMulti;
    }

    public void setFallosMulti(int fallosMulti) {
        this.fallosMulti = fallosMulti;
    }

    public int getFallosDiv() {
        return fallosDiv;
    }

    public void setFallosDiv(int fallosDiv) {
        this.fallosDiv = fallosDiv;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public void toggleSound() { this.soundActive = !this.soundActive; }


    public void activateSound() { this.soundActive = true; }


    public void deActivateSound() { this.soundActive = false; }


    public void toggleAnimation() { this.animationActive = ! this.animationActive; }


    public void activateAnimation() { this.animationActive = true; }


    public void deActivateAnimation() { this.animationActive = false; }


    public void activateSuma() { this.operacionActiva = OP_SUMA; }


    public void activateResta() { this.operacionActiva = OP_RESTA; }


    public void activateMultiplicacion() { this.operacionActiva = OP_MULTIPLICACION; }


    public void activateDivision() { this.operacionActiva = OP_DIVISION; }



    public int getOperacionActiva() { return this.operacionActiva; }


    public String operacionActivaToString() {
        String op = "Indefinida";

        switch( this.operacionActiva ) {
            case OP_SUMA:
                 op = "Suma"; break;
            case OP_RESTA:
                op = "Resta"; break;
            case OP_MULTIPLICACION:
                op = "Multiplicacion"; break;
            case OP_DIVISION:
                op = "Division"; break;
        }

        return op;
    }

     
}