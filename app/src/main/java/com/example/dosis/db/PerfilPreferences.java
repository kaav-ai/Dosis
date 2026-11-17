package com.example.dosis.db;

import android.content.Context;
import android.content.SharedPreferences;

public class PerfilPreferences {

    private static final String PREF_NAME = "DosisPerfilPrefs";
    private static final String KEY_NOMBRE_USUARIO = "nombre_usuario";
    private static final String KEY_NOTIFICACIONES_ACTIVAS = "notificaciones_activas";
    private static final String KEY_FORMATO_24H = "formato_24h";

    private final SharedPreferences sharedPreferences;

    public PerfilPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void guardarNombreUsuario(String nombre) {
        sharedPreferences.edit().putString(KEY_NOMBRE_USUARIO, nombre).apply();
    }

    public String obtenerNombreUsuario() {
        return sharedPreferences.getString(KEY_NOMBRE_USUARIO, "Alex Ramírez");
    }

    public void setNotificacionesActivas(boolean activas) {
        sharedPreferences.edit().putBoolean(KEY_NOTIFICACIONES_ACTIVAS, activas).apply();
    }

    public boolean areNotificacionesActivas() {
        return sharedPreferences.getBoolean(KEY_NOTIFICACIONES_ACTIVAS, true);
    }

    public void setFormato24Horas(boolean es24Horas) {
        sharedPreferences.edit().putBoolean(KEY_FORMATO_24H, es24Horas).apply();
    }

    public boolean isFormato24Horas() {
        return sharedPreferences.getBoolean(KEY_FORMATO_24H, false);
    }
}