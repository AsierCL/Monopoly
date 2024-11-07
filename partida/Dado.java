package partida;
import java.util.Random;



public class Dado {
    //El dado solo tiene un atributo en nuestro caso: su valor.
    private int valor;

    // Instancia de Random para generar números aleatorios.
    private Random random;

    // Constructor para inicializar la instancia de Random.
    public Dado() {
        this.random = new Random();
    }

    // Método para simular el lanzamiento del dado.
    public int hacerTirada() {
        this.valor = random.nextInt(6) + 1;
        return this.valor;
    }

}