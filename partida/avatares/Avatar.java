package partida.avatares;

import monopoly.*;
import monopoly.casillas.Casilla;
import partida.Jugador;
import monopoly.Tablero;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public abstract class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.
    private boolean modo; //Modo de movimiento avanzado

    public boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    public int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    public int ultimatirada;
    public int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    
    public static final Set<String> avataresValidos = Set.of("esfinge", "sombrero", "coche", "pelota");
    
    //Constructor vacío
    public Avatar() {
    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        this.modo = false;
        lugar.anhadirAvatar(this);

        /* // Generamos ID
        Random random = new Random();
        this.id = String.valueOf((char) (random.nextInt(26) + 'A')); */

        generarId(avCreados);
    }

    public Casilla getLugar() {
        return lugar;
    }

    public void setLugar(Casilla lugar) {
        this.lugar = lugar;
    }

    
    public String getId() {
        return id;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public String getTipo(){
        return tipo;
    }

    public boolean getModo(){
        return modo;
    }

    public void setModo(boolean modo){
        this.modo = modo;
    }

    /*Método que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        //Creamos as variables e os obxetos necesarios
        Random random = new Random();
        boolean idDuplicado = true;
        
        //Si é o primeiro elemento da lista, non e necesario chequear nada
        if (avCreados.size()==0) {
            this.id = String.valueOf((char) (random.nextInt(26) + 'A'));
        }else{ //Mentres o ID sea igual a algun dos que xa hai na lista, repetir o proceso

            while (idDuplicado) {
                this.id = String.valueOf((char) (random.nextInt(26) + 'A'));
                //Se ningún coincide, salimos do bucle while
                idDuplicado = false;
                //Comparamos con todos os elementos da lista excepto si mismo
                for(int i=0; i < avCreados.size();i++){
                    if(this.id.equals(avCreados.get(i).id)){
                        idDuplicado = true;
                    }
                }
            }
        }
        //Añadimos o avatar a lista de avatares
        avCreados.add(this);
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siempre es positivo.
     */
    private void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada, Tablero tablero) {
        if(!this.getJugador().getEnCarcel()){
            int posicionActual = lugar.getPosicion();
            Casilla casillaActual = tablero.obtenerCasilla(posicionActual);
            int posicionNueva = (posicionActual + valorTirada)%40;

            if (posicionNueva < 0) { 
                posicionNueva += 40; // Si el resultado es negativo, lo ajustamos sumando 40
            }

            Casilla nuevaCasilla = tablero.obtenerCasilla(posicionNueva);
            if(valorTirada >= 0) this.getJugador().cobrarPasoPorSalida(casillaActual, nuevaCasilla);
            else this.getJugador().PasoPorSalidaInverso(casillaActual, nuevaCasilla);

            lugar.eliminarAvatar(this);
            this.lugar = nuevaCasilla;
            nuevaCasilla.anhadirAvatar(this);
            Juego.consola.print("El jugador " + this.jugador.getNombre() + " se mueve " + valorTirada + " casillas,\nde " + casillaActual.getNombre() + " a " + nuevaCasilla.getNombre());
        } else {
            Juego.consola.print("El jugador no avanza porque está en la cárcel");
        }
        
    }

    // Métodos para movimiento
    public void moverEnBasico(ArrayList<ArrayList<Casilla>> casillas, int valorTirada, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores){
        moverAvatar(casillas, valorTirada, tablero); // Comportamiento estándar
    }

    // Método abstracto, obliga a las subclases a implementarlo
    //public abstract boolean moverEnAvanzado(ArrayList<ArrayList<Casilla>> casillas, int valorTirada, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores);

    public abstract int moverEnAvanzado(int resultadoTotal, int faltaPorMover, ArrayList<ArrayList<Casilla>> casillas, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores);
}