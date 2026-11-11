package com.example.dosis;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InicioFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos la vista primero para poder buscar los botones dentro de ella
        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        // Enlazamos el botón flotante de tu diseño[cite: 18]
        FloatingActionButton btnAgregar = vista.findViewById(R.id.btnAgregarDesdeInicio);

        btnAgregar.setOnClickListener(v -> {
            // Acción: Abrir la pantalla de agregar medicamento
            Intent intent = new Intent(getActivity(), AgregarMedicamentoActivity.class);
            startActivity(intent);
        });

        return vista;
    }
}