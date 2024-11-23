package monopoly.edificios;
import java.util.Set;

public abstract class Edificio {
    public static final Set<String> edificiosValidos = Set.of("casa", "hotel", "piscina", "pista");
    
    public Edificio() {
        
    }
}
