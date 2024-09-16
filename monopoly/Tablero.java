package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.banca = banca;
        // No hace falta respecificar que es un array de arrays por ¿inferencia de tipos?
        this.posiciones = new ArrayList<>();
        this.grupos = new HashMap<>();
        generarCasillas(); // Para crear las casillas del tablero
    }

    
    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }
    
    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
    ArrayList<Casilla> ladoNorte = new ArrayList<>(); // Creamos un nuevo lado del tablero (sur)

    ladoNorte.add(new Casilla("Parking", "Especial", 20, banca));
    ladoNorte.add(new Casilla("Solar12", "Solar", 21, banca));
    ladoNorte.add(new Casilla("Suerte", "Especial", 22, banca));
    ladoNorte.add(new Casilla("Solar13", "Solar", 23, banca));
    ladoNorte.add(new Casilla("Solar14", "Solar", 24, banca));
    ladoNorte.add(new Casilla("Estacion 3", "Transporte", 25, banca));
    ladoNorte.add(new Casilla("Solar15", "Solar", 26, banca));
    ladoNorte.add(new Casilla("Servicio 2", "Servicios", 28, banca));
    ladoNorte.add(new Casilla("Solar16", "Solar", 27, banca));
    ladoNorte.add(new Casilla("Solar17", "Solar", 29, banca));
    }

    //Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
    ArrayList<Casilla> ladoSur = new ArrayList<>(); // Creamos un nuevo lado del tablero (sur)

    ladoSur.add(new Casilla("Salida", "Especial", 0, banca));
    ladoSur.add(new Casilla("Solar1", "Solar", 1, 600000, banca));
    ladoSur.add(new Casilla("Caja 1", "Especial", 2, banca));
    ladoSur.add(new Casilla("Solar2", "Solar", 3, 600000, banca));
    ladoSur.add(new Casilla("Impuesto 1", "Especial", 4, banca));
    ladoSur.add(new Casilla("Estación 1", "Transporte", 5, banca));
    ladoSur.add(new Casilla("Solar3", "Solar", 6, 520000, banca));
    ladoSur.add(new Casilla("Suerte 1", "Especial", 7, banca));
    ladoSur.add(new Casilla("Solar4", "Solar", 8, 520000, banca));
    ladoSur.add(new Casilla("Solar5", "Solar", 9, 520000, banca));
    }

    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> ladoOeste = new ArrayList<>();
        
        ladoOeste.add(new Casilla("Carcel", "Especial", 10, banca));
        ladoOeste.add(new Casilla("Solar6", "Solar", 11, banca));
        ladoOeste.add(new Casilla("Servicio 1", "Servicios", 12, banca));
        ladoOeste.add(new Casilla("Solar7", "Solar", 13, banca));
        ladoOeste.add(new Casilla("Solar8", "Solar", 14, banca));
        ladoOeste.add(new Casilla("Estacion 2", "Transporte", 15, banca));
        ladoOeste.add(new Casilla("Solar9", "Solar", 16, banca));
        ladoOeste.add(new Casilla("Caja 2", "Especial", 17, banca));
        ladoOeste.add(new Casilla("Solar10", "Solar", 18, banca));
        ladoOeste.add(new Casilla("Solar11", "Solar", 19, banca));
    }
    
    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> ladoEste = new ArrayList<>();

        ladoEste.add(new Casilla("IrCarcel", "Especial", 30, banca));
        ladoEste.add(new Casilla("Solar18", "Solar", 31, banca));
        ladoEste.add(new Casilla("Solar19", "Solar", 32, banca));
        ladoEste.add(new Casilla("Caja 3", "Especial", 33, banca));
        ladoEste.add(new Casilla("Solar20", "Solar", 34, banca));
        ladoEste.add(new Casilla("Estación 4", "Transporte",35, banca));
        ladoEste.add(new Casilla("Suerte 3", "Especial", 36, banca));
        ladoEste.add(new Casilla("Solar21", "Solar", 37, banca));
        ladoEste.add(new Casilla("Impuesto 2", "Especial", 38, banca));
        ladoEste.add(new Casilla("Solar22", "Solar", 39, banca));
    }

    //Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
    }
    
    //Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre){
    }
}
