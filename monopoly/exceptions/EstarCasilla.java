package monopoly.exceptions;

public class EstarCasilla extends PropiedadException {
    public EstarCasilla() {
        super("Debes estar en la casilla para ejecutar esta acción.");
    }
}
