package com.example.dosis.servicios;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class AlarmaReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "dosis_canal";

    @Override
    public void onReceive(Context context, Intent intent) {
        String nombreMed = intent.getStringExtra("nombre_med");
        String dosisMed = intent.getStringExtra("dosis_med");

        NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Crear el canal (Obligatorio en Android 8+)[cite: 5]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(CHANNEL_ID, "Recordatorios Dosis", NotificationManager.IMPORTANCE_HIGH);
            notifManager.createNotificationChannel(canal);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentTitle("¡Hora de tu medicina!")
                .setContentText("Toma " + dosisMed + " de " + nombreMed)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notifManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}