package partida.avatares;

import monopoly.*;
import monopoly.casillas.Casilla;
import partida.Dado;
import partida.Jugador;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public abstract class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.
    private boolean modo; //Modo de movimiento avanzado

    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private int ultimatirada;
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    
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
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        int posicionActual = lugar.getPosicion();
        Casilla casillaActual = obtenerCasilla(posicionActual, casillas);
        int posicionNueva = (posicionActual + valorTirada)%40;

        if (posicionNueva < 0) { 
            posicionNueva += 40; // Si el resultado es negativo, lo ajustamos sumando 40
        }

        Casilla nuevaCasilla = obtenerCasilla(posicionNueva, casillas);
        lugar.eliminarAvatar(this);
        this.lugar = nuevaCasilla;
        nuevaCasilla.anhadirAvatar(this);
        System.out.println("El jugador " + this.jugador.getNombre() + " se mueve " + valorTirada + " casillas,\nde " + casillaActual.getNombre() + " a " + nuevaCasilla.getNombre());
    }

    public Casilla obtenerCasilla(int posicionNueva, ArrayList<ArrayList<Casilla>> casillas) {
        for (ArrayList<Casilla> lado : casillas) {
            for (Casilla casilla : lado) {
                if (casilla.getPosicion()==posicionNueva) {
                    return casilla;  
                }
            }
        }
        return null;
    }

    // Método para mover el avatar y evaluar la casilla
    protected void moverAvatarYEvaluar(Avatar avatarActual, int valorTirada, int resultadoTotal, ArrayList<ArrayList<Casilla>> casillas) {
        Casilla origen = avatarActual.getLugar();

        avatarActual.moverAvatar(casillas, valorTirada);
        /*if (!avatarActual.getLugar().evaluarCasilla(avatarActual.getJugador(), banca, resultadoTotal, tablero,jugadores)){ //Añadir estos parámetros a la función
            partida = declararBancarrota(avatarActual.getLugar().getDuenho(), avatarActual.getJugador());
        }*/
        
        Casilla destino = avatarActual.getLugar();

        if(valorTirada >= 0) avatarActual.getJugador().cobrarPasoPorSalida(origen, destino);
        else avatarActual.getJugador().PasoPorSalidaInverso(origen, destino);
    }

    // Métodos para movimiento
    public  void moverEnBasico(ArrayList<ArrayList<Casilla>> casillas, int valorTirada){
        moverAvatarYEvaluar(this, valorTirada, valorTirada, casillas); // Comportamiento estándar
    }

    // Método abstracto, obliga a las subclases a implementarlo
    public abstract void moverEnAvanzado(ArrayList<ArrayList<Casilla>> casillas, int valorTirada);

    public boolean turnoIntermedio(Avatar avatarActual, Casilla casillaActual, boolean haComprado) {
        Jugador jugador = avatarActual.getJugador();
        Scanner scanner = new Scanner(System.in);
        boolean turnoActivo = true;
    
        while (turnoActivo) {
            // Mostrar el menú de opciones disponibles
            System.out.println();
            System.out.print("Seleccione una opción: ");
            
            String opcion = scanner.nextLine();
            scanner.nextLine();  // Consumir el salto de línea

            String[] palabras = opcion.split(" ");

            String accion = palabras[0];  
            String subAccion = (palabras.length > 1) ? palabras[1] : "";
            String parametro1 = (palabras.length > 2) ? palabras[2] : "";
    
            switch (accion) {
                case("jugador"):  // Ver jugador con el turno actual
                    //verTurno();
                    break;
    
                //listar jugadores / avatares / enventa
                case("listar"):
                    switch(subAccion){
                        case("jugadores"):
                            //listarJugadores();
                            break;
                        case("avatares"):
                            //listarAvatares();
                            break;
                        case("enventa"):
                            //listarVenta();
                            break;
                        case("edificios"):
                            if(parametro1.equals("")); //listarEdificios();
                            else if(Grupo.coloresValidos.contains(parametro1)); //listarEdificiosPorColor(parametro1);
                            else; System.out.println("Error, color invalido");
                            break;
                        default:
                            System.out.println("Error, introduzca un comando valido");
                            break;
                        }
                        break;
                //salir carcel
                case("salir"): //Pagar y salir de la carcel
                    //salirCarcel();
                    break;
                case("describir"):
                    if (subAccion.equals("jugador")) {
                        // Describir un jugador
                        //descJugador(palabras);
                    } else if (subAccion.equals("avatar")) {
                        // Describir un avatar
                        //descAvatar(parametro1);
                    } else if (!subAccion.isEmpty()) {
                        // Asumimos que subAccion es el nombre de una casilla
                        //descCasilla(subAccion);  // Interpretamos subAccion como nombre de la casilla
                    } else {
                        // Si no se recibe una subacción válida
                        System.out.println("Error, introduzca un comando válido");
                    }
                    break;
                // hipotecar + Casilla
                case("hipotecar"):
                    //hipotecar(subAccion);
                    break;
                // deshipotecar + Casilla
                case("deshipotecar"):
                    //deshipotecar(subAccion);
                    break;
                case("bancarrota"):
                    //bancarrota(subAccion);
                    break;
                //comprar + Mostoles
                case("comprar"):
                    //comprar(subAccion);
                    break;
                case("vender"):
                    //vender(palabras);
                    break;
                //ver tablero
                case("construir"):
                    //casillaActual.Construir(jugadorActual, subAccion, jugadores);
                    break;
                case("ver"):
                    //System.out.println(tablero);
                    break;
                case("estadisticas"):
                    if(subAccion.isEmpty()){ //Estadísticas del juego
                        //mostrarEstadisticasJuego();
                    } else { //Estadísticas del jugador
                        //mostrarEstadisticasJugadorPorNombre(subAccion);
                    }
                    break;
                case("cambiar"):
                    //cambiarModo(jugadores.get(turno).getAvatar());
                    break;
                case("?"):
                    //printAyuda();
                    break;
                default:
                    System.out.println("Error, introduzca un comando valido\n");
                    break;
            }
        }
        System.out.println();
        return haComprado; // Retorna el estado actualizado de haComprado
    }


    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados(Tablero tablero) {
        if(!tirado){

            ArrayList<ArrayList<Casilla>> casillas = tablero.getPosiciones();

            if (this.getJugador().estaBloqueado()) {
                System.out.println("Estás bloqueado para tirar los dados, te quedan " + this.getJugador().getTurnosBloqueado() + " turno(s) bloqueado(s).");
                this.getJugador().decrementarTurnosBloqueados(); // Decrementamos el número de turnos bloqueados
                tirado = true;
                return; // No se permite tirar los dados
            }

            ////////////REVISAR////////////
            Dado dado1 = new Dado();
            Dado dado2 = new Dado();
            ////////////REVISAR////////////

            int[] resultadoDados = new int[2];
            resultadoDados[0] = dado1.hacerTirada();
            resultadoDados[1] = dado2.hacerTirada();

            int resultadoTotal = resultadoDados[0] + resultadoDados[1];
            this.ultimatirada = resultadoTotal;
            System.out.println("\nDADOS: [" + resultadoDados[0] + "] " + " [" + resultadoDados[1] + "]\n");
    
            this.getJugador().incrementarLanzamientos(); 
            this.lanzamientos++;
            tirado = true;
    
            if (this.getJugador().getEnCarcel()) {
                manejarJugadorEnCarcel(this.getJugador(), this, resultadoTotal, resultadoDados, casillas);
            } else {
                manejarJugadorFueraCarcel(this.getJugador(), this, resultadoTotal, resultadoDados, casillas);
            }
    
        } else {
            System.out.println("Ya no puedes tirar más");
        }
    }

    //Lanzar dados trucados
    private void dadosTrucados(Tablero tablero) {
        if (!tirado) {
            ArrayList<ArrayList<Casilla>> casillas = tablero.getPosiciones();

            if (this.getJugador().estaBloqueado()) {
                System.out.println("Estás bloqueado para tirar los dados, te quedan " + this.getJugador().getTurnosBloqueado() + " turno(s) bloqueado(s).");
                this.getJugador().decrementarTurnosBloqueados(); // Decrementamos el número de turnos bloqueados
                tirado = true;
                return; // No se permite tirar los dados
            }

            int[] resultadoDados = solicitarTiradaDados(); // Pide que introduzcasd la tirada
            int resultadoTotal = resultadoDados[0] + resultadoDados[1];
            this.ultimatirada = resultadoTotal;
            System.out.println("\nDADOS: [" + resultadoDados[0] + "] " + " [" + resultadoDados[1] + "]\n");
    
            this.getJugador().incrementarLanzamientos(); 
            this.lanzamientos++;
            tirado = true;
    
            if (this.getJugador().getEnCarcel()) {
                manejarJugadorEnCarcel(this.getJugador(), this, resultadoTotal, resultadoDados, casillas);
            } else {
                manejarJugadorFueraCarcel(this.getJugador(), this, resultadoTotal, resultadoDados, casillas);
            }
    
        } else {
            System.out.println("Ya no puedes tirar más");
        }
    }
    
    // Método para solicitar las tiradas de los dados
    private int[] solicitarTiradaDados() {
        Scanner scanDado = new Scanner(System.in);
        System.out.print("Introduzca el valor de la tirada del dado 1: ");
        int resultadoDado1 = scanDado.nextInt();
        System.out.print("Introduzca el valor de la tirada del dado 2: ");
        int resultadoDado2 = scanDado.nextInt();
        return new int[]{resultadoDado1, resultadoDado2};
    }
    
    // Método para manejar la lógica cuando el jugador está en la cárcel
    private void manejarJugadorEnCarcel(Jugador jugadorActual, Avatar avatarActual, int resultadoTotal, int[] resultadoDados, ArrayList<ArrayList<Casilla>> casillas) {
        if (resultadoDados[0] == resultadoDados[1]) { // Si sacas dobles sales de la carcel
            System.out.println("Sales de la carcel");
            jugadorActual.setEnCarcel(false);
            tirado = false;
            moverAvatarYEvaluar(avatarActual, resultadoTotal, resultadoTotal, casillas);
        } else {
            jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1); //Suma uno a sus turnos dentro de la carcel
            if (jugadorActual.getTiradasCarcel() == 3) { // Si lleva tres turnos en la carcel sale
                //salirCarcel();
                jugadorActual.setTiradasCarcel(0);
            } else {
                System.out.println("Sigues en la carcel. Te quedan " + (3 - jugadorActual.getTiradasCarcel()) + " intentos.");
            }
        }
    }
    
    // Método para manejar la lógica cuando el jugador está fuera de la cárcel
    private void manejarJugadorFueraCarcel( Jugador jugadorActual, Avatar avatarActual, int resultadoTotal, int[] resultadoDados, ArrayList<ArrayList<Casilla>> casillas) {
        if (resultadoDados[0] == resultadoDados[1]) { 
            System.out.println("LLevas " + this.lanzamientos + " dobles");
            tirado = false;
    
            if (this.lanzamientos < 3) {
                manejarMovimientoAvatarPorTipo(avatarActual, resultadoTotal, casillas);
                System.out.println("Vuelve a tirar");
            } else {
                System.out.println("VAS A LA CARCEL");
                avatarActual.getJugador().encarcelar(casillas);
                tirado = true;
            }
        } else {
            manejarMovimientoAvatarPorTipo(avatarActual, resultadoTotal, casillas);
        }
    }
    
    // Método para manejar el movimiento del avatar según su tipo
    private void manejarMovimientoAvatarPorTipo(Avatar avatarActual, int resultadoTotal, ArrayList<ArrayList<Casilla>> casillas) {
        if (avatarActual.getModo()) { // Si está en modo especial cambia el movimiento
            // Llama al método moverEnAvanzado, definido por cada subclase
            avatarActual.moverEnAvanzado(casillas, resultadoTotal);
        }
        else {
            // Llama al método moverEnBasico, implementado en la clase base
            avatarActual.moverEnBasico(casillas, resultadoTotal);
        }
    }
}