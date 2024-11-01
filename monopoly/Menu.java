package monopoly;

import java.util.ArrayList;
import java.util.Scanner;

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
    private boolean partida;

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

    public void printAyuda(){
        System.out.println("\nEstos son los posibles comandos: \n");
        System.out.println("crear jugador \"nombre\" \"ficha\"\t-> crea un nuevo jugador");
        System.out.println("jugador \t\t\t-> indica que jugador tiene el turno");
        System.out.println("listar jugadores \t\t-> lista los jugadores creados");
        System.out.println("listar avatares \t\t-> lista los avatares de los jugadores creados");
        System.out.println("listar enventa \t\t\t-> lista las propiedades a la venta");
        System.out.println("lanzar dados \t\t\t-> lanza los dados");
        System.out.println("dados trucados \t\t\t-> permite asignar un valor a cada dado");
        System.out.println("acabar turno \t\t\t-> termina el turno del jugador que esté jugando");
        System.out.println("salir cárcel \t\t\t-> permite pagar y salir de la cárcel");
        System.out.println("describir \"nombre casilla\" \t-> describe la casilla introducida");
        System.out.println("describir jugador \"nombre\" \t-> describe el jugador introducido");
        System.out.println("describir avatar \"nombre\" \t-> describe el avatar introducido");
        System.out.println("comprar \"nombre propiedad\" \t-> permite comprar una propiedad");
        System.out.println("construir \"edificio\" \t\t-> construye un edificio");
        System.out.println("ver tablero \t\t\t-> muestra el tablero");
        System.out.println("? \t\t\t\t-> muestra este menú de ayuda");

        System.out.println("\n");
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
    public void iniciarPartida(Tablero tablero) {
        // Crear un único Scanner
        Scanner input = new Scanner(System.in);
        this.partida = true;

        System.out.println("Introduzca al menos dos jugadores para comenzar\n.");
        System.out.println("Cuando termines introduce \"fin\"");
    
        while (jugadores.size() < 7) {
            System.out.println("Introduzca el nombre del jugador: ");
            String jugador = input.nextLine();  // Usar el mismo Scanner
    
            if (!jugador.equals("fin")) {
                System.out.println("Introduzca la ficha: ");
                String tipoAvatar = input.nextLine();  // Usar el mismo Scanner
    
                Jugador player = new Jugador(jugador, tipoAvatar, tablero.obtenerCasilla(0), avatares);
                jugadores.add(player);
            } else if (jugadores.size() < 2) {
                System.out.println("Debes introducir al menos dos jugadores.");
            } else {
                break;
            }
        }

        printAyuda();
    
        while (partida) {
            System.out.print("Introduzca comando: ");
            String comando = input.nextLine();  // Usar el mismo Scanner
            analizarComando(comando);
        }
    
        // Cerrar el Scanner solo al final
        input.close();
    }
    
    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casillaActual = jugadorActual.getAvatar().getLugar();
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
                    default:
                        System.out.println("Error, introduzca un comando valido");
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
            
            ////////////////DEBUGG/////////////////////
            case("carcel"): //Pagar y salir de la carcel
                jugadores.get(turno).encarcelar(tablero.getPosiciones());
                break;
            ////////////////DEBUGG///////////////////
            //describir _/jugador/avatar + Badajoz/Maria/M
            case("describir"):
                if (subAccion.equals("jugador")) {
                    // Describir un jugador
                    descJugador(palabras);
                } else if (subAccion.equals("avatar")) {
                    // Describir un avatar
                    descAvatar(parametro1);
                } else if (!subAccion.isEmpty()) {
                    // Asumimos que subAccion es el nombre de una casilla
                    descCasilla(subAccion);  // Interpretamos subAccion como nombre de la casilla
                } else {
                    // Si no se recibe una subacción válida
                    System.out.println("Error, introduzca un comando válido");
                }
                break;
            //comprar + Mostoles
            case("comprar"):
                comprar(subAccion);
                break;
            //ver tablero
            case("construir"):
                casillaActual.Construir(jugadorActual, subAccion);
                break;
            case("ver"):
                System.out.println(tablero);
                break;
            case("?"):
                printAyuda();
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
                    if(propiedad.getTipo().equals("Solar")){
                        System.out.println("\t" + "|Casas=" + propiedad.getEdificios().getCasas() + "|Hoteles=" + propiedad.getEdificios().getHoteles() + "|Piscinas=" + propiedad.getEdificios().getPiscinas() + "|Pistas=" + propiedad.getEdificios().getPistas());
                    }
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
        Avatar avatarBuscado = null;
        // Iterar sobre os avatares para encontrar o que coincide co ID.
        for (Avatar avatar : avatares) {
            if (avatar.getId().equals(ID)) {
                avatarBuscado = avatar;
                break;
            }
        }

        if (avatarBuscado == null) {
            System.out.println("Avatar con ID " + ID + " no encontrado.");
        } else {
            System.out.println("ID: " + avatarBuscado.getId());
            System.out.println("Jugador: " + avatarBuscado.getJugador().getNombre());
            System.out.println("Posición actual: " +  avatarBuscado.getLugar().getNombre());
            System.out.println("Tipo: " + avatarBuscado.getTipo());  
        }
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
        if(!tirado){
            Dado dado1 = new Dado();
            Dado dado2 = new Dado();

            int resultadoDado1 = dado1.hacerTirada();
            int resultadoDado2 = dado2.hacerTirada();


            int resultadoTotal = resultadoDado1 + resultadoDado2;
            System.out.println("\nDADOS: [" + resultadoDado1 + "] " + " [" + resultadoDado2 + "]\n");

            Avatar avatarActual = avatares.get(turno);
            Jugador jugadorActual = avatares.get(turno).getJugador();
            ArrayList<ArrayList<Casilla>> casillas = tablero.getPosiciones();
            
            this.lanzamientos++;
            tirado = true;
            if(jugadorActual.getEnCarcel()){// ESTÁ EN CARCEL
                if(resultadoDado1 == resultadoDado2){
                    System.out.println("Sales de la carcel");
                    jugadorActual.setEnCarcel(false);
                    tirado = false;
                    avatarActual.moverAvatar(casillas, resultadoTotal);
                    partida = avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero);
                }else{
                    jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                    
                    if(jugadorActual.getTiradasCarcel() == 3){
                        System.out.println("Te quedan " + (3-jugadorActual.getTiradasCarcel()) + " intentos");
                        salirCarcel();
                        jugadorActual.setTiradasCarcel(0);
                    }else{
                        System.out.println("Sigues en la carcel");
                        System.out.println("Te quedan " + (3-jugadorActual.getTiradasCarcel()) + " intentos");
                    }
                }

            }else{ // NO ESTÄ EN CARCEL
                if(resultadoDado1 == resultadoDado2){
                    System.out.println("LLevas " + this.lanzamientos + " dobles");
                    tirado = false;
                
                    if(this.lanzamientos<3){
                        avatarActual.moverAvatar(casillas, resultadoTotal);
                        partida = avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero);
                        System.out.println("Vuelve a tirar");
                    }else{
                        System.out.println("VAS A LA CARCEL");
                        avatarActual.getJugador().encarcelar(casillas);
                        tirado = true;
                    }
                }else{
                    avatarActual.moverAvatar(casillas, resultadoTotal);
                    partida = avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero);
                }
            }

        }else{//TIRADAS COMPLETADAS
            System.out.println("Ya no puedes tirar más");
        }
    }

    private void dadosTrucados(){

        if(!tirado){
            Scanner scanDado = new Scanner(System.in);

            System.out.print("Introduzca el valor de la tirada del dado 1: ");
            int resultadoDado1 = scanDado.nextInt();
            System.out.print("Introduzca el valor de la tirada del dado 2: ");
            int resultadoDado2 = scanDado.nextInt();
             
            int resultadoTotal = resultadoDado1 + resultadoDado2;
            System.out.println("\nDADOS: [" + resultadoDado1 + "] " + " [" + resultadoDado2 + "]\n");

            Avatar avatarActual = avatares.get(turno);
            Jugador jugadorActual = avatares.get(turno).getJugador();
            ArrayList<ArrayList<Casilla>> casillas = tablero.getPosiciones();
            
            this.lanzamientos++;
            tirado = true;
            if(jugadorActual.getEnCarcel()){// ESTÁ EN CARCEL
                if(resultadoDado1 == resultadoDado2){
                    System.out.println("Sales de la carcel");
                    jugadorActual.setEnCarcel(false);
                    tirado = false;
                    avatarActual.moverAvatar(casillas, resultadoTotal);
                    partida = avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero);
                }else{
                    jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                    
                    if(jugadorActual.getTiradasCarcel() == 3){
                        System.out.println("Te quedan " + (3-jugadorActual.getTiradasCarcel()) + " intentos");
                        salirCarcel();
                        jugadorActual.setTiradasCarcel(0);
                    }else{
                        System.out.println("Sigues en la carcel");
                        System.out.println("Te quedan " + (3-jugadorActual.getTiradasCarcel()) + " intentos");
                    }
                }

            }else{ // NO ESTÄ EN CARCEL
                if(resultadoDado1 == resultadoDado2){
                    System.out.println("LLevas " + this.lanzamientos + " dobles");
                    tirado = false;
                
                    if(this.lanzamientos<3){
                        avatarActual.moverAvatar(casillas, resultadoTotal);
                        partida = avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero);
                        System.out.println("Vuelve a tirar");
                    }else{
                        System.out.println("VAS A LA CARCEL");
                        avatarActual.getJugador().encarcelar(casillas);
                        tirado = true;
                    }
                }else{
                    avatarActual.moverAvatar(casillas, resultadoTotal);
                    partida = avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero);
                }
            }

        }else{//TIRADAS COMPLETADAS
            System.out.println("Ya no puedes tirar más");
        }
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        Jugador jugador_actual = jugadores.get(turno);
        Casilla casilla_compra = tablero.obtenerCasilla(nombre);

        if(casilla_compra != null && jugador_actual != null){
            casilla_compra.comprarCasilla(jugador_actual, banca);
        }else{
            System.out.println("Casilla no encontrada");
        }
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {
        Jugador jugador = jugadores.get(turno);
        if(jugador.getEnCarcel()){
            jugador.setEnCarcel(false);
            jugador.sumarGastos(Valor.SALIR_CARCEL);
            System.out.println("El jugador "+jugador.getNombre()+" sale de la carcel " + " pagando " + Valor.SALIR_CARCEL);
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
        if(tirado){
            this.turno = (this.turno+1)%(jugadores.size());
            this.tirado = false;
            this.lanzamientos = 0;
        }else{
            System.out.println("Debes tirar antes");
        }
    }

}
