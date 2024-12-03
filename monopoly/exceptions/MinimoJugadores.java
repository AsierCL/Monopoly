package monopoly.exceptions;

public class MinimoJugadores extends JuegoException {
    public MinimoJugadores() {
        super("Se deben introducir al menos 2 jugadores.");
    }
}

