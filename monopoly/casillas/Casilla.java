package monopoly.casillas;

import partida.Jugador;
import partida.avatares.Avatar;

import monopoly.Juego;
import monopoly.casillas.Propiedades.*;
import monopoly.Tablero;

import java.util.ArrayList;

public abstract class Casilla {

    // Atributos:
    private String nombre; // Nombre de la casilla
    // private String tipo; //Tipo de casilla (Solar, Especial, Transporte,
    // Servicios, Suerte, Comunidad).
    // private float valor; //Valor de esa casilla (en la mayoría será valor de
    // compra, en la casilla parking se usará como el bote).
    private int posicion; // Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; // Dueño de la casilla (por defecto sería la banca).
    // private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    // private float impuesto; //Cantidad a pagar por caer en la casilla: el
    // alquiler en solares/servicios/transportes o impuestos.
    private ArrayList<Avatar> avatares; // Avatares que están situados en la casilla.
    private ArrayList<Integer> hanEstado; // Avatares que están situados en la casilla.
    // private Edificios edificios;
    // private boolean hipotecada;

    // Atributos para estadísticas
    private float ingresosTotales;
    private int contadorVisitas; // Borrar y usar hanEstado

    // Constructores:
    public Casilla() {
    }// Parámetros vacíos

    /*
     * Constructor para casillas
     * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte),
     * posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.duenho = duenho;
        this.avatares = new ArrayList<>();
        inicializarHanEstado();

        // Stats
        this.ingresosTotales = 0;
    }

    private void inicializarHanEstado() {
        this.hanEstado = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.hanEstado.add(0);
        }
    }

    // Métodos Getter
    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }

    public ArrayList<Avatar> getAvatares() {
        return this.avatares;
    }

    public float getIngresosTotales() {
        return this.ingresosTotales;
    }

    public int getContadorVisitas() {
        return this.contadorVisitas;
    }

    public ArrayList<Integer> getHanEstado() {
        return this.hanEstado;
    }

    // Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (av != null && !this.avatares.contains(av)) {
            this.avatares.add(av);
        }
    }

    // Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        if (this.avatares.contains(av)) {
            this.avatares.remove(av);
        } else {
            Juego.consola.print("El avatar " + av.getId() + " no está en " + this.nombre);
        }
    }

    public void haEstado(Jugador jugador, ArrayList<Jugador> jugadores) {
        this.hanEstado.set(jugadores.indexOf(jugador), this.hanEstado.get(jugadores.indexOf(jugador)) + 1);
    }
    /*
     * Método para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de
     * todos los jugadores.
     * Este método toma como argumento la cantidad a añadir del valor de la casilla.
     */
    /*
     * public void sumarValor(float suma) {
     * this.valor += suma;
     * }
     */

    /*
     * Método para mostrar información sobre una casilla.
     * Devuelve una cadena con información específica de cada tipo de casilla.
     */
    /*
     * public String infoCasilla() {
     * //Creamos a cadena a devolver
     * StringBuilder info = new StringBuilder();
     * 
     * info.append("Nombre: ").append(nombre).append("\n");
     * info.append("Posición: ").append(posicion).append("\n");
     * info.append("Tipo: ").append(tipo).append("\n");
     * 
     * switch (tipo.toLowerCase()) {
     * case "especial":
     * info.append("Descripción: Casilla especial.\n");
     * break;
     * 
     * case "comunidad":
     * info.append("Descripción: Casilla de comunidad.\n");
     * break;
     * 
     * default:
     * info.append("Descripción: Tipo de casilla desconocido.\n");
     * break;
     * }
     * 
     * return info.toString();
     * }
     */

    public abstract String infoCasilla();

    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero,
            ArrayList<Jugador> jugadores);

    public void registrarIngreso(float cantidad) {
        this.ingresosTotales += cantidad; // Suma la cantidad recibida a los ingresos totales
        if (this instanceof Propiedad) {
            ((Propiedad) this).getGrupo().registrarIngresosGrupo(cantidad); // Registrar ingresos en el grupo si existe
        }
    }
}