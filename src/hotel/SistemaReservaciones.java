package hotel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SistemaReservaciones extends JFrame {

    // Contenedor principal con CardLayout para cambiar entre pantallas
    private CardLayout cardLayout;
    private JPanel mainContainer;

    // Componentes del Formulario de Reservaciones
    private JTextField txtNombreHuesped;
    private JTextField txtNumHabitacion;
    private JTextField txtFechaEntrada;
    private JTextField txtFechaSalida;
    private JTextField txtCostoEstancia;

    // Datos del alumno y módulo asignado
    private final String NOMBRE_ALUMNO = "ES17200419"; // Sustituye con tu nombre completo
    private final String MODULO_ASIGNADO = "Módulo de Reservaciones Hotel";

    public SistemaReservaciones() {
        // Configuración de la Ventana Principal
        setTitle(MODULO_ASIGNADO + " - " + NOMBRE_ALUMNO);
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Construcción de Vistas
        mainContainer.add(crearPanelInicio(), "Inicio");
        mainContainer.add(crearPanelFormulario(), "Formulario");

        add(mainContainer);
    }

    /**
     * Vista 1: Pantalla de Inicio / Menú Principal
     */
    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Encabezado
        JLabel lblTitulo = new JLabel("Sistema de Reservaciones de Hotel", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Centro: Imagen o representación gráfica del módulo
        JLabel lblImagen = new JLabel("🏨 Imagen Alusiva al Hotel", JLabel.CENTER);
        lblImagen.setFont(new Font("Arial", Font.ITALIC, 16));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.add(lblImagen, BorderLayout.CENTER);

        // Sur: Botones del Menú Principal
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnModulo = new JButton("Acceder al Módulo de Reservaciones");
        JButton btnSalir = new JButton("Salir");

        btnModulo.addActionListener((ActionEvent e) -> cardLayout.show(mainContainer, "Formulario"));
        btnSalir.addActionListener((ActionEvent e) -> System.exit(0));

        panelBotones.add(btnModulo);
        panelBotones.add(btnSalir);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Vista 2: Formulario del Módulo de Reservaciones
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblSubtitulo = new JLabel("Captura de Reservación de Huésped", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblSubtitulo, BorderLayout.NORTH);

        // Formulario central
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));

        panelForm.add(new JLabel("Nombre del Huésped:"));
        txtNombreHuesped = new JTextField();
        panelForm.add(txtNombreHuesped);

        panelForm.add(new JLabel("Número de Habitación:"));
        txtNumHabitacion = new JTextField();
        panelForm.add(txtNumHabitacion);

        panelForm.add(new JLabel("Fecha de Entrada (DD/MM/AAAA):"));
        txtFechaEntrada = new JTextField();
        panelForm.add(txtFechaEntrada);

        panelForm.add(new JLabel("Fecha de Salida (DD/MM/AAAA):"));
        txtFechaSalida = new JTextField();
        panelForm.add(txtFechaSalida);

        panelForm.add(new JLabel("Costo de Estancia ($):"));
        txtCostoEstancia = new JTextField();
        panelForm.add(txtCostoEstancia);

        panel.add(panelForm, BorderLayout.CENTER);

        // Botones del Formulario
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnRegresar = new JButton("Regresar al Menú Principal");

        btnGuardar.addActionListener((ActionEvent e) -> guardarReservacion());
        btnRegresar.addActionListener((ActionEvent e) -> cardLayout.show(mainContainer, "Inicio"));

        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnRegresar);
        panel.add(panelAcciones, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Lógica del Botón Guardar: Validación y Almacenamiento en Archivo de Texto (.txt)
     */
    private void guardarReservacion() {
        String nombre = txtNombreHuesped.getText().trim();
        String habitacion = txtNumHabitacion.getText().trim();
        String entrada = txtFechaEntrada.getText().trim();
        String salida = txtFechaSalida.getText().trim();
        String costo = txtCostoEstancia.getText().trim();

        // Verificación de campos vacíos
        if (nombre.isEmpty() || habitacion.isEmpty() || entrada.isEmpty() || salida.isEmpty() || costo.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Error: Todos los campos son obligatorios para guardar la reservación.", 
                "Campo Vacío", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Estructura de la reservación a escribir en el archivo .txt
        String registro = String.format("Huésped: %s | Habitación: %s | Entrada: %s | Salida: %s | Costo: $%s",
                nombre, habitacion, entrada, salida, costo);

        // Flujo de salida mediante FileWriter y BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservaciones.txt", true))) {
            writer.write(registro);
            writer.newLine();

            JOptionPane.showMessageDialog(this, 
                "¡Reservación guardada con éxito en 'reservaciones.txt'!\n\nDatos ingresados:\n" + registro, 
                "Confirmación", JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, 
                "Ocurrió un error al guardar los datos en el archivo: " + ex.getMessage(), 
                "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombreHuesped.setText("");
        txtNumHabitacion.setText("");
        txtFechaEntrada.setText("");
        txtFechaSalida.setText("");
        txtCostoEstancia.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaReservaciones().setVisible(true);
        });
    }
}
