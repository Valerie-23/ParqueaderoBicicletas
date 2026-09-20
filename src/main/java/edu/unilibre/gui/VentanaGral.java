package edu.unilibre.gui;

import edu.unilibre.datos.Cupo;
import edu.unilibre.datos.MetodoPago;
import edu.unilibre.operaciones.GestionBicicletas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class VentanaGral extends JFrame {

    private final GestionBicicletas servicio = new GestionBicicletas(10); // Parqueadero con 10 cupos

    private JTextField txtCedula, txtNombre, txtSerial, txtColor;

    private JTextField txtSerialSalida, txtCedulaSalida;
    private JComboBox<MetodoPago> cbMetodoPago;

    private JTextArea txtAreaConsola;
    private JLabel lblCapacidad, lblRecaudo;

    private final Color COLOR_BOTON = new Color(51, 111, 158);

    public VentanaGral() {
        setTitle("Sistema de Gestión de Parqueadero de Bicicletas");
        setSize(920, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initComponentes();
        actualizarEstado();
    }

    private void initComponentes() {

        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 8));
        pnlHeader.setBackground(new Color(240, 243, 246));
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        lblCapacidad = new JLabel("Cupos Libres: 10/10");
        lblCapacidad.setFont(new Font("Arial", Font.BOLD, 13));

        lblRecaudo = new JLabel("Total Recaudado: $0.0 COP");
        lblRecaudo.setFont(new Font("Arial", Font.BOLD, 13));

        pnlHeader.add(lblCapacidad);
        pnlHeader.add(new JSeparator(JSeparator.VERTICAL));
        pnlHeader.add(lblRecaudo);
        add(pnlHeader, BorderLayout.NORTH);

        JTabbedPane pestanas = new JTabbedPane();

        JPanel pnlIngreso = new JPanel(new GridLayout(5, 2, 8, 8));
        pnlIngreso.setBorder(new EmptyBorder(12, 12, 12, 12));

        pnlIngreso.add(new JLabel("Cédula Propietario:"));
        txtCedula = new JTextField();
        pnlIngreso.add(txtCedula);

        pnlIngreso.add(new JLabel("Nombre Propietario:"));
        txtNombre = new JTextField();
        pnlIngreso.add(txtNombre);

        pnlIngreso.add(new JLabel("Serial Bicicleta:"));
        txtSerial = new JTextField();
        pnlIngreso.add(txtSerial);

        pnlIngreso.add(new JLabel("Color Bicicleta:"));
        txtColor = new JTextField();
        pnlIngreso.add(txtColor);

        JButton btnIngresar = crearBoton("Registrar Ingreso ");
        pnlIngreso.add(new JLabel());
        pnlIngreso.add(btnIngresar);

        JPanel pnlSalida = new JPanel(new GridLayout(4, 2, 8, 8));
        pnlSalida.setBorder(new EmptyBorder(12, 12, 12, 12));

        pnlSalida.add(new JLabel("Serial Bicicleta:"));
        txtSerialSalida = new JTextField();
        pnlSalida.add(txtSerialSalida);

        pnlSalida.add(new JLabel("Cédula Propietario:"));
        txtCedulaSalida = new JTextField();
        pnlSalida.add(txtCedulaSalida);

        pnlSalida.add(new JLabel("Método de Pago:"));
        cbMetodoPago = new JComboBox<>(MetodoPago.values());
        pnlSalida.add(cbMetodoPago);

        JButton btnCalcular = crearBoton("Calcular Tarifa ");
        JButton btnPagar = crearBoton("Pagar y Liberar ");

        pnlSalida.add(btnCalcular);
        pnlSalida.add(btnPagar);

        pestanas.addTab("Ingreso", pnlIngreso);
        pestanas.addTab("Salida y Cobro", pnlSalida);

        JPanel pnlContenedorIzquierdo = new JPanel(new BorderLayout());
        pnlContenedorIzquierdo.setPreferredSize(new Dimension(410, 0));
        pnlContenedorIzquierdo.setBorder(new EmptyBorder(5, 10, 10, 0));
        pnlContenedorIzquierdo.add(pestanas, BorderLayout.CENTER);

        add(pnlContenedorIzquierdo, BorderLayout.WEST);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Estado del Parqueadero "));

        txtAreaConsola = new JTextArea();
        txtAreaConsola.setEditable(false);
        txtAreaConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(txtAreaConsola);
        panelDerecho.add(scroll, BorderLayout.CENTER);

        JPanel pnlContenedorDerecho = new JPanel(new BorderLayout());
        pnlContenedorDerecho.setBorder(new EmptyBorder(5, 0, 10, 10));
        pnlContenedorDerecho.add(panelDerecho, BorderLayout.CENTER);

        add(pnlContenedorDerecho, BorderLayout.CENTER);

        // --- MANEJO DE EVENTOS ---

        btnIngresar.addActionListener(e -> {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String serial = txtSerial.getText().trim();
            String color = txtColor.getText().trim();

            if (cedula.isEmpty() || nombre.isEmpty() || serial.isEmpty() || color.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos de ingreso son obligatorios.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (servicio.registrarIngreso(cedula, nombre, serial, color)) {
                JOptionPane.showMessageDialog(this, "Bicicleta ingresada correctamente.");
                txtCedula.setText("");
                txtNombre.setText("");
                txtSerial.setText("");
                txtColor.setText("");
                actualizarEstado();
            } else {
                JOptionPane.showMessageDialog(this, "No hay espacios disponibles en el parqueadero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCalcular.addActionListener(e -> {
            String serial = txtSerialSalida.getText().trim();
            String cedula = txtCedulaSalida.getText().trim();

            if (serial.isEmpty() || cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el serial y la cédula para realizar la búsqueda.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Cupo cupo = servicio.registrarSalida(serial, cedula);
            if (cupo != null) {
                double valor = servicio.calcularValor(cupo.obtenerHora());
                JOptionPane.showMessageDialog(this, "Vehículo en Cupo #" + cupo.obtenerNumero() +
                        "\nTotal a pagar: $" + valor + " COP ($10/minuto)", "Liquidación de Servicio", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró registro con los datos especificados.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPagar.addActionListener(e -> {
            String serial = txtSerialSalida.getText().trim();
            String cedula = txtCedulaSalida.getText().trim();
            MetodoPago metodo = (MetodoPago) cbMetodoPago.getSelectedItem();

            if (serial.isEmpty() || cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el serial y la cédula para procesar el pago.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Cupo cupo = servicio.registrarSalida(serial, cedula);
            if (cupo != null) {
                double valor = servicio.calcularValor(cupo.obtenerHora());
                if (servicio.registrarPago(cupo, metodo)) {
                    JOptionPane.showMessageDialog(this, "Pago de $" + valor + " completado vía " + metodo + ".\nCupo #" + cupo.obtenerNumero() + " liberado.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                    txtSerialSalida.setText("");
                    txtCedulaSalida.setText("");
                    actualizarEstado();
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el vehículo para efectuar la salida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        return btn;
    }

    private void actualizarEstado() {
        lblCapacidad.setText("Cupos Libres: " + servicio.obtenerParqueadero().obtenerEspaciosLibres() + "/" + servicio.obtenerParqueadero().obtenerCapacidad());
        lblRecaudo.setText("Total Recaudado: $" + servicio.obtenerTotalRecaudado() + " COP");

        StringBuilder sb = new StringBuilder();
        sb.append("=========================================================\n");
        sb.append("         ESTADO Y OCUPACIÓN DE CUPOS EN TIEMPO REAL      \n");
        sb.append("=========================================================\n\n");

        ArrayList<Cupo> cupos = servicio.obtenerParqueadero().obtenerListaCupos();
        for (Cupo c : cupos) {
            if (c.obtenerOcupado()) {
                sb.append(String.format("    Cupo [%02d] : OCUPADO\n", c.obtenerNumero()));
                sb.append(String.format("    • Serial: %s  | Color: %s\n", c.obtenerBicicleta().obtenerSerial(), c.obtenerBicicleta().obtenerColor()));
                sb.append(String.format("    • Cliente: %s (C.C. %s)\n", c.obtenerBicicleta().obtenerPropietario().obtenerNombre(), c.obtenerBicicleta().obtenerPropietario().obtenerCedula()));
                sb.append("      ---------------------------------------------------\n");
            } else {
                sb.append(String.format("   🔲 Cupo [%02d] : [ Disponible ]\n", c.obtenerNumero()));
            }
        }

        sb.append("\n=========================================================\n");
        sb.append("                   REPORTE DIARIO                       \n");
        sb.append("=========================================================\n");
        sb.append("   • Total bicicletas atendidas: ").append(servicio.obtenerTotalAtendidas()).append("\n");
        sb.append("   • Recaudo acumulado: $").append(servicio.obtenerTotalRecaudado()).append(" COP\n");

        txtAreaConsola.setText(sb.toString());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Font fuenteGeneral = new Font("Arial", Font.PLAIN, 13);
            java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof javax.swing.plaf.FontUIResource) {
                    UIManager.put(key, fuenteGeneral);
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel.");
        }

        SwingUtilities.invokeLater(() -> new VentanaGral().setVisible(true));
    }
}