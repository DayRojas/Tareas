package com.example.tareas.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.tareas.Helper;
import com.example.tareas.modelos.Tareas;

import java.util.ArrayList;

public class TareasController {
    private Helper helper;
    private String NOMBRE_TABLA = "tareas";

    public TareasController(Context contexto) {

        helper = new Helper (contexto);
    }


    public int eliminarTarea(Tareas tareas) {

        SQLiteDatabase baseDeDatos = helper.getWritableDatabase();
        String[] argumentos = {String.valueOf(tareas.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaTarea(Tareas tareas) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = helper.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("nombre", tareas.getNombre());
        valoresParaInsertar.put("numero", tareas.getNumero());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public int guardarCambios(Tareas tareaEditada) {
        SQLiteDatabase baseDeDatos = helper.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("nombre", tareaEditada.getNombre());
        valoresParaActualizar.put("numero", tareaEditada.getNumero());
        // cuando id...
        String campoParaActualizar = "id = ?";
        // ... = idTarea
        String[] argumentosParaActualizar = {String.valueOf(tareaEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Tareas> obtenerTareas() {
        ArrayList<Tareas> tareas = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = helper.getReadableDatabase();
        // SELECT nombre, numero, id
        String[] columnasAConsultar = {"nombre", "numero", "id"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from mascotas
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                regresar lista vacía
             */
            return tareas;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return tareas;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de tareas
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            String nombreObtenidoDeBD = cursor.getString(0);
            int numeroObtenidaDeBD = cursor.getInt(1);
            long idTarea = cursor.getLong(2);
            Tareas tareaObtenidaDeBD = new Tareas(nombreObtenidoDeBD, numeroObtenidaDeBD, idTarea);
            tareas.add(tareaObtenidaDeBD);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de tareas
        cursor.close();
        return tareas;
    }
}
