package edu.unilibre.operaciones;

import edu.unilibre.datos.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestionBicicletas {

    private Parqueadero parqueadero;
    private ArrayList<Pago> pagosDelDia;

    public GestionBicicletas() {
        this.parqueadero = new Parqueadero(10); // Capacidad por defecto de 10 cupos
        this.pagosDelDia = new ArrayList<>();
    }

    public GestionBicicletas(int capacidad) {
        this.parqueadero = new Parqueadero(capacidad);
        this.pagosDelDia = new ArrayList<>();
    }

    public boolean registrarIngreso(String cedula, String nombre, String serial, String color) {
        if (cedula == null || cedula.isEmpty() || serial == null || serial.isEmpty()) {
            return false;
        }

        if (parqueadero.obtenerEspaciosLibres() <= 0) {
            return false;
        }

        for (Cupo cupo : parqueadero.obtenerListaCupos()) {
            if (!cupo.obtenerOcupado()) {
                Propietario propietario = new Propietario(cedula, nombre);
                Bicicleta bicicleta = new Bicicleta(serial, color, propietario);
                Hora hora = new Hora(LocalDateTime.now());

                cupo.asignarBicicleta(bicicleta);
                cupo.asignarHora(hora);
                cupo.asignarOcupado(true);

                parqueadero.asignarEspaciosLibres(parqueadero.obtenerEspaciosLibres() - 1);
                return true;
            }
        }
        return false;
    }

    public Cupo registrarSalida(String serial, String cedula) {
        if (serial == null || cedula == null || serial.isEmpty() || cedula.isEmpty()) {
            return null;
        }

        for (Cupo cupo : parqueadero.obtenerListaCupos()) {
            if (cupo.obtenerOcupado()
                    && cupo.obtenerBicicleta() != null
                    && cupo.obtenerBicicleta().obtenerSerial().equalsIgnoreCase(serial)
                    && cupo.obtenerBicicleta().obtenerPropietario().obtenerCedula().equals(cedula)) {

                cupo.obtenerHora().asignarHoraSalida(LocalDateTime.now());
                return cupo;
            }
        }
        return null;
    }

    public double calcularValor(Hora hora) {
        if (hora == null || hora.obtenerHoraIngreso() == null) {
            return 0.0;
        }

        LocalDateTime salida = hora.obtenerHoraSalida();
        if (salida == null) {
            salida = LocalDateTime.now();
        }

        long minutos = Duration.between(hora.obtenerHoraIngreso(), salida).toMinutes();
        if (minutos <= 0) {
            minutos = 1; // Tarifa mínima de 1 minuto
        }

        return minutos * 10.0;
    }

    public boolean registrarPago(Cupo cupo, MetodoPago metodoPago) {
        if (cupo == null || !cupo.obtenerOcupado()) {
            return false;
        }

        double valor = calcularValor(cupo.obtenerHora());
        Pago pago = new Pago(valor, metodoPago, LocalDateTime.now());

        cupo.asignarPago(pago);
        pagosDelDia.add(pago);

        liberarCupo(cupo);
        return true;
    }

    public void liberarCupo(Cupo cupo) {
        if (cupo != null) {
            cupo.asignarBicicleta(null);
            cupo.asignarHora(null);
            cupo.asignarPago(null);
            cupo.asignarOcupado(false);
            parqueadero.asignarEspaciosLibres(parqueadero.obtenerEspaciosLibres() + 1);
        }
    }

    public int obtenerTotalAtendidas() {
        return pagosDelDia.size();
    }

    public double obtenerTotalRecaudado() {
        double total = 0.0;
        for (Pago p : pagosDelDia) {
            total += p.obtenerValor();
        }
        return total;
    }

    public Parqueadero obtenerParqueadero() {
        return parqueadero;
    }
}