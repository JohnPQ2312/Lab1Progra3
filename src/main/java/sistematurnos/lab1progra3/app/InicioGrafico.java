package sistematurnos.lab1progra3.app;

import java.nio.file.Path;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import sistematurnos.lab1progra3.persistencia.ArchivoTurnoRepository;
import sistematurnos.lab1progra3.persistencia.PersistenciaException;
import sistematurnos.lab1progra3.persistencia.TurnoRepository;
import sistematurnos.lab1progra3.servicio.TurnoService;

/** Arma las piezas de la demostración gráfica de la sección 8. */
public class InicioGrafico {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                TurnoRepository repositorio = new ArchivoTurnoRepository(
                        Path.of("data", "turnos.txt"));
                TurnoService servicio = new TurnoService(repositorio);
                TurnoController controller = new TurnoController(servicio);
                new VentanaTurnos(controller).setVisible(true);
            } catch (PersistenciaException e) {
                JOptionPane.showMessageDialog(null,
                        "No se pudo iniciar el almacenamiento: " + e.getMessage(),
                        "Error de almacenamiento", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
