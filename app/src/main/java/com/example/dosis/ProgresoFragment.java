package com.example.dosis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dosis.db.AppDatabase;

public class ProgresoFragment extends Fragment {

    private TextView tvResumenMesNumero, tvConstanciaTexto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_progreso, container, false);

        // Enlazamos los componentes con el XML actualizado
        tvResumenMesNumero = vista.findViewById(R.id.tvResumenMesNumero);
        tvConstanciaTexto = vista.findViewById(R.id.tvConstanciaTexto);

        cargarEstadisticasReales();

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarEstadisticasReales();
    }

    private void cargarEstadisticasReales() {
        if (getContext() != null) {
            // Consultar el total de dosis completadas desde SQLite mediante Room
            int totalCompletadas = AppDatabase.getInstancia(getContext()).registroHoraDAO().obtenerTotalCompletadas();

            // Reflejar el número real en la pantalla
            tvResumenMesNumero.setText(String.valueOf(totalCompletadas));
            tvConstanciaTexto.setText("Has completado " + totalCompletadas + " dosis programadas.");
        }
    }
}