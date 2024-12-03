package monopoly.exceptions;

public class CasillaNombre extends JuegoException {
    public CasillaNombre() {
        super("No existe una casilla con este nombre");
    }
}
