package monopoly.casillas.Especiales;

import java.util.ArrayList;

import monopoly.Tablero;
import monopoly.casillas.*;
import partida.Jugador;

public abstract class IrCarcel extends Casilla {
    public IrCarcel(String nombre, int posicion, Jugador duenho, float valor){
        super(nombre, posicion, duenho);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> jugadores){
        haEstado(actual, jugadores);

        System.out.println("VAS A LA CARCEL");
        actual.encarcelar(tablero.getPosiciones());
        
        return true;
    }

    @Override
    public String infoCasilla(){
        //Creamos a cadena a devolver
        StringBuilder info = new StringBuilder();

        info.append("Nombre: ").append(this.getNombre()).append("\n");
        info.append("Posición: ").append(this.getPosicion()).append("\n");
        info.append("Tipo: Especial").append("\n");

        return info.toString();
    }
}
