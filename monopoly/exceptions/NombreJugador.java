package monopoly.exceptions;

public class NombreJugador extends JuegoException {
    public NombreJugador() {
        super("No existe un jugador con este nombre");
    }
}
