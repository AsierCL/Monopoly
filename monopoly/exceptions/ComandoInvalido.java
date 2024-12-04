package monopoly.exceptions;

public class ComandoInvalido extends JuegoException {
    public ComandoInvalido() {
        super("Error: introduce un comando válido.");
    }
}
