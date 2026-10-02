package sistematurnos.lab1progra3.app;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import sistematurnos.lab1progra3.persistencia.PersistenciaException;

/** Sección 8: la ventana delega en el controlador; no lee ni escribe archivos. */
public class VentanaTurnos extends JFrame {

    private final TurnoController controller;
    private final JLabel lblMensaje = new JLabel(
            "Presione el botón para solicitar un turno", SwingConstants.CENTER);
    private final JButton btnSolicitar = new JButton("Solicitar turno");

    public VentanaTurnos(TurnoController controller) {
        super("Sistema de turnos");
        this.controller = controller;

        JPanel contenido = new JPanel(new BorderLayout(15, 15));
        contenido.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        contenido.add(lblMensaje, BorderLayout.CENTER);
        contenido.add(btnSolicitar, BorderLayout.SOUTH);
        setContentPane(contenido);

        btnSolicitar.addActionListener(this::btnSolicitarActionPerformed);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void btnSolicitarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            lblMensaje.setText(controller.solicitarTurno());
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el turno: " + e.getMessage(),
                    "Error de almacenamiento", JOptionPane.ERROR_MESSAGE);
        }
    }
}
