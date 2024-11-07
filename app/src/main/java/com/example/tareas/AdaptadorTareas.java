package com.example.tareas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareas.modelos.Tareas;

import java.util.List;

public class AdaptadorTareas extends RecyclerView.Adapter<AdaptadorTareas.MyViewHolder> {

    private List<Tareas> listaDeTareas;

    public void setListaDeTareas(List<Tareas> listaDeTareas) {
        this.listaDeTareas = listaDeTareas;
    }

    public AdaptadorTareas(List<Tareas> tareas) {
        this.listaDeTareas = tareas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaTareas = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_tareas, viewGroup, false);
        return new MyViewHolder(filaTareas);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener la tarea de nuestra lista gracias al índice i
        Tareas tareas = listaDeTareas.get(i);

        // Obtener los datos de la lista
        String nombreMascota = tareas.getNombre();
        int numeroTarea = tareas.getNumero();
        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nombreMascota);
        myViewHolder.numero.setText(String.valueOf(numeroTarea));
    }

    @Override
    public int getItemCount() {
        return listaDeTareas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, numero;

        MyViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.tvNombre);
            this.numero = itemView.findViewById(R.id.tvNumero);
        }
    }
}
