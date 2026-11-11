package com.example.dosis;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dosis.db.AppDatabase;
import com.example.dosis.db.Medicamento;
import com.example.dosis.servicios.PlanificadorAlarmas;

public class AgregarMedicamentoActivity extends AppCompatActivity {

    private EditText etNombre, etDosis;
    private Spinner spinnerFrecuencia;
    private Button btnGuardar;

    private int medicamentoId = -1; // Identificador si estamos editando
    private boolean esEdicion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medicamento);

        etNombre = findViewById(R.id.etNombreMed);
        etDosis = findViewById(R.id.etDosisMed);
        spinnerFrecuencia = findViewById(R.id.spinnerFrecuencia);
        btnGuardar = findViewById(R.id.btnGuardarMedicamento);

        String[] opcionesFrecuencia = {"Cada 8 horas", "Cada 12 horas", "Cada 24 horas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesFrecuencia);
        spinnerFrecuencia.setAdapter(adapter);

        // Verificar si nos pasaron datos para editar
        if (getIntent().hasExtra("id_med")) {
            esEdicion = true;
            medicamentoId = getIntent().getIntExtra("id_med", -1);
            etNombre.setText(getIntent().getStringExtra("nombre_med"));
            etDosis.setText(getIntent().getStringExtra("dosis_med"));
            setTitle("Editar Medicamento");
            btnGuardar.setText("Actualizar Medicamento");
        }

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String dosis = etDosis.getText().toString().trim();
            int posicionSeleccionada = spinnerFrecuencia.getSelectedItemPosition();

            if (nombre.isEmpty()) {
                etNombre.setError("El nombre no puede estar vacío");
                return;
            }
            if (dosis.isEmpty()) {
                etDosis.setError("La dosis no puede estar vacía");
                return;
            }

            int horasAAgregar = 8;
            if (posicionSeleccionada == 0) horasAAgregar = 8;
            else if (posicionSeleccionada == 1) horasAAgregar = 12;
            else if (posicionSeleccionada == 2) horasAAgregar = 24;

            AppDatabase bd = AppDatabase.getInstancia(AgregarMedicamentoActivity.this);

            if (esEdicion) {
                // Actualizar registro existente
                Medicamento med = new Medicamento();
                med.id = medicamentoId;
                med.nombre = nombre;
                med.dosis = dosis;
                med.frecuenciaHoras = horasAAgregar;
                med.proximaToma = System.currentTimeMillis() + (horasAAgregar * 60L * 60L * 1000L);

                bd.medicamentoDAO().actualizarMedicamento(med);
                PlanificadorAlarmas.programarAlarma(AgregarMedicamentoActivity.this, med);

                Toast.makeText(this, "Tratamiento actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                // Crear nuevo registro[cite: 10]
                Medicamento med = new Medicamento();
                med.nombre = nombre;
                med.dosis = dosis;
                med.frecuenciaHoras = horasAAgregar;
                med.proximaToma = System.currentTimeMillis() + (horasAAgregar * 60L * 60L * 1000L);

                bd.medicamentoDAO().insertarMedicamento(med);
                PlanificadorAlarmas.programarAlarma(AgregarMedicamentoActivity.this, med);

                Toast.makeText(this, "Tratamiento guardado correctamente", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}