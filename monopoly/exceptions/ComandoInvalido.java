package monopoly.exceptions;

import monopoly.interfaces.Comando;

public class ComandoInvalido extends JuegoException {
    public ComandoInvalido() {
        super("Error: introduce un comando válido.");
    }
}
