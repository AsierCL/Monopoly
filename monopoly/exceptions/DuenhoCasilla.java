package monopoly.exceptions;

public class DuenhoCasilla extends MonopolyException {
    public DuenhoCasilla() {
        super("No eres propietario de la casilla.");
    }
}
