package monopoly.exceptions;

import monopoly.Juego;

public class Tirada extends JuegoException {
    public Tirada() {
        super("Ya no puedes tirar más.");
    }
}

