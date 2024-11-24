package monopoly.exceptions;

public class PropiedadNoPerteneceAJugadorException extends PropiedadException {
    public PropiedadNoPerteneceAJugadorException() {
        super("La propiedad no pertenece al jugador.");
    }
}
