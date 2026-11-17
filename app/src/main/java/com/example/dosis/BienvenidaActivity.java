package com.example.dosis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BienvenidaActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar si ya existe un nombre guardado previamente
        SharedPreferences prefs = getSharedPreferences("DosisPrefs", Context.MODE_PRIVATE);
        String nombreGuardado = prefs.getString("nombre_usuario", null);

        if (nombreGuardado != null) {
            // Si ya guardó su nombre antes, saltar directo a la MainActivity principal
            startActivity(new Intent(BienvenidaActivity.this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_bienvenida);

        etNombre = findViewById(R.id.etNombreUsuario);
        btnGuardar = findViewById(R.id.btnGuardarNombre);

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();

            if (nombre.isEmpty()) {
                etNombre.setError("Por favor ingresa tu nombre");
                return;
            }

            // Guardar el nombre permanentemente
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nombre_usuario", nombre);
            editor.apply();

            Toast.makeText(this, "¡Hola, " + nombre + "!", Toast.LENGTH_SHORT).show();

            // Ir a la pantalla principal y cerrar la de bienvenida
            startActivity(new Intent(BienvenidaActivity.this, MainActivity.class));
            finish();
        });
    }
}