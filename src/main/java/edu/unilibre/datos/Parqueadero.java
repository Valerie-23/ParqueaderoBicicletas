package edu.unilibre.datos;
import java.util.ArrayList;

public class Parqueadero {
    private int capacidad;
    private int espaciosLibres;
    private ArrayList<Cupo> cupos;

    public Parqueadero() {
        this.cupos = new ArrayList<>();
    }

    public Parqueadero(int capacidad) {
        this.capacidad = capacidad;
        this.espaciosLibres = capacidad;
        this.cupos = new ArrayList<>();
        for (int i = 1; i <= capacidad; i++) {
            this.cupos.add(new Cupo(i));
        }
    }

    public int obtenerCapacidad() { return capacidad; }
    public void asignarCapacidad(int capacidad) { this.capacidad = capacidad; }

    public int obtenerEspaciosLibres() { return espaciosLibres; }
    public void asignarEspaciosLibres(int espaciosLibres) { this.espaciosLibres = espaciosLibres; }

    public ArrayList<Cupo> obtenerListaCupos() { return cupos; }
    public void asignarListaCupos(ArrayList<Cupo> cupos) { this.cupos = cupos; }
}
