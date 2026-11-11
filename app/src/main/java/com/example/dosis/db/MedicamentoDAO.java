package com.example.dosis.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update; // <-- Importación nueva
import java.util.List;

@Dao
public interface MedicamentoDAO {
    @Insert
    void insertarMedicamento(Medicamento medicamento);

    @Query("SELECT * FROM medicamentos")
    List<Medicamento> obtenerTodos();

    @Delete
    void eliminarMedicamento(Medicamento medicamento);

    @Update // <-- Función nueva para actualizar[cite: 9]
    void actualizarMedicamento(Medicamento medicamento);
}