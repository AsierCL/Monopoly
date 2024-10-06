package monopoly;


/* public class Valor {
    //Se incluyen una serie de constantes útiles para no repetir valores.
    public static final float FORTUNA_BANCA = 50000;
    public static final float FORTUNA_INICIAL = 14000000;
    public static final float SUMA_VUELTA = 1800000; //Se aproxima a la media de los precios de los solares del tablero.
    
    //Colores del texto:
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

} */

public class Valor {
    //Se incluyen una serie de constantes útiles para no repetir valores.
    public static final float FORTUNA_BANCA = 500000000.0f;
    public static final float FORTUNA_INICIAL = 9543076.28f; // Cantidad que recibe cada jugador al comenzar la partida
    public static final float SUMA_VUELTA = 1301328.584f; // Cantidad que recibe un jugador al pasar pos la Salida
    public static final float SALIR_CARCEL = SUMA_VUELTA * 0.25f; // Cantidad que paga un jugador por salir de la carcel
    
    //Colores del texto:
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[40m";
    public static final String RED = "\u001B[41m";
    public static final String GREEN = "\u001B[42m";
    public static final String YELLOW = "\u001B[48;5;228m";
    public static final String BLUE = "\u001B[44m";
    public static final String PURPLE = "\u001B[45m";
    public static final String CYAN = "\u001B[46m";
    public static final String WHITE = "\u001B[47m";
    public static final String BROWN = "\u001B[43m";

}