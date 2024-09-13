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
    }

    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
    }

    //Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
    }
    
    //Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre){
    }
}
