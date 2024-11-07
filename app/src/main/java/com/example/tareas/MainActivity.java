package com.example.tareas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareas.controllers.TareasController;
import com.example.tareas.modelos.Tareas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<Tareas> listaDeTareas;
    private RecyclerView recyclerView;
    private AdaptadorTareas adaptadorTareas;
    private TareasController tareasController;
    private FloatingActionButton fabAgregarTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tareasController = new TareasController(MainActivity.this);

        recyclerView = findViewById(R.id.recyclerViewTareas);
        fabAgregarTarea = findViewById(R.id.fabAgregarTarea);



        listaDeTareas = new ArrayList<>();
        adaptadorTareas = new AdaptadorTareas(listaDeTareas);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorTareas);

        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeTareas();


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override // Un toque sencillo
            public void onClick(View view, int position) {

                Tareas tareaSeleccionada = listaDeTareas.get(position);
                Intent intent = new Intent(MainActivity.this, EditarTarea.class);
                intent.putExtra("idTarea", tareaSeleccionada.getId());
                intent.putExtra("nombreTarea", tareaSeleccionada.getNombre());
                intent.putExtra("numeroTarea", tareaSeleccionada.getNumero());
                startActivity(intent);
            }

            @Override // Un toque largo
            public void onLongClick(View view, int position) {
                final Tareas tareaParaEliminar = listaDeTareas.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tareasController.eliminarTarea(tareaParaEliminar);
                                refrescarListaDeTareas();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar la tarea " + tareaParaEliminar.getNombre() + "?")
                        .create();
                dialog.show();

            }
        }));

        // Listener del FAB
        fabAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cambiamos de actividad
                Intent intent = new Intent(MainActivity.this, AgregarTarea.class);
                startActivity(intent);
            }
        });

        // Créditos
        fabAgregarTarea.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Acerca de")

                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo, int which) {
                                dialogo.dismiss();
                            }
                        })
                        .setPositiveButton("Sitio web", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse("https://parzibyte.me"));
                                startActivity(intentNavegador);
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeTareas();
    }

    public void refrescarListaDeTareas() {
        /*
         * ==========
         obtenemos la lista de la BD
         * y se la ponemos al RecyclerView
         * ============
         *
         * */
        if (adaptadorTareas == null) return;
        listaDeTareas = tareasController.obtenerTareas();
        adaptadorTareas.setListaDeTareas(listaDeTareas);
        adaptadorTareas.notifyDataSetChanged();
    }

    }


