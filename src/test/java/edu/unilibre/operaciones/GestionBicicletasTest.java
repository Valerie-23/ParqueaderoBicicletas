package edu.unilibre.operaciones;

import edu.unilibre.datos.Cupo;
import edu.unilibre.datos.MetodoPago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GestionBicicletasTest {

    private GestionBicicletas servicio;

    @BeforeEach
    public void setUp() {
        servicio = new GestionBicicletas(5);
    }

    @Test
    public void registrarIngresoOk() {
        boolean resultado = servicio.registrarIngreso("101010", "Carlos", "BIC-001", "Rojo");
        assertTrue(resultado);
    }

    @Test
    public void registrarIngresoSinCedula() {
        boolean resultado = servicio.registrarIngreso("", "Carlos", "BIC-001", "Rojo");
        assertFalse(resultado);
    }

    @Test
    public void registrarIngresoCedulaNula() {
        boolean resultado = servicio.registrarIngreso(null, "Carlos", "BIC-001", "Rojo");
        assertFalse(resultado);
    }

    @Test
    public void registrarIngresoSinSerial() {
        boolean resultado = servicio.registrarIngreso("101010", "Carlos", "", "Rojo");
        assertFalse(resultado);
    }

    @Test
    public void registrarSalidaOk() {
        servicio.registrarIngreso("101010", "Carlos", "BIC-001", "Rojo");
        Cupo cupo = servicio.registrarSalida("BIC-001", "101010");
        assertNotNull(cupo);
    }

    @Test
    public void registrarSalidaCedulaIncorrectaDevuelveNulo() {
        servicio.registrarIngreso("101010", "Carlos", "BIC-001", "Rojo");
        Cupo cupo = servicio.registrarSalida("BIC-001", "999999");
        assertNull(cupo);
    }

    @Test
    public void registrarSalidaSerialNuloDevuelveNulo() {
        Cupo cupo = servicio.registrarSalida(null, "101010");
        assertNull(cupo);
    }

    @Test
    public void calcularValorHoraNulaDevuelveCero() {
        double valor = servicio.calcularValor(null);
        assertEquals(0.0, valor);
    }

    @Test
    public void registrarPagoOk() {
        servicio.registrarIngreso("101010", "Carlos", "BIC-001", "Rojo");
        Cupo cupo = servicio.registrarSalida("BIC-001", "101010");
        boolean pagoExitoso = servicio.registrarPago(cupo, MetodoPago.EFECTIVO);
        assertTrue(pagoExitoso);
    }

    @Test
    public void registrarPagoCupoNuloDevuelveFalso() {
        boolean pagoExitoso = servicio.registrarPago(null, MetodoPago.EFECTIVO);
        assertFalse(pagoExitoso);
    }

    @Test
    public void obtenerTotalAtendidasVacio() {
        assertEquals(0, servicio.obtenerTotalAtendidas());
    }

    @Test
    public void obtenerTotalAtendidasConUno() {
        servicio.registrarIngreso("101010", "Carlos", "BIC-001", "Rojo");
        Cupo cupo = servicio.registrarSalida("BIC-001", "101010");
        servicio.registrarPago(cupo, MetodoPago.TRANSFERENCIA);
        assertEquals(1, servicio.obtenerTotalAtendidas());
    }
}