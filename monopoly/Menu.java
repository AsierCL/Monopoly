package monopoly;

import java.util.ArrayList;
import partida.*;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Linux")) {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printBanner(){
        String banner = " /$$      /$$                                                   /$$                 /$$$$$$$$ /$$                       \n"
        + "| $$$    /$$$                                                  | $$                | $$_____/| $$                       \n"
        + "| $$$$  /$$$$  /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$ | $$ /$$   /$$      | $$     /$$$$$$   /$$$$$$$  /$$$$$$ \n"
        + "| $$ $$/$$ $$ /$$__  $$| $$__  $$ /$$__  $$ /$$__  $$ /$$__  $$| $$| $$  | $$      | $$$$$ |_  $$_/  /$$_____/ /$$__  $$\n"
        + "| $$  $$$| $$| $$  \\ $$| $$  \\ $$| $$  \\ $$| $$  \\ $$| $$  \\ $$| $$| $$  | $$      | $$__/   | $$   |  $$$$$$ | $$$$$$$$\n"
        + "| $$\\  $ | $$| $$  | $$| $$  | $$| $$  | $$| $$  | $$| $$  | $$| $$| $$  | $$      | $$      | $$ /$$\\____  $$| $$_____/\n"
        + "| $$ \\/  | $$|  $$$$$$/| $$  | $$|  $$$$$$/| $$$$$$$/|  $$$$$$/| $$|  $$$$$$$      | $$$$$$$$|  $$$$//$$$$$$$/|  $$$$$$$\n"
        + "|__/     |__/ \\______/ |__/  |__/ \\______/ | $$____/  \\______/ |__/ \\____  $$      |________/ \\___/ |_______/  \\_______/\n"
        + "                                           | $$                     /$$  | $$                                           \n"
        + "                                           | $$                    |  $$$$$$/                                           \n"
        + "                                           |__/                     \\______/                                            \n";

        System.out.println(banner);

        try {
            Thread.sleep(2000); // 2000 milisegundos = 2 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Menu(){
        clearScreen();
        Jugador banca = new Jugador();
        Tablero tablero = new Tablero(banca);
        this.avatares = new ArrayList<>();
        Jugador player1 = new Jugador("Maduro", "esfinge", tablero.obtenerCasilla(0), avatares);
        //Menu start = new Menu();
        //start.iniciarPartida(tablero);
        //Menu.printBanner();
        //Menu.clearScreen();
        System.out.println(tablero);
        player1.getAvatar().moverAvatar(tablero.getPosiciones(), 13);
        System.out.println(tablero);
        
    }

    
    // Método para inciar una partida: crea los jugadores y avatares.
    public void iniciarPartida(Tablero tablero) {
        this.avatares = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        Jugador Player1 = new Jugador("Player1", "Coche", tablero.getPosiciones().get(0).get(0), avatares);
        jugadores.add(Player1);
    }
    
    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    private void descCasilla(String nombre) {
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        if(tablero.obtenerCasilla(nombre).getDuenho()!=banca){
            System.out.println("No se puede comprar la casilla");
            System.out.println("La casilla pertenece a: " + tablero.obtenerCasilla(nombre).getDuenho().getNombre());
        }else if(!tablero.obtenerCasilla(nombre).getAvatares().contains(jugadores.get(turno).getAvatar())){
            System.out.println("No se puede comprar la casilla");
            System.out.println("No estás en la casilla que quieres comprar");
        }else{
            tablero.obtenerCasilla(nombre).setDuenho(jugadores.get(turno));
            jugadores.get(turno).sumarGastos(tablero.obtenerCasilla(nombre).getValor());
        }
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {
        jugadores.get(turno).setEnCarcel(false);
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
    }

}
