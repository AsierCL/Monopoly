package partida;

import monopoly.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.
    
    
    public static final Set<String> tiposValidos = Set.of("esfinge", "sombrero", "coche", "pelota");
    
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

        /* // Generamos ID
        Random random = new Random();
        this.id = String.valueOf((char) (random.nextInt(26) + 'A')); */

        generarId(avCreados);
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
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
                for(int i=0; i < avCreados.size()-1;i++){
                    if(this.id == avCreados.get(i).id){
                        idDuplicado = true;
                    }
                }
            }
        }
        //Añadimos o avatar a lista de avatares
        avCreados.add(this);
    }
}
