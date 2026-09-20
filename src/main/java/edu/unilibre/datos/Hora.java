package edu.unilibre.datos;
import java.time.LocalDateTime;

public class Hora {
    private LocalDateTime horaIngreso;
    private LocalDateTime horaSalida;

    public Hora() {
    }

    public Hora(LocalDateTime horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public LocalDateTime obtenerHoraIngreso() { return horaIngreso; }
    public void asignarHoraIngreso(LocalDateTime horaIngreso) { this.horaIngreso = horaIngreso; }

    public LocalDateTime obtenerHoraSalida() { return horaSalida; }
    public void asignarHoraSalida(LocalDateTime horaSalida) { this.horaSalida = horaSalida; }
}