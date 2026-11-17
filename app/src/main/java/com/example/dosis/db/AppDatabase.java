package com.example.dosis.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Añadimos RegistroHora.class a las entidades y aumentamos la versión a 2
@Database(entities = {Medicamento.class, RegistroHora.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MedicamentoDAO medicamentoDAO();
    public abstract RegistroHoraDAO registroHoraDAO(); // <-- Nuevo DAO

    private static AppDatabase INSTANCIA;

    public static AppDatabase getInstancia(Context context) {
        if (INSTANCIA == null) {
            INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "dosis_bd")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() // Evita errores si cambia la versión de la BD
                    .build();
        }
        return INSTANCIA;
    }
}