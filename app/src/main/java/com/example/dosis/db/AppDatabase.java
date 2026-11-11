package com.example.dosis.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Medicamento.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedicamentoDAO medicamentoDAO();
    private static AppDatabase INSTANCIA;

    public static AppDatabase getInstancia(Context context) {
        if (INSTANCIA == null) {
            INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "dosis_bd").allowMainThreadQueries().build();
        }
        return INSTANCIA;
    }
}