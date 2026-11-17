package com.example.dosis;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.example.dosis.db.PerfilPreferences;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class PerfilFragment extends Fragment {

    private TextView tvInicialesPerfil, tvNombrePerfil, tvEstadoAlarmas, tvFormatoHorario;
    private MaterialCardView btnEditarNombre, cardAlarmasExactas, cardFormatoHorario;
    private SwitchMaterial switchNotificaciones;
    private PerfilPreferences perfilPrefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ajuste dinámico para evitar que la barra de estado cubra el contenido
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), systemBars.top, v.getPaddingRight(), v.getPaddingBottom());
            return insets;
        });

        perfilPrefs = new PerfilPreferences(requireContext());

        tvInicialesPerfil = view.findViewById(R.id.tvInicialesPerfil);
        tvNombrePerfil = view.findViewById(R.id.tvNombrePerfil);
        tvEstadoAlarmas = view.findViewById(R.id.tvEstadoAlarmas);
        tvFormatoHorario = view.findViewById(R.id.tvFormatoHorario);
        btnEditarNombre = view.findViewById(R.id.btnEditarNombre);
        switchNotificaciones = view.findViewById(R.id.switchNotificaciones);
        cardAlarmasExactas = view.findViewById(R.id.cardAlarmasExactas);
        cardFormatoHorario = view.findViewById(R.id.cardFormatoHorario);

        cargarDatosPerfil();
        configurarEventos();
    }

    @Override
    public void onResume() {
        super.onResume();
        verificarPermisoAlarmasExactas();
    }

    private void cargarDatosPerfil() {
        String nombre = perfilPrefs.obtenerNombreUsuario();
        tvNombrePerfil.setText(nombre);
        tvInicialesPerfil.setText(generarIniciales(nombre));

        switchNotificaciones.setChecked(perfilPrefs.areNotificacionesActivas());
        actualizarTextoFormatoHorario(perfilPrefs.isFormato24Horas());
    }

    private void configurarEventos() {
        btnEditarNombre.setOnClickListener(v -> mostrarDialogoEditarNombre());

        switchNotificaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            perfilPrefs.setNotificacionesActivas(isChecked);
            String estado = isChecked ? "activadas" : "desactivadas";
            Toast.makeText(requireContext(), "Notificaciones " + estado, Toast.LENGTH_SHORT).show();
        });

        cardAlarmasExactas.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            } else {
                Toast.makeText(requireContext(), "Permitidas automáticamente en este dispositivo", Toast.LENGTH_SHORT).show();
            }
        });

        cardFormatoHorario.setOnClickListener(v -> {
            boolean nuevoEstado24h = !perfilPrefs.isFormato24Horas();
            perfilPrefs.setFormato24Horas(nuevoEstado24h);
            actualizarTextoFormatoHorario(nuevoEstado24h);
        });
    }

    private void mostrarDialogoEditarNombre() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Editar nombre");

        final EditText input = new EditText(requireContext());
        input.setText(perfilPrefs.obtenerNombreUsuario());
        input.setSelection(input.getText().length());
        builder.setView(input);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nuevoNombre = input.getText().toString().trim();
            if (!nuevoNombre.isEmpty()) {
                perfilPrefs.guardarNombreUsuario(nuevoNombre);
                tvNombrePerfil.setText(nuevoNombre);
                tvInicialesPerfil.setText(generarIniciales(nuevoNombre));
            } else {
                Toast.makeText(requireContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private String generarIniciales(String nombre) {
        String[] palabras = nombre.trim().split("\\s+");
        if (palabras.length >= 2) {
            return ("" + palabras[0].charAt(0) + palabras[1].charAt(0)).toUpperCase();
        } else if (palabras.length == 1 && !palabras[0].isEmpty()) {
            return ("" + palabras[0].charAt(0)).toUpperCase();
        }
        return "U";
    }

    private void actualizarTextoFormatoHorario(boolean es24h) {
        if (es24h) {
            tvFormatoHorario.setText(R.string.perfil_horario_24h);
        } else {
            tvFormatoHorario.setText(R.string.perfil_horario_12h);
        }
    }

    private void verificarPermisoAlarmasExactas() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && alarmManager.canScheduleExactAlarms()) {
                tvEstadoAlarmas.setText(R.string.perfil_alarmas_permitidas);
            } else {
                tvEstadoAlarmas.setText(R.string.perfil_alarmas_requeridas);
            }
        } else {
            tvEstadoAlarmas.setText(R.string.perfil_alarmas_permitidas);
        }
    }
}