package com.example.dosis.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "registro_horas")
public class RegistroHora {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombreMedicamento;
    public String estado; // Guardará "completada" o "omitida"
}