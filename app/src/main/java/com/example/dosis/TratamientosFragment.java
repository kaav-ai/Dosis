package com.example.dosis;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dosis.db.AppDatabase;
import com.example.dosis.db.Medicamento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class TratamientosFragment extends Fragment {

    private RecyclerView recyclerView;
    private MedicamentoAdapter adaptador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_tratamientos, container, false);

        // 1. Configurar el botón de agregar
        FloatingActionButton btnAgregar = vista.findViewById(R.id.btnAgregarDesdeTratamientos);
        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AgregarMedicamentoActivity.class);
            startActivity(intent);
        });

        // 2. Configurar la lista (RecyclerView)
        recyclerView = vista.findViewById(R.id.recyclerTratamientos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 3. Obtener datos de la Base de Datos Local[cite: 10]
        cargarMedicamentos();

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Recargar la lista cada vez que se vuelve a esta pantalla (por si se agregó uno nuevo)
        cargarMedicamentos();
    }

    private void cargarMedicamentos() {
        // Consultar la base de datos local usando el DAO[cite: 9]
        AppDatabase bd = AppDatabase.getInstancia(getContext());
        List<Medicamento> lista = bd.medicamentoDAO().obtenerTodos();

        // Enviar la lista al adaptador
        adaptador = new MedicamentoAdapter(lista);
        recyclerView.setAdapter(adaptador);
    }
}