package com.example.tareas.modelos;

public class Tareas {
    private String nombre;
    private int numero;

    private long id; // El ID de la BD

    public Tareas (String nombre, int numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    // Constructor para cuando instanciamos desde la BD
    public Tareas (String nombre, int numero, long id) {
        this.nombre = nombre;
        this.numero = numero;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setEdad(int edad) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", numero=" + numero +
                '}';
    }

}
