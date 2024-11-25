package monopoly.casillas.Especiales;

import java.util.ArrayList;

import monopoly.Tablero;
import monopoly.casillas.*;
import partida.Jugador;

public abstract class Especial extends Casilla {
    public Especial(String nombre, int posicion, Jugador duenho){
        super(nombre, posicion, duenho);
        
    }


    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> jugadores);

    public abstract String infoCasilla();
}
