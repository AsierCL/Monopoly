package monopoly.exceptions;

public class LimitacionPista extends PropiedadException {
    public LimitacionPista() {
        super("Debes tener, como mínimo, 1 hotel y 2 casas");
    } 
}
