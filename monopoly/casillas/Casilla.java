package monopoly.casillas;

import partida.*;
import partida.avatares.Avatar;
import partida.cartas.Carta;

import monopoly.Grupo;
import monopoly.Tablero;
import monopoly.Valor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public abstract class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    //private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Suerte, Comunidad).
    //private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    //private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    //private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.
    private ArrayList<Integer> hanEstado; //Avatares que están situados en la casilla.
    //private Edificios edificios;
    //private boolean hipotecada;

    //Atributos para estadísticas
    private float ingresosTotales;
    private int contadorVisitas;

    //Constructores:
    public Casilla() {
    }//Parámetros vacíos

    /*Constructor para casillas 
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.duenho = duenho;
        this.avatares = new ArrayList<>();
        inicializarHanEstado();
        
        // Stats
        this.ingresosTotales = 0;
        this.contadorVisitas = 0;
    }

    private void inicializarHanEstado(){
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

    public void setAvatares(ArrayList<Avatar> avatares){
        this.avatares = avatares;
    }

    public ArrayList<Avatar> getAvatares(){
        return this.avatares;
    }

    public float getIngresosTotales() {
        return this.ingresosTotales;
    }

    public int getContadorVisitas(){
        return this.contadorVisitas;
    }

    public ArrayList<Integer> getHanEstado(){
        return this.hanEstado;
    }

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (av != null && !this.avatares.contains(av)) {
            this.avatares.add(av);
        }
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        if (this.avatares.contains(av)){
            this.avatares.remove(av);
        }else{
            System.out.println("El avatar " + av.getId() + " no está en " + this.nombre);
        }
    }


    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if(this.tipo == "Solar" || this.tipo == "Transporte" || this.tipo == "Servicios"){
            if(solicitante.getAvatar().getLugar() == this){
                if(this.duenho == banca){
                    solicitante.incrementarDineroInvertido(this.valor);
                    solicitante.anhadirPropiedad(this);
                    this.duenho = solicitante;
                    System.out.println("Has comprado la casilla " + this.nombre + " por " + this.valor);
                }else if(this.duenho == solicitante){
                    System.out.println("Esta casilla ya te pertenece");
                }else{
                    System.out.println("La casilla es de "+this.duenho.getNombre());
                }
            }else{
                System.out.println("Debes estar en la casilla");
            }
        }else{ // Comprar carcel, salida, etcetc
            System.out.println("Esta casilla no se puede comprar");
        }
    }

    /*Método para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
    * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        this.valor += suma;
    }

    /*Método para mostrar información sobre una casilla.
    * Devuelve una cadena con información específica de cada tipo de casilla.*/
    public String infoCasilla() {
        //Creamos a cadena a devolver
        StringBuilder info = new StringBuilder();
        
        info.append("Nombre: ").append(nombre).append("\n");
        info.append("Posición: ").append(posicion).append("\n");
        info.append("Tipo: ").append(tipo).append("\n");
    
        switch (tipo.toLowerCase()) {
            case "solar":
                info.append("Valor de compra: ").append(valor).append("\n");
                info.append("Impuesto: ").append(calcularPagoSolar(this.getDuenho())).append("\n");
                if (duenho != null) {
                    info.append("Dueño: ").append(duenho.getNombre()).append("\n");
                } else {
                    info.append("Dueño: Banca\n");
                }
                info.append("Grupo: ").append(grupo.getColorGrupo()).append("  ").append(Valor.RESET).append("\n");
                info.append("Construcciones: " + "|Casas=" + this.getEdificios().getCasas() + "|Hoteles=" + this.getEdificios().getHoteles() + "|Piscinas=" + this.getEdificios().getPiscinas() + "|Pistas=" + this.getEdificios().getPistas() + "|\n");
                break;
                
            case "especial":
                info.append("Descripción: Casilla especial.\n");
                break;
                
            case "transporte":
                info.append("Valor de compra: ").append(valor).append("\n");
                info.append("Impuesto: ").append(impuesto).append("\n");
                if (duenho != null) {
                    info.append("Dueño: ").append(duenho.getNombre()).append("\n");
                } else {
                    info.append("Dueño: Banca\n");
                }
                break;
                
            case "servicios":
                info.append("Valor de compra: ").append(valor).append("\n");
                info.append("Impuesto: ").append(impuesto).append("\n");
                if (duenho != null) {
                    info.append("Dueño: ").append(duenho.getNombre()).append("\n");
                } else {
                    info.append("Dueño: Banca\n");
                }
                break;
                
            case "comunidad":
                info.append("Descripción: Casilla de comunidad.\n");
                break;
                
            default:
                info.append("Descripción: Tipo de casilla desconocido.\n");
                break;
        }
    
        return info.toString();
    }

    

    private int numTransporte(){
        int contador = 0;
        Jugador duenho = this.getDuenho();
        for (Casilla casilla : duenho.getPropiedades()) {
            if(casilla.tipo.equals("Transporte")){
                contador++;
            }
        }
        return contador;
    }

    private int numServicios(){
        int contador = 0;
        Jugador duenho = this.getDuenho();
        for (Casilla casilla : duenho.getPropiedades()) {
            if(casilla.tipo.equals("Servicios")){
                contador++;
            }
        }
        return contador;
    }
    public void registrarIngreso(float cantidad) {
        this.ingresosTotales += cantidad; // Suma la cantidad recibida a los ingresos totales
        if (grupo != null) {
            grupo.registrarIngresosGrupo(cantidad); // Registrar ingresos en el grupo si existe
        }
    }

    

    public void cambiarDuenho(Jugador nuevoduenho){

        this.duenho.getPropiedades().remove(this);
        this.duenho = nuevoduenho;
        nuevoduenho.getPropiedades().add(this);
    }
}