package monopoly.exceptions;

public class LimitacionPiscina extends PropiedadException {
    public LimitacionPiscina() {
        super("Debes tener, como mínimo, 1 hotel y 2 casas");
    } 
}