package com.example.dosis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InicioFragment extends Fragment {

    private TextView tvSaludoUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        // Enlazamos el TextView con el ID que acabamos de agregar en el XML
        tvSaludoUsuario = vista.findViewById(R.id.tvSaludoUsuario);

        // Leer el nombre guardado en SharedPreferences al ingresar a la app
        if (getContext() != null) {
            SharedPreferences prefs = getContext().getSharedPreferences("DosisPrefs", Context.MODE_PRIVATE);
            String nombre = prefs.getString("nombre_usuario", "Usuario");

            // Actualizar el texto dinámicamente con el nombre del usuario
            tvSaludoUsuario.setText("Buenos días, " + nombre);
        }

        FloatingActionButton btnAgregar = vista.findViewById(R.id.btnAgregarDesdeInicio);
        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AgregarMedicamentoActivity.class);
            startActivity(intent);
        });

        return vista;
    }
}