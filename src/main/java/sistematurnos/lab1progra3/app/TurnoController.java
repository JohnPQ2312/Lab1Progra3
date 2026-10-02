package sistematurnos.lab1progra3.app;

import sistematurnos.lab1progra3.servicio.TurnoService;

public class TurnoController {

    private final TurnoService servicio;

    public TurnoController(TurnoService servicio) {
        this.servicio = servicio;
    }

    public String solicitarTurno() {
        return "Su turno es el "
                + String.format(
                        "%03d",
                        servicio.crearTurno().getNumero()
                );
    }
}