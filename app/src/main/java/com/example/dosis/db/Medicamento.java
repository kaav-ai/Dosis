package com.example.dosis.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicamentos")
public class Medicamento {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;
    public String dosis;
    public int frecuenciaHoras; // Ej: cada 8 horas
    public long proximaToma; // Timestamp en milisegundos
}