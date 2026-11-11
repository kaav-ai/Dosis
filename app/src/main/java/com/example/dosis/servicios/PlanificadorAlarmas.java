package com.example.dosis.servicios;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.example.dosis.db.Medicamento;

public class PlanificadorAlarmas {
    public static void programarAlarma(Context context, Medicamento med) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmaReceiver.class);
        intent.putExtra("nombre_med", med.nombre);
        intent.putExtra("dosis_med", med.dosis);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, med.id, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Disparar en el tiempo calculado[cite: 5]
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, med.proximaToma, pendingIntent);
        }
    }

    public static void cancelarAlarma(Context context, Medicamento med) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmaReceiver.class);

        // Debe ser exactamente el mismo PendingIntent que usamos para crearla
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, med.id, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }


}