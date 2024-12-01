package monopoly.exceptions;

public class DineroInsuficiente extends MonopolyException {
    public DineroInsuficiente() {
        super("No tienes dinero suficiente.");
    }
}
