package monopoly.exceptions;

public class Parametros extends JuegoException {
    public Parametros() {
        super("Error: parámetros insuficientes, inválidos o nulos");
    }
}
