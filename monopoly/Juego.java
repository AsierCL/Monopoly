package monopoly;

import java.util.ArrayList;
import java.util.List;

import monopoly.casillas.Casilla;
import monopoly.casillas.Propiedades.*;
import monopoly.interfaces.ConsolaNormal;
import monopoly.interfaces.Consola;
import partida.*;
import partida.Tratos.Tratos;
import partida.avatares.Avatar;
import monopoly.interfaces.Comando;

public class Juego implements Comando {

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
    private int ultimatirada;
    private Tratos tratos;

    public static Consola consola = new ConsolaNormal();

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

        consola.print(banner);
    }

    public void printAyuda(){
        consola.print("\nEstos son los posibles comandos: \n");
        consola.print("crear jugador \"nombre\" \"ficha\"\t-> crea un nuevo jugador");
        consola.print("jugador \t\t\t-> indica que jugador tiene el turno");
        consola.print("listar jugadores \t\t-> lista los jugadores creados");
        consola.print("listar avatares \t\t-> lista los avatares de los jugadores creados");
        consola.print("listar enventa \t\t\t-> lista las propiedades a la venta");
        consola.print("listar edificios \t\t-> lista los edificios construidos");
        consola.print("lanzar dados \t\t\t-> lanza los dados");
        consola.print("dados trucados \t\t\t-> permite asignar un valor a cada dado");
        consola.print("acabar turno \t\t\t-> termina el turno del jugador que esté jugando");
        consola.print("salir cárcel \t\t\t-> permite pagar y salir de la cárcel");
        consola.print("describir \"nombre casilla\" \t-> describe la casilla introducida");
        consola.print("describir jugador \"nombre\" \t-> describe el jugador introducido");
        consola.print("describir avatar \"nombre\" \t-> describe el avatar introducido");
        consola.print("comprar \"nombre propiedad\" \t-> permite comprar una propiedad");
        consola.print("hipotecar \"nombre casilla\" \t-> permite hipotecar una propiedad");
        consola.print("deshipotecar \"nombre casilla\" \t-> permite deshipotecar una propiedad");
        consola.print("bancarrota \"nombre jugador\" \t-> declararse en bancarrota");
        consola.print("construir \"edificio\" \t\t-> construye un edificio");
        consola.print("vender \"edificio , solar, cantidad\" \t-> permite vender edificios");
        consola.print("ver tablero \t\t\t-> muestra el tablero");
        consola.print("estadisticas \t\t\t-> muestra estadisticas partida");
        consola.print("estadisticas \"nombre jugador\" \t-> muestra estadisticas partida");
        consola.print("crear trato  \t\t\t\t-> crea un nuevo trato");
        consola.print("listar tratos  \t\t\t\t-> lista los tratos actuales");
        consola.print("? \t\t\t\t-> muestra este menú de ayuda");

        consola.print("\n");
    }

    public Juego(){
        this.avatares = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        banca = new Jugador();
        tablero = new Tablero(banca);
        banca.InicializarBanca(tablero);
        this.tratos = new Tratos();
        dado1 = new Dado();
        dado2 = new Dado();

        clearScreen();
        printBanner();

        iniciarPartida(tablero);
    }


    // Método para inciar una partida: crea los jugadores y avatares.
    public void iniciarPartida(Tablero tablero) {
        this.partida = true;

        consola.print("Introduzca al menos dos jugadores para comenzar\n.");
        consola.print("Cuando termines introduce \"fin\"");

        while (jugadores.size() < 7) {
            String jugador = Juego.consola.read("Introduzca el nombre del jugador: ");

            if (!jugador.equals("fin")) {
                String tipoAvatar = Juego.consola.read("Introduzca la ficha: ");

                Jugador player = new Jugador(jugador, tipoAvatar, tablero.obtenerCasilla(0), avatares);
                if(player.getAvatar()!=null){
                    jugadores.add(player);
                }
            } else if (jugadores.size() < 2) {
                consola.print("Debes introducir al menos dos jugadores.");
            } else {
                break;
            }
        }

        printAyuda();

        while (partida) {
            String comando = Juego.consola.read("Introduzca comando: ");
            analizarComando(comando);
        }
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

        switch(accion){
            //jugador
            case("jugador"):  //indicar jugador que tiene el turno
                verTurno();
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
                    case("edificios"):
                        if(parametro1.equals("")){
                            listarEdificios();
                        }else if(Grupo.coloresValidos.contains(parametro1)){
                            listarEdificiosPorColor(parametro1);
                        }else{
                            consola.print("Error, color invalido");
                        }
                        break;
                    case("tratos"):
                        tratos.listarTratos();
                        break;
                    default:
                        consola.print("Error, introduzca un comando valido");
                        break;
                }
                break;
            case("crear"):
                if(!subAccion.equals("trato"))
                    break;
                tratos.proponerTrato(jugadorActual, jugadores, tablero);
                break;
            case("aceptar"):
                if(!subAccion.equals("trato"))
                    break;
                tratos.aceptarTrato(jugadorActual);
                break;
            case("rechazar"):
                if(!subAccion.equals("trato"))
                    break;
                tratos.rechazarTrato(jugadorActual);
                break;
            //lanzar dados
            case("lanzar"):
                lanzarDados(false);
                consola.print(tablero.toString());;
                break;
            case("dados"):
                lanzarDados(true);
                consola.print(tablero.toString());;
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
                    consola.print("Error, introduzca un comando válido");
                }
                break;
            // hipotecar + Casilla
            case("hipotecar"):
                hipotecar(subAccion);
                break;
            // deshipotecar + Casilla
            case("deshipotecar"):
                deshipotecar(subAccion);
                break;
            case("bancarrota"):
                bancarrota(subAccion);
                break;
            //comprar + Mostoles
            case("comprar"):
                comprar(subAccion);
                break;
            case("vender"):
                vender(palabras);
                break;
            //ver tablero
            case("construir"):
                if(casillaActual instanceof Solar){
                    ((Solar)casillaActual).Construir(jugadorActual, subAccion, jugadores);
                }else{
                    consola.print("No puedes construir aqui");
                }
                break;
            case("ver"):
                consola.print(tablero.toString());;
                break;
            case("estadisticas"):
                if(subAccion.isEmpty()){ //Estadísticas del juego
                    mostrarEstadisticasJuego();
                } else { //Estadísticas del jugador
                    mostrarEstadisticasJugadorPorNombre(subAccion);
                }
                break;
            case("cambiar"):
                cambiarModo(jugadores.get(turno).getAvatar());
                break;
            case("?"):
                printAyuda();
                break;
            default:
                consola.print("Error, introduzca un comando valido\n");
                break;
        }
    }

    public boolean turnoIntermedio(Avatar avatarActual, Casilla casillaActual, boolean haComprado) {
        Jugador jugador = avatarActual.getJugador();
        boolean turnoActivo = true;

        while (turnoActivo) {
            // Mostrar el menú de opciones disponibles
            consola.print("TURNO INTERMEDIO");
            consola.print("Para salir \"continuar\".");
            String opcion = Juego.consola.read("Seleccione una opción: ");

            String[] palabras = opcion.split(" ");

            String accion = palabras[0];
            String subAccion = (palabras.length > 1) ? palabras[1] : "";
            String parametro1 = (palabras.length > 2) ? palabras[2] : "";

            switch (accion) {
                case("jugador"):  // Ver jugador con el turno actual
                    verTurno();
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
                        case("edificios"):
                            if(parametro1.equals("")); //listarEdificios();
                            else if(Grupo.coloresValidos.contains(parametro1)); //listarEdificiosPorColor(parametro1);
                            else; consola.print("Error, color invalido");
                            break;
                        default:
                            consola.print("Error, introduzca un comando valido");
                            break;
                        }
                        break;
                //salir carcel
                case("salir"): //Pagar y salir de la carcel
                    salirCarcel();
                    break;
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
                        consola.print("Error, introduzca un comando válido");
                    }
                    break;
                // hipotecar + Casilla
                case("hipotecar"):
                    hipotecar(subAccion);
                    break;
                // deshipotecar + Casilla
                case("deshipotecar"):
                    deshipotecar(subAccion);
                    break;
                case("bancarrota"):
                    bancarrota(subAccion);
                    break;
                //comprar + Mostoles
                case("comprar"):
                    comprar(subAccion);
                    break;
                case("vender"):
                    vender(palabras);
                    break;
                //ver tablero
                case("construir"):
                    if(casillaActual instanceof Solar){
                        ((Solar)casillaActual).Construir(jugador, subAccion, jugadores);
                    }else{
                        consola.print("No puedes construir aqui");
                    }
                    break;
                case("ver"):
                    consola.print(tablero.toString());;
                    break;
                case("estadisticas"):
                    if(subAccion.isEmpty()){ //Estadísticas del juego
                        mostrarEstadisticasJuego();
                    } else { //Estadísticas del jugador
                        mostrarEstadisticasJugadorPorNombre(subAccion);
                    }
                    break;
                case("cambiar"):
                    cambiarModo(jugadores.get(turno).getAvatar());
                    break;
                case("crear"):
                    if(!subAccion.equals("trato"))
                        break;
                    tratos.proponerTrato(jugador, jugadores, tablero);
                    break;
                case("?"):
                    printAyuda();
                    break;
                case("continuar"):
                turnoActivo = false;
                    break;
                default:
                    consola.print("Error, introduzca un comando valido\n");
                    break;
            }
        }
        return haComprado; // Retorna el estado actualizado de haComprado
    }

    public void gestionMovimientoAvanzado(Avatar avatarActual, int resultadoTotal, int[] resultadoDados, ArrayList<ArrayList<Casilla>> casillas) {
        boolean haComprado = false;
        int contador = 0;
        int faltaPorMover = resultadoTotal;
        if(avatarActual.getTipo().equalsIgnoreCase("pelota")){
            while(faltaPorMover != 0){
                faltaPorMover = avatarActual.moverEnAvanzado(resultadoTotal, faltaPorMover, casillas, banca, tablero, jugadores);
                if (!avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero, jugadores)){
                    partida = declararBancarrota(avatarActual.getLugar().getDuenho(), avatarActual.getJugador());
                }
                consola.print(tablero.toString());
                turnoIntermedio(avatarActual, avatarActual.getLugar(), false);
            }
        }
        else if(avatarActual.getTipo().equalsIgnoreCase("coche")){
            while(faltaPorMover != 0 && contador < 4){
                // gestionar dobles cuando contador es igual a 3 (solución no optima?)
                if(contador == 3 && resultadoDados[0]== resultadoDados[1]){ // En la última tirada adicional se gestionan los dados dobles
                    while(resultadoDados[0]== resultadoDados[1]){
                        consola.print("LLevas " + this.lanzamientos + " dobles");
                        tirado = false;
                        if(this.lanzamientos<3){
                            resultadoDados = vuelveATirar(resultadoDados);
                            resultadoTotal = resultadoDados[0] + resultadoDados[1];
                            consola.print("\nDADOS: [" + resultadoDados[0] + "] " + " [" + resultadoDados[1] + "]\n");
                            avatarActual.moverEnBasico(casillas, resultadoTotal, banca, tablero, jugadores);
                            if (!avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero, jugadores)){
                                partida = declararBancarrota(avatarActual.getLugar().getDuenho(), avatarActual.getJugador());
                            }
                            consola.print(tablero.toString());;
                            haComprado = turnoIntermedio(avatarActual, avatarActual.getLugar(), haComprado);
                        }else{
                            consola.print("VAS A LA CARCEL");
                            avatarActual.getJugador().encarcelar(casillas);
                            tirado = true;
                            contador++;
                            break;
                        }
                        this.lanzamientos++;
                    }
                }
                if (contador < 3){
                    faltaPorMover = avatarActual.moverEnAvanzado(resultadoTotal, faltaPorMover, casillas, banca, tablero, jugadores);
                    if (!avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero, jugadores)){
                        partida = declararBancarrota(avatarActual.getLugar().getDuenho(), avatarActual.getJugador());
                    }
                    contador += faltaPorMover;
                    consola.print(tablero.toString());;
                    haComprado = turnoIntermedio(avatarActual, avatarActual.getLugar(), haComprado);
                    resultadoDados = vuelveATirar(resultadoDados);
                    resultadoTotal = resultadoDados[0] + resultadoDados[1];
                    consola.print("\nDADOS: [" + resultadoDados[0] + "] " + " [" + resultadoDados[1] + "]\n");
                }
            }
        }
    }

    private int[] vuelveATirar(int[] resultadoDados){
        consola.print("Vuelve a tirar");
        resultadoDados[0] = Integer.parseInt(Juego.consola.read("Introduzca el valor de la tirada del dado 1: "));
        resultadoDados[1] = Integer.parseInt(Juego.consola.read("Introduzca el valor de la tirada del dado 2: "));
        consola.print("\nDADOS: [" + resultadoDados[0] + "] " + " [" + resultadoDados[1] + "]\n");

        return resultadoDados;
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    public void descJugador(String[] partes) {
        // Verificar si el array partes es nulo o tiene menos elementos de los necesarios
        if (partes == null || partes.length <= 2) {
            consola.print("Error: Parámetros insuficientes o nulos.");
            return;
        }

        // Supoñendo que o nombre do jugador está en partes[1].
        String nombreJugador = partes[2];
        Jugador jugador = buscarJugadorPorNombre(nombreJugador);

        if (jugador != null) {
            consola.print("Nombre del Jugador: " + jugador.getNombre());
            consola.print("Fortuna: " + jugador.getFortuna() + "€" );

            if (!jugador.getPropiedades().isEmpty()) {
                consola.print("Propiedades: ");
                for (Casilla propiedad : jugador.getPropiedades()) {
                    consola.print(" - " + propiedad.getNombre());
                    if(propiedad instanceof Solar){
                        consola.print("\t" + ((Solar)propiedad).listarEdificios());
                    }
                }
            } else {
                consola.print("El jugador no tiene propiedades.");
            }

            Avatar avatar = jugador.getAvatar();
            if (avatar != null) {
                consola.print("Avatar: " + avatar.getId());
            } else {
                consola.print("El jugador no tiene un avatar asignado.");
            }

        // Hipotecas.
        } else {
            consola.print("No existe un jugador con este nombre.");
        }
    }

    //////////////////  MIRAR ESTO URGENTEMENTEEEEEEEEEEEEEEEEEEEE  ////////////////
    //////////////////  MIRAR ESTO URGENTEMENTEEEEEEEEEEEEEEEEEEEE  ////////////////
    private Jugador buscarJugadorPorNombre(String nombre) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre.trim())) {
                return jugador;
            }
        }
        consola.print("Jugador no encontrado");
        return null;
    }

    //////////////////  MIRAR ESTO URGENTEMENTEEEEEEEEEEEEEEEEEEEE  ////////////////
    //////////////////  MIRAR ESTO URGENTEMENTEEEEEEEEEEEEEEEEEEEE  ////////////////
    public static Jugador buscarJugadorPorNombre(String nombre, ArrayList<Jugador> jugadores) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre.trim())) {
                return jugador;
            }
        }
        consola.print("Jugador no encontrado");
        return null;
    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    public void descAvatar(String ID) {
        Avatar avatarBuscado = null;
        // Iterar sobre os avatares para encontrar o que coincide co ID.
        for (Avatar avatar : avatares) {
            if (avatar.getId().equals(ID)) {
                avatarBuscado = avatar;
                break;
            }
        }

        if (avatarBuscado == null) {
            consola.print("Avatar con ID " + ID + " no encontrado.");
        } else {
            consola.print("ID: " + avatarBuscado.getId());
            consola.print("Jugador: " + avatarBuscado.getJugador().getNombre());
            consola.print("Posición actual: " +  avatarBuscado.getLugar().getNombre());
            consola.print("Tipo: " + avatarBuscado.getTipo());
        }
    }



    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    public void descCasilla(String nombre) {
        Casilla casilla = tablero.obtenerCasilla(nombre);
        if (casilla != null) {
            consola.print(casilla.infoCasilla());
        } else {
            consola.print("No se encontró una casilla con ese nombre.");
        }
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    public void lanzarDados(boolean trucados) {
        if(!tirado){

            Avatar avatarActual = avatares.get(turno);
            Jugador jugadorActual = avatarActual.getJugador();
            ArrayList<ArrayList<Casilla>> casillas = tablero.getPosiciones();

            if (jugadorActual.estaBloqueado()) {
                consola.print("Estás bloqueado para tirar los dados, te quedan " + jugadorActual.getTurnosBloqueado() + " turno(s) bloqueado(s).");
                jugadorActual.decrementarTurnosBloqueados(); // Decrementamos el número de turnos bloqueados
                tirado = true;
                return; // No se permite tirar los dados
            }

            int[] resultadoDados;
            if(trucados){
                resultadoDados = solicitarTiradaDados(); // Pide que introduzcasd la tirada
            } else {
                resultadoDados = new int[2];
                resultadoDados[0] = dado1.hacerTirada();
                resultadoDados[1] = dado2.hacerTirada();
            }

            int resultadoTotal = resultadoDados[0] + resultadoDados[1];
            this.ultimatirada = resultadoTotal;
            consola.print("\nDADOS: [" + resultadoDados[0] + "] " + " [" + resultadoDados[1] + "]\n");

            jugadorActual.incrementarLanzamientos();
            this.lanzamientos++;
            tirado = true;

            if (jugadorActual.getEnCarcel()) {
                manejarJugadorEnCarcel(jugadorActual, avatarActual, resultadoTotal, resultadoDados, casillas);
            } else {
                manejarJugadorFueraCarcel(avatarActual, jugadorActual, resultadoTotal, resultadoDados, casillas);
            }

        } else {
            consola.print("Ya no puedes tirar más");
        }
    }

    // Método para solicitar las tiradas de los dados
    private int[] solicitarTiradaDados() {
        int resultadoDado1 = Integer.parseInt(Juego.consola.read("Introduzca el valor de la tirada del dado 1: "));
        int resultadoDado2 = Integer.parseInt(Juego.consola.read("Introduzca el valor de la tirada del dado 2: "));
        return new int[]{resultadoDado1, resultadoDado2};
    }

    // Método para manejar la lógica cuando el jugador está en la cárcel
    private void manejarJugadorEnCarcel(Jugador jugadorActual, Avatar avatarActual, int resultadoTotal, int[] resultadoDados, ArrayList<ArrayList<Casilla>> casillas) {
        if (resultadoDados[0] == resultadoDados[1]) { // Si sacas dobles sales de la carcel
            consola.print("Sales de la carcel");
            jugadorActual.setEnCarcel(false);
            tirado = false;
            avatarActual.moverEnBasico(casillas, resultadoTotal, banca, tablero, jugadores);
            if (!avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero,jugadores)){
                partida = declararBancarrota(avatarActual.getLugar().getDuenho(), avatarActual.getJugador());
            } //Aquí hay que comprobar esto cada vez que se mueve el jugador para ver si hay que terminar la partida
        } else {
            jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1); //Suma uno a sus turnos dentro de la carcel
            if (jugadorActual.getTiradasCarcel() == 3) { // Si lleva tres turnos en la carcel sale
                salirCarcel();
                jugadorActual.setTiradasCarcel(0);
            } else {
                consola.print("Sigues en la carcel. Te quedan " + (3 - jugadorActual.getTiradasCarcel()) + " intentos.");
            }
        }
    }

    // Método para manejar la lógica cuando el jugador está fuera de la cárcel
    private void manejarJugadorFueraCarcel(Avatar avatarActual, Jugador jugadorActual, int resultadoTotal, int[] resultadoDados, ArrayList<ArrayList<Casilla>> casillas) {
        if (resultadoDados[0] == resultadoDados[1]) {
            consola.print("Llevas " + this.lanzamientos + " dobles");
            tirado = false;

            if (this.lanzamientos < 3) {
                manejarMovimientoAvatarPorTipo(avatarActual, resultadoTotal, resultadoDados, casillas);
                consola.print("Vuelve a tirar");
            } else {
                consola.print("VAS A LA CARCEL");
                avatarActual.getJugador().encarcelar(casillas);
                tirado = true;
            }
        } else {
            manejarMovimientoAvatarPorTipo(avatarActual, resultadoTotal, resultadoDados, casillas);
        }
    }

    // Método para manejar el movimiento del avatar según su tipo
    private void manejarMovimientoAvatarPorTipo(Avatar avatarActual, int resultadoTotal, int[] resultadoDados, ArrayList<ArrayList<Casilla>> casillas) {
        if (avatarActual.getModo()) { // Si está en modo especial cambia el movimiento
            // Llama al método moverEnAvanzado, definido por cada subclase
            // Llamar a mover avanzado desde turno intermedio?
            gestionMovimientoAvanzado(avatarActual, resultadoTotal, resultadoDados, casillas);
        }
        else {
            // Llama al método moverEnBasico, implementado en la clase base
            avatarActual.moverEnBasico(casillas, resultadoTotal, banca, tablero, jugadores);
            if (!avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero, jugadores)){
                partida = declararBancarrota(avatarActual.getLugar().getDuenho(), avatarActual.getJugador());
            }
        }
    }


    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    public void comprar(String nombre) {
        Jugador jugador_actual = jugadores.get(turno);
        Casilla casilla_compra = tablero.obtenerCasilla(nombre);

        if(casilla_compra instanceof Propiedad){
            if(((Propiedad)casilla_compra).comprarPropiedad(jugador_actual, banca));
        }
    }

    public void hipotecar(String nombre) {
        Casilla casilla_hipotecada = tablero.obtenerCasilla(nombre);
        Jugador jugador_actual = jugadores.get(turno);

        if(casilla_hipotecada instanceof Propiedad){
            ((Propiedad)casilla_hipotecada).hipotecarCasilla(jugador_actual);
        }
    }

    public void deshipotecar(String nombre) {
        Casilla casilla_deshipotecada = tablero.obtenerCasilla(nombre);
        Jugador jugador_actual = jugadores.get(turno);

        if(casilla_deshipotecada instanceof Propiedad){
            ((Propiedad)casilla_deshipotecada).deshipotecarCasilla(jugador_actual);
        }
    }

    public int obtenerTirada() {
        return this.ultimatirada;
    }

    public void bancarrota(String nombre) {
        if (!jugadores.contains(buscarJugadorPorNombre(nombre))) {
            consola.print("No existe un jugador con ese nombre.");
            return;
        }

        Jugador jugadorActual = jugadores.get(turno);
        Jugador jugador_acreedor = jugadorActual.getAvatar().getLugar().getDuenho();
        int tirada = this.ultimatirada;
        Casilla casilla = jugadorActual.getAvatar().getLugar();
        solvente = casilla.evaluarCasilla(jugadorActual, banca, tirada, tablero,jugadores);

        declararBancarrota(jugador_acreedor, jugadorActual);
        tirado = true;
        lanzamientos = 0;
    }
    
    
    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    public void salirCarcel() {
        Jugador jugador = jugadores.get(turno);
        if(jugador.getEnCarcel()){
            jugador.setEnCarcel(false);
            jugador.incrementarPagoTasasEImpuestos(Valor.SALIR_CARCEL);
            consola.print("El jugador " + jugador.getNombre() + " sale de la carcel " + " pagando " + Valor.SALIR_CARCEL);
        }else{
            consola.print("El jugador " + " no está en la carcel");
        }
    }

    public void vender(String[] partes){
        if (partes.length < 4){
            consola.print("No has pasado todos los parámetros");
            return;
        }

        String construccion = partes[1];
        Jugador jugador = jugadores.get(turno);
        Casilla casilla = tablero.obtenerCasilla(partes[2]);
        int cantidad = Integer.valueOf(partes[3]);

        ((Solar)casilla).VenderEdificios(jugador, construccion, cantidad);
    }

    public boolean declararBancarrota(Jugador jugador_acreedor, Jugador jugadorActual){
        if (jugador_acreedor == null) {
            consola.print("El jugador acreedor no existe.");
            return true;
        }
        if (!solvente) {
            consola.print(jugadorActual.getNombre() + " no puede pagar su deuda y se declara en bancarrota. Sus propiedades pasan a " + jugador_acreedor.getNombre() + ".");
                List<Casilla> propiedadesJugador = new ArrayList<>(jugadorActual.getPropiedades());
                for (Casilla propiedad : propiedadesJugador) {
                    ((Propiedad)propiedad).cambiarDuenho(jugador_acreedor);

                }
            jugadorActual.getAvatar().getLugar().getAvatares().remove(jugadorActual.getAvatar());
            jugadores.remove(jugadorActual);
            avatares.remove(jugadorActual.getAvatar());
            if (jugadores.size() < 2) {
                consola.print("El ganador es " + jugador_acreedor.getNombre() + ", ¡enhorabuena!");
                return false;
            }
        } else if (solvente) {
            List<Casilla> propiedadesJugador = new ArrayList<>(jugadorActual.getPropiedades());
            for (Casilla propiedad : propiedadesJugador) {
                propiedad.setDuenho(banca);
                jugadorActual.getPropiedades().remove(propiedad);
            }
            jugadorActual.getAvatar().getLugar().getAvatares().remove(jugadorActual.getAvatar());
            jugadores.remove(jugadorActual);
            avatares.remove(jugadorActual.getAvatar());
            consola.print(jugadorActual.getNombre() + " ha decidido declararse en bancarrota voluntariamente. Sus propiedades vuelven a la banca.");
            if (jugadores.size() < 2){
                consola.print("No hay suficientes jugadores para continuar.");
            }
            return false;
        }
        return true;
    }


    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    public void listarVenta() {
        for(int i = 0; i<40; i++){
            Casilla casilla = this.tablero.obtenerCasilla(i);
            if((casilla instanceof Propiedad) && (casilla.getDuenho().equals(banca))){
                consola.print(this.tablero.obtenerCasilla(i).infoCasilla());
            }
        }
    }

    public void listarEdificios() {
        boolean ningunEdificio = true;
        for(int i = 0; i<40; i++){
            Casilla casilla = this.tablero.obtenerCasilla(i);
            if(casilla instanceof Solar){
                if(((Solar)casilla).EsSolarEdificado()){
                    ningunEdificio = false;
                    ((Solar)casilla).listarEdificios();
                }
            }
        }
        if(ningunEdificio){
            consola.print("No hai ningun edificio construido");
        }
    }
    
    public void listarEdificiosPorColor(String color) {
        Grupo grupo = tablero.getGrupos().get(color); //For Loop para facelo case insensitive
        if(grupo==null){
            consola.print("Grupo de color inválido");
        }else{
            grupo.listarEdificiosPorColor(banca);
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    @Override
    public void listarJugadores() {
        for(int i = 0; i<jugadores.size();i++){
            consola.print(jugadores.get(i).getNombre());
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    public void listarAvatares() {
        for(int i = 0; i<jugadores.size();i++){
            consola.print(jugadores.get(i).getAvatar().getId());
        }
    }

    // Método que muestra el nombre y el avatar del jugador que tiene el turno
    @Override
    public void verTurno() {
        if(!jugadores.isEmpty()){
            consola.print("El turno es de:");
            consola.print("\n{");
            System.out.print("Nombre: "); consola.print(jugadores.get(turno).getNombre());
            System.out.print("Avatar: "); consola.print(jugadores.get(turno).getAvatar().getId());
            consola.print("}\n");
        }
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    public void acabarTurno() {
        if(tirado){
            this.turno = (this.turno+1)%(jugadores.size());
            this.tirado = false;
            this.lanzamientos = 0;
        }else{
            consola.print("Debes tirar antes");
        }
    }

    public void cambiarModo(Avatar avatar){
        if (avatar != null) {
            avatar.setModo(!avatar.getModo());
            consola.print("Modo cambiado. El avatar ahora está en modo " + (avatar.getModo() ? "avanzado" : "normal") + ".");
        } else {
            consola.print("El avatar no existe.");
        }
    }

   // Nuevo método para buscar un jugador por nombre y mostrar sus estadísticas
   public void mostrarEstadisticasJugadorPorNombre(String nombreJugador) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombreJugador)) {
                jugador.mostrarEstadisticasJugador(jugador);
                return;
            }
        }
        consola.print("Jugador con nombre '" + nombreJugador + "' no encontrado.");
    }

    public void mostrarEstadisticasJuego() {
        // Obtener las estadísticas usando los métodos correspondientes
        Casilla casillaMasRentable = obtenerCasillaMasRentable();
        Grupo grupoMasRentable = obtenerGrupoMasRentable();
        Casilla casillaMasFrecuentada = obtenerCasillaMasFrecuentada();
        Jugador jugadorMasVueltas = obtenerJugadorMasVueltas();
        Jugador jugadorMasVecesDados = obtenerJugadorMasVecesDados();
        Jugador jugadorEnCabeza = obtenerJugadorEnCabeza();

        // Imprimir las estadísticas en el formato solicitado
        consola.print("$> estadisticas");
        consola.print("{");
        consola.print("  casillaMasRentable: " + (casillaMasRentable != null ? casillaMasRentable.getNombre() : "N/A") + ",");
        consola.print("  grupoMasRentable: " + (grupoMasRentable != null ? grupoMasRentable.getColorGrupo() + "  " : "N/A") + Valor.RESET + ",");
        consola.print("  casillaMasFrecuentada: " + (casillaMasFrecuentada != null ? casillaMasFrecuentada.getNombre() : "N/A") + ",");
        consola.print("  jugadorMasVueltas: " + (jugadorMasVueltas != null ? jugadorMasVueltas.getNombre() : "N/A") + ",");
        consola.print("  jugadorMasVecesDados: " + (jugadorMasVecesDados != null ? jugadorMasVecesDados.getNombre() : "N/A") + ",");
        consola.print("  jugadorEnCabeza: " + (jugadorEnCabeza != null ? jugadorEnCabeza.getNombre() : "N/A"));
        consola.print("}");
    }

    private Casilla obtenerCasillaMasRentable() {
        Casilla masRentable = null;
        float maxIngresos = 0;

        for (int i = 0; i < 40; i++) {
            Casilla casilla = tablero.obtenerCasilla(i);
            if (casilla.getIngresosTotales() > maxIngresos) {
                maxIngresos = casilla.getIngresosTotales();
                masRentable = casilla;
            }
        }

        return masRentable;
    }

    private Grupo obtenerGrupoMasRentable(){
        Grupo grupoMasRentable = null;
        float maxIngresos = 0;

        for (int i = 0; i < 40; i++) {
            Casilla casilla = tablero.obtenerCasilla(i); // Obtener la casilla
            if(casilla instanceof Solar){
                Grupo grupo = ((Solar)casilla).getGrupo();
                if (grupo.getIngresosTotales() > maxIngresos) {
                    maxIngresos = grupo.getIngresosTotales();
                    grupoMasRentable = grupo;
                }
            }
        }
        return grupoMasRentable;
    }

    private Casilla obtenerCasillaMasFrecuentada() {
        Casilla casillaMasFrecuentada = null;
        int maxVisitas = 0;

        for (int i = 0; i < 40; i++) {
            Casilla casilla = tablero.obtenerCasilla(i); // Obtener la casilla
            if (casilla.getContadorVisitas() > maxVisitas) {
                maxVisitas = casilla.getContadorVisitas();
                casillaMasFrecuentada = casilla;
            }
        }

        return casillaMasFrecuentada; // Retorna la casilla más frecuentada
    }

    private Jugador obtenerJugadorMasVueltas() {
        Jugador jugadorMasVueltas = null;
        int maxVueltas = 0;

        for (Jugador jugador : jugadores) {
            if (jugador.getVueltas() > maxVueltas) {
                maxVueltas = jugador.getVueltas();
                jugadorMasVueltas = jugador;
            }
        }

        return jugadorMasVueltas;  // Retorna el jugador con más vueltas
    }

    private Jugador obtenerJugadorMasVecesDados() {
        Jugador jugadorMasVecesDados = null;
        int maxLanzamientos = 0;

        for (Jugador jugador : jugadores) {
            if (jugador.getLanzamientos() > maxLanzamientos) {
                maxLanzamientos = jugador.getLanzamientos();
                jugadorMasVecesDados = jugador;
            }
        }

        return jugadorMasVecesDados;  // Retorna el jugador que ha lanzado más veces
    }

    /**
     * Obtiene el jugador con la mayor fortuna total.
     * @return el jugador con la mayor fortuna acumulada.
     */
    private Jugador obtenerJugadorEnCabeza() {
        Jugador jugadorEnCabeza = null;
        float fortunaMaxima = 0;

        // Recorre todos los jugadores y calcula su fortuna total
        for (Jugador jugador : jugadores) {
            float fortunaActual = jugador.calcularFortunaTotal();
            if (fortunaActual > fortunaMaxima) {
                fortunaMaxima = fortunaActual;
                jugadorEnCabeza = jugador;
            }
        }

        return jugadorEnCabeza;  // Retorna el jugador con la mayor fortuna
    }
}
