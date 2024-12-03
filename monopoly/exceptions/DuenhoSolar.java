package monopoly.exceptions;


public class DuenhoSolar extends MonopolyException {
    public DuenhoSolar() {
        super("Debes ser dueño de todo el solar o haber caído 2 veces en la casilla.");
    }
}
