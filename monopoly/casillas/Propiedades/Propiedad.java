package monopoly.casillas.Propiedades;

import java.util.ArrayList;

import monopoly.Grupo;
import monopoly.Tablero;
import partida.Jugador;

public abstract class Propiedad extends monopoly.casillas.Casilla {
    private float valor;
    private boolean hipotecada;
    private Grupo grupo;

    public Propiedad(String nombre, int posicion, Jugador duenho, float valor){
        super(nombre, posicion, duenho);
        this.valor = valor;
        this.hipotecada = false;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public boolean getHipotecada() {
        return hipotecada;
    } 

    public void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> jugadores);

    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if(solicitante.getAvatar().getLugar() == this){
            if(this.getDuenho() == banca){
                solicitante.incrementarDineroInvertido(this.valor);
                solicitante.anhadirPropiedad(this);
                setDuenho(solicitante);
                System.out.println("Has comprado la casilla " + this.getNombre() + " por " + this.valor);
            }else if(this.getDuenho() == solicitante){
                System.out.println("Esta casilla ya te pertenece");
            }else{
                System.out.println("La casilla es de " + this.getDuenho().getNombre());
            }
        }else{
            System.out.println("Debes estar en la casilla");
        }
    }

    public void hipotecarCasilla(Jugador solicitante) { //SOBREESCRIBIR EN SOLAR PARA CHECKEAR SI ESTÁ CONSTRUIDA
        if (this.getDuenho().equals(solicitante)) {
            if (this.hipotecada == true) {
                System.out.println("La casilla ya está hipotecada");
            } else {
                solicitante.setFortuna(solicitante.getFortuna() + this.getValor());
                this.hipotecada = true;
                System.out.println(solicitante.getNombre() + " ha hipotecado la casilla " + this.getNombre() + " y recibe " + this.getValor() + ".");
            }
        } else {
            System.out.println("No eres propietario de esta casilla.");
        }
       
    }

    public void deshipotecarCasilla(Jugador solicitante){
        if(this.hipotecada == true) {
            if (this.getDuenho() != solicitante) {
                System.out.println("No puedes deshipotecar la casilla si no eres su dueño.");    
            } else if (this.getDuenho() == solicitante) {
            solicitante.setFortuna(solicitante.getFortuna() - (this.getValor() + (10*this.getValor()/100)));
            System.out.println(solicitante.getNombre() + " ha deshipotecado la casilla " + this.getNombre() + " y paga " + (this.getValor() + (10*this.getValor()/100)));
            this.hipotecada = false;
            }
        } else {
            System.out.println("La casilla no está hipotecada.");
        } 
    }
}
