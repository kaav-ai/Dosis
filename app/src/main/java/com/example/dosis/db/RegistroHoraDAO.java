package com.example.dosis.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RegistroHoraDAO {
    @Insert
    void insertarRegistro(RegistroHora registro);

    // Cuenta cuántas dosis están marcadas como completadas para mostrarlas en Progreso
    @Query("SELECT COUNT(*) FROM registro_horas WHERE estado = 'completada'")
    int obtenerTotalCompletadas();
}