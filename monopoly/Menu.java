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

    }

    public Menu(){
        this.avatares = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        banca = new Jugador();
        tablero = new Tablero(banca);
        
        clearScreen();
        printBanner();

        iniciarPartida(tablero);
    }

    
    // Método para inciar una partida: crea los jugadores y avatares.
    public void iniciarPartida(Tablero tablero) 
    {
        Scanner input = new Scanner(System.in);
        boolean partida = true;
        while(partida){

            System.out.print("Introduzca comando: ");
            String comando = input.nextLine();

            analizarComando(comando);
            
        }
        input.close();
    }
    
    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
        // Dividimos el comando en palabras usando el espacio como separador
        String[] palabras = comando.split(" ");

        String accion = palabras[0];  
        String subAccion = (palabras.length > 1) ? palabras[1] : "";
        String parametro1 = (palabras.length > 2) ? palabras[2] : "";
        String parametro2 = (palabras.length > 3) ? palabras[3] : "";

        switch(accion){ 
            //crear jugador
            case("crear"):  //Dar de alta a un jugador: crear jugador Pedro coche
                Jugador player = new Jugador(parametro1, parametro2, tablero.obtenerCasilla(0),avatares);
                jugadores.add(player);

                System.out.println("\n{");
                System.out.print("Nombre: ");System.out.println(player.getNombre());
                System.out.print("Avatar: ");System.out.println(player.getAvatar().getId());
                System.out.println("}\n");

                break;
            //jugador
            case("jugador"):  //indicar jugador que tiene el turno
                verTurno();

                System.out.println("\n{");
                System.out.print("Nombre: "); System.out.println(jugadores.get(turno).getNombre());
                System.out.print("Avatar: "); System.out.println(jugadores.get(turno).getAvatar().getId());
                System.out.println("}\n");

                ///// verTurno(); ?¿?¿?¿?¿?¿?

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
            case("dados"):
                dadosTrucados();
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
                        descJugador(palabras);
                        break;
                    case("avatar"):
                        descAvatar(parametro1);
                        break;
                    case(""): //igual usar default
                        descCasilla(parametro1);
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
            default:
                System.out.println("Error, introduzca un comando valido");
                break;
        }
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
        
        // Supoñendo que o nombre do jugador está en partes[1].
        String nombreJugador = partes[2]; 
        Jugador jugador = buscarJugadorPorNombre(nombreJugador);
        
        if (jugador != null) {
            System.out.println("Nombre del Jugador: " + jugador.getNombre());
            System.out.println("Fortuna: €" + jugador.getFortuna());
            
            if (!jugador.getPropiedades(jugador).isEmpty()) {
                System.out.println("Propiedades: ");
                for (Casilla propiedad : jugador.getPropiedades(jugador)) {
                    System.out.println(" - " + propiedad.getNombre());
                }
            } else {
                System.out.println("El jugador no tiene propiedades.");
            }
    
            Avatar avatar = jugador.getAvatar();
            if (avatar != null) {
                System.out.println("Avatar: " + avatar.getId());
            } else {
                System.out.println("El jugador no tiene un avatar asignado.");
            
            }
        

        // Hipotecas.
        // Edificios.
        } else {
            System.out.println("No existe un jugador con este nombre.");
        }
    }

    public Jugador buscarJugadorPorNombre(String nombre) {
        for (Jugador jugador : jugadores) {  
            if (jugador.getNombre().equals(nombre)) {  // .equalsIgnoreCase(nombre.trim())
                return jugador;
            }
        }

        return null;
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

        Avatar avatarActual = avatares.get(turno);
        ArrayList<ArrayList<Casilla>> casillas = tablero.getPosiciones(); // Asumindo que Tablero ten este método

        avatarActual.moverAvatar(casillas, resultadoTotal); 
    }

    private void dadosTrucados(){
        Scanner scanDado = new Scanner(System.in);

        System.out.print("Introduzca el valor de la tirada del dado 1: ");
        int resultadoDado1 = scanDado.nextInt();
        System.out.print("Introduzca el valor de la tirada del dado 2: ");
        int resultadoDado2 = scanDado.nextInt();
         
        int resultadoTotal = resultadoDado1 + resultadoDado2;

        Avatar avatarActual = avatares.get(turno);
        ArrayList<ArrayList<Casilla>> casillas = tablero.getPosiciones(); // Asumindo que Tablero ten este método
        avatarActual.moverAvatar(casillas, resultadoTotal); 
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
        casilla.getTipo().equals("Solar") || 
        casilla.getTipo().equals("Transporte") || 
        casilla.getTipo().equals("Servicios"))
        
        {
            System.out.println(this.tablero.obtenerCasilla(i).infoCasilla());
        }

        }
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        for(int i = 0; i<jugadores.size();i++){ //Empeza array en 1 para evitar listar banca
            System.out.println(jugadores.get(i).getNombre());
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        for(int i = 0; i<jugadores.size();i++){ //Empeza array en 1 para evitar listar banca
            System.out.println(jugadores.get(i).getAvatar().getId());
        }
    }

    // Método que muestra el nombre y el avatar del jugador que tiene el turno
    private void verTurno() {
        if(!jugadores.isEmpty()){
            System.out.println("El turno es de: " + jugadores.get(turno).getNombre());
        }
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        this.turno = (this.turno+1)%(jugadores.size());
    }

}
