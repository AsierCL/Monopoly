package monopoly.exceptions;

public class EvaluarCasilla extends MonopolyException {
    public EvaluarCasilla() {
        super("Dinero insuficiente para pagar, vende propiedades o declárate en bancarrota.");
    }   
}
