package com.example.tareas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tareas.controllers.TareasController;
import com.example.tareas.modelos.Tareas;

public class AgregarTarea extends AppCompatActivity {

    private Button btnAgregarTarea, btnCancelarNuevaTarea;
    private EditText etNombre, etNumero;
    private TareasController tareasController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_tarea);

        // Instanciar vistas
        etNombre = findViewById(R.id.etNombre);
        etNumero = findViewById(R.id.etNumero);
        btnAgregarTarea = findViewById(R.id.btnAgregarTarea);
        btnCancelarNuevaTarea = findViewById(R.id.btnCancelarNuevaTarea);
        // Crear el controlador
        tareasController = new TareasController(AgregarTarea.this);

        // Agregar listener del botón de guardar
        btnAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear errores a ambos
                etNombre.setError(null);
                etNumero.setError(null);
                String nombre = etNombre.getText().toString(),
                        edadComoCadena = etNumero.getText().toString();
                if ("".equals(nombre)) {
                    etNombre.setError("Escribe el nombre de la tarea");
                    etNombre.requestFocus();
                    return;
                }
                if ("".equals(edadComoCadena)) {
                    etNumero.setError("Escribe el numero de la tarea");
                    etNumero.requestFocus();
                    return;
                }

                // Ver si es un entero
                int numero;
                try {
                    numero = Integer.parseInt(etNumero.getText().toString());
                } catch (NumberFormatException e) {
                    etNumero.setError("Escribe un número");
                    etNumero.requestFocus();
                    return;
                }
                // Ya pasó la validación
                Tareas nuevaTarea = new Tareas(nombre, numero);
                long id = tareasController.nuevaTarea(nuevaTarea);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarTarea.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    finish();
                }
            }
        });

        // El de cancelar simplemente cierra la actividad
        btnCancelarNuevaTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
