package monopoly;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.plaf.TreeUI;

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
        Tablero tablero = new Tablero(banca);
        boolean partida = true;
        while(partida){

            System.out.println("Introduzca comando: ");
            Scanner input = new Scanner(System.in);
            String comando = input.nextLine();

            analizarComando(comando);
            input.close();
        }
    }
    
    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
        // Dividimos el comando en palabras usando el espacio como separador
        String[] palabras = comando.split(" ");

        String accion = palabras[0];  
        String subAccion = (palabras.length > 1) ? palabras[1] : "";
        String parametro = (palabras.length > 2) ? palabras[2] : "";

        switch(accion){ 
            //crear jugador
            case("crear"):  //Dar de alta a un jugador: crear jugador Pedro coche
                break;
            //jugador
            case("jugador"):  //indicar jugador que tiene el turno
                break;
            //listar jugadores / avatares / enventa
            case("listar"):
                switch(subAccion){
                    case("jugadores"):
                        listarJugadores();
                        break;
                    case("avatares"):
                        listarAvatares();
                        break;
                    case("enventa"):
                        listarVenta();
                        break;
                }
                break;
            //lanzar dados
            case("lanzar"):
                lanzarDados();
                break;
            //acabar turno
            case("acabar"):
                acabarTurno();
                break;
            //salir carcel
            case("salir"): //Pagar y salir de la carcel
                salirCarcel();
                break;
            //describir _/jugador/avatar + Badajoz/Maria/M
            case("describir"):
                switch(subAccion){
                    case("jugador"):
                        descJugador(parametro); // no entiendo bien esto
                        break;
                    case("avatar"):
                        descAvatar(parametro);
                        break;
                    case(""): //igual usar default
                        descCasilla(parametro);
                        break;
                    }
                    break;
            //comprar + Mostoles
            case("comprar"):
                comprar(subAccion);
                break;
            //ver tablero
            case("ver"):
                System.out.println(tablero);
                break;

        }
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
        Casilla casilla = tablero.obtenerCasilla(nombre);
        if (casilla != null) {
            System.out.println(casilla.infoCasilla());
        } else {
            System.out.println("No se encontró una casilla con ese nombre.");
        }
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {
        Dado dado1 = new Dado();
        Dado dado2 = new Dado();

        int resultadoDado1 = dado1.hacerTirada();
        int resultadoDado2 = dado2.hacerTirada();
        int resultadoTotal = resultadoDado1 + resultadoDado2;
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {

        Casilla casilla_compra = tablero.obtenerCasilla(nombre);
        Jugador jugador_compra = jugadores.get(turno);

        if(casilla_compra.getDuenho()!=banca){
            System.out.println("No se puede comprar la casilla");
            System.out.println("La casilla pertenece a: " + casilla_compra.getDuenho().getNombre());
        }else if(!casilla_compra.getAvatares().contains(jugador_compra.getAvatar())){
            System.out.println("No se puede comprar la casilla");
            System.out.println("No estás en la casilla que quieres comprar");
        }else{
            casilla_compra.setDuenho(jugador_compra);
            jugador_compra.sumarGastos(casilla_compra.getValor());
        }
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {
        Jugador jugador = jugadores.get(turno);
        if(jugador.getEnCarcel()){
            jugador.setEnCarcel(false);
            //jugador.sumarGastos();  ////VALOR DE SALIR CARCEL
            System.out.println("El jugador "+jugador.getNombre()+" sale de la carcel " + " pagando VALOR"  );///VALOR DE SALIR CARCEL
        }else{
            System.out.println("El jugador " + " no está en la carcel");
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
        for(int i = 0; i<40; i++){
        Casilla casilla = this.tablero.obtenerCasilla(i);
        if(casilla.getDuenho()==banca && 
        casilla.getTipo().equals("solar") || 
        casilla.getTipo().equals("transporte") || 
        casilla.getTipo().equals("servicios"))
        
        {
            System.out.println(this.tablero.obtenerCasilla(i).infoCasilla());
        }

        }
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        for(int i = 1; i<jugadores.size();i++){ //Empeza array en 1 para evitar listar banca
            System.out.println(jugadores.get(i).getNombre());
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        for(int i = 1; i<jugadores.size();i++){ //Empeza array en 1 para evitar listar banca
            System.out.println(jugadores.get(i).getAvatar().getId());
        }
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        this.turno = (this.turno+1)%(jugadores.size()-1);
    }

}
