package edu.unilibre.datos;

public class Propietario {
    private String cedula;
    private String nombre;

    public Propietario() {
    }

    public Propietario(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public String obtenerCedula() { return cedula; }
    public void asignarCedula(String cedula) { this.cedula = cedula; }

    public String obtenerNombre() { return nombre; }
    public void asignarNombre(String nombre) { this.nombre = nombre; }
}