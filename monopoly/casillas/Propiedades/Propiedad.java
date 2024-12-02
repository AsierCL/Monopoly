package monopoly.casillas.Propiedades;

import java.util.ArrayList;

import monopoly.Juego;
import monopoly.Grupo;
import monopoly.Tablero;
import monopoly.exceptions.DuenhoCasilla;
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

    public void hipotecarCasilla(Jugador solicitante) { //SOBREESCRIBIR EN SOLAR PARA CHECKEAR SI ESTÁ CONSTRUIDA
        if (this.getDuenho().equals(solicitante)) {
            if (this.hipotecada == true) {
            } else {
                solicitante.setFortuna(solicitante.getFortuna() + this.getValor());
                this.hipotecada = true;
                Juego.consola.print(solicitante.getNombre() + " ha hipotecado la casilla " + this.getNombre() + " y recibe " + this.getValor() + ".");
            }
        } else {
            Juego.consola.print("No eres propietario de esta casilla.");
        }
       
    }

    public void deshipotecarCasilla(Jugador solicitante){
        if(this.hipotecada == true) {
            if (this.getDuenho() != solicitante) {
                Juego.consola.print("No puedes deshipotecar la casilla si no eres su dueño.");    
            } else if (this.getDuenho() == solicitante) {
            solicitante.setFortuna(solicitante.getFortuna() - (this.getValor() + (10*this.getValor()/100)));
            Juego.consola.print(solicitante.getNombre() + " ha deshipotecado la casilla " + this.getNombre() + " y paga " + (this.getValor() + (10*this.getValor()/100)));
            this.hipotecada = false;
            }
        } else {
            Juego.consola.print("La casilla no está hipotecada.");
        } 
    }

    public void cambiarDuenho(Jugador nuevoduenho){
        this.getDuenho().getPropiedades().remove(this);
        this.setDuenho(nuevoduenho);
        nuevoduenho.anhadirPropiedad(this);
    }

    public boolean comprarPropiedad(Jugador jugador, Jugador banca){
        if(!jugador.getAvatar().getLugar().equals(this)){
            Juego.consola.print("Debes de estar en la casilla para comprarla");
            return false;
        }
        if(!this.getDuenho().equals(banca)){
            Juego.consola.print("La casilla no está en venta");
            return false;
        }
        if(jugador.getFortuna() < valor){
            Juego.consola.print("No tienes dinero suficiente");
            return false;
        }
        banca.eliminarPropiedad(this);
        banca.sumarFortuna(valor);
        jugador.anhadirPropiedad(this);
        jugador.incrementarDineroInvertido(this.valor);
        setDuenho(jugador);
        Juego.consola.print("Has comprado la casilla " + this.getNombre() + " por " + this.valor);
        return true;
    }
}
