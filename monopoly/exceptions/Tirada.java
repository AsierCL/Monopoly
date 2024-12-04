package monopoly.exceptions;

public class Tirada extends JuegoException {
    public Tirada() {
        super("Ya no puedes tirar más.");
    }
}

