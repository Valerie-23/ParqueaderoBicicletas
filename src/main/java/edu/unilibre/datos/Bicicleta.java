package edu.unilibre.datos;

public class Bicicleta {
    private String serial;
    private String color;
    private Propietario propietario;

    public Bicicleta() {
    }

    public Bicicleta(String serial, String color, Propietario propietario) {
        this.serial = serial;
        this.color = color;
        this.propietario = propietario;
    }

    public String obtenerSerial() { return serial; }
    public void asignarSerial(String serial) { this.serial = serial; }

    public String obtenerColor() { return color; }
    public void asignarColor(String color) { this.color = color; }

    public Propietario obtenerPropietario() { return propietario; }
    public void asignarPropietario(Propietario propietario) { this.propietario = propietario; }
}