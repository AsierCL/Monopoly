package monopoly.exceptions;

public class CasasCantidad extends PropiedadException {
    public CasasCantidad() {
        super("Debes tener al menos 4 casas.");
    }
}