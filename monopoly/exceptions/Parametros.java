package monopoly.exceptions;

import monopoly.Juego;

public class Parametros extends JuegoException {
    public Parametros() {
        super("Error: parámetros insuficientes, inválidos o nulos");
    }
}
