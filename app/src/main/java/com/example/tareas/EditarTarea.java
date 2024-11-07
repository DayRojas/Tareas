package com.example.tareas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tareas.controllers.TareasController;
import com.example.tareas.modelos.Tareas;

public class EditarTarea extends AppCompatActivity {

    private EditText etEditarNombre, etEditarNumero;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private Tareas tareas;//La mascota que vamos a estar editando
    private TareasController tareasController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_tareas);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de las mascotas
        tareasController = new TareasController(EditarTarea.this);

        // Rearmar la mascota
        // Nota: igualmente solamente podríamos mandar el id y recuperar la mascota de la BD
        long idTarea = extras.getLong("idTarea");
        String nombreTarea = extras.getString("nombreTarea");
        int numeroTarea = extras.getInt("numeroTarea");
        tareas = new Tareas(nombreTarea, numeroTarea, idTarea);


        // Ahora declaramos las vistas
        etEditarNumero = findViewById(R.id.etEditarNumero);
        etEditarNombre = findViewById(R.id.etEditarNombre);
        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionTarea);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosTarea);


        // Rellenar los EditText con los datos de la mascota
        etEditarNumero.setText(String.valueOf(tareas.getNumero()));
        etEditarNombre.setText(tareas.getNombre());

        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen
                etEditarNombre.setError(null);
                etEditarNumero.setError(null);
                // Crear la mascota con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoNombre = etEditarNombre.getText().toString();
                String posibleNuevaEdad = etEditarNumero.getText().toString();
                if (nuevoNombre.isEmpty()) {
                    etEditarNombre.setError("Escribe el nombre");
                    etEditarNombre.requestFocus();
                    return;
                }
                if (posibleNuevaEdad.isEmpty()) {
                    etEditarNumero.setError("Escribe el numero");
                    etEditarNumero.requestFocus();
                    return;
                }
                // Si no es entero, igualmente marcar error
                int nuevoNumero;
                try {
                    nuevoNumero = Integer.parseInt(posibleNuevaEdad);
                } catch (NumberFormatException e) {
                    etEditarNumero.setError("Escribe un número");
                    etEditarNumero.requestFocus();
                    return;
                }
                // Si llegamos hasta aquí es porque los datos ya están validados
                Tareas tareaConNuevosCambios = new Tareas(nuevoNombre, nuevoNumero, tareas.getId());
                int filasModificadas = tareasController.guardarCambios(tareaConNuevosCambios);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarTarea.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }

}
