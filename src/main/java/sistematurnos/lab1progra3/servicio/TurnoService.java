package sistematurnos.lab1progra3.servicio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import sistematurnos.lab1progra3.modelo.EstadoTurno;
import sistematurnos.lab1progra3.modelo.Turno;
import sistematurnos.lab1progra3.persistencia.TurnoRepository;

public class TurnoService {

    private final TurnoRepository repositorio;

    public TurnoService(TurnoRepository repositorio) {
        this.repositorio = Objects.requireNonNull(
                repositorio, "El repositorio es obligatorio"
        );
    }

    public Turno crearTurno() {
        int siguiente = repositorio.listar().stream()
                .mapToInt(Turno::getNumero)
                .max()
                .orElse(0) + 1;

        return registrar(siguiente);
    }

    public Turno registrar(int numero) {
        if (repositorio.buscarPorNumero(numero).isPresent()) {
            throw new IllegalStateException(
                    "Ya existe el turno " + numero
            );
        }

        Turno nuevo = new Turno(
                numero,
                EstadoTurno.PENDIENTE,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        );

        repositorio.guardar(nuevo);
        return nuevo;
    }

    public Optional<Turno> llamarSiguiente() {
        Optional<Turno> siguiente = listarPendientes()
                .stream()
                .findFirst();

        siguiente.ifPresent(
                turno -> cambiarEstado(turno, EstadoTurno.LLAMADO)
        );

        return siguiente;
    }

    public Turno atender(int numero) {
        Turno turno = repositorio.buscarPorNumero(numero)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el turno " + numero
                ));

        if (turno.getEstado() != EstadoTurno.LLAMADO) {
            throw new IllegalStateException(
                    "El turno " + numero + " no ha sido llamado"
            );
        }

        cambiarEstado(turno, EstadoTurno.ATENDIDO);
        return turno;
    }

    public List<Turno> listarPendientes() {
        return repositorio.listar().stream()
                .filter(turno ->
                        turno.getEstado() == EstadoTurno.PENDIENTE
                )
                .sorted(
                        Comparator.comparing(Turno::getFechaHora)
                                .thenComparingInt(Turno::getNumero)
                )
                .toList();
    }

    public List<Turno> listarTodos() {
        return repositorio.listar();
    }

    public Optional<Turno> buscar(int numero) {
        return repositorio.buscarPorNumero(numero);
    }

    private void cambiarEstado(Turno turno, EstadoTurno nuevoEstado) {
        turno.setEstado(nuevoEstado);
        repositorio.actualizar(turno);
    }
}