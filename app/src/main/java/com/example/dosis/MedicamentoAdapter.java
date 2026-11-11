package com.example.dosis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dosis.db.AppDatabase;
import com.example.dosis.db.Medicamento;
import com.example.dosis.servicios.PlanificadorAlarmas;
import java.util.List;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder> {

    private List<Medicamento> listaMedicamentos;

    public MedicamentoAdapter(List<Medicamento> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
    }

    @NonNull
    @Override
    public MedicamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento, parent, false);
        return new MedicamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoViewHolder holder, int position) {
        Medicamento med = listaMedicamentos.get(position);
        holder.tvNombre.setText(med.nombre);
        holder.tvDosis.setText(med.dosis);

        // Lógica para el botón de Eliminar
        holder.btnEliminar.setOnClickListener(v -> {
            // 1. Cancelar la alarma del sistema[cite: 9]
            PlanificadorAlarmas.cancelarAlarma(v.getContext(), med);

            // 2. Borrar de la base de datos local[cite: 9]
            AppDatabase bd = AppDatabase.getInstancia(v.getContext());
            bd.medicamentoDAO().eliminarMedicamento(med);

            // 3. Actualizar la lista visualmente
            listaMedicamentos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listaMedicamentos.size());

            Toast.makeText(v.getContext(), "Tratamiento eliminado", Toast.LENGTH_SHORT).show();
        });

        // Lógica para el botón de Editar (el ícono del lápiz)[cite: 19]
        holder.btnEditar.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(v.getContext(), AgregarMedicamentoActivity.class);
            intent.putExtra("id_med", med.id);
            intent.putExtra("nombre_med", med.nombre);
            intent.putExtra("dosis_med", med.dosis);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaMedicamentos.size();
    }

    public static class MedicamentoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDosis, tvHora;
        ImageView btnEditar, btnEliminar;

        public MedicamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvItemNombre);
            tvDosis = itemView.findViewById(R.id.tvItemDosis);
            tvHora = itemView.findViewById(R.id.tvItemHora);

            // Enlazamos los botones de tu diseño XML[cite: 19]
            btnEditar = itemView.findViewById(R.id.btnItemEditar);
            btnEliminar = itemView.findViewById(R.id.btnItemEliminar);
        }
    }
}