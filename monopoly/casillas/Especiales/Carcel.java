package monopoly.casillas.Especiales;

import java.util.ArrayList;

import monopoly.Tablero;
import partida.Jugador;

public class Carcel extends Especial {

    public Carcel(String nombre, int posicion, Jugador duenho) {
        super(nombre, posicion, duenho);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero,
            ArrayList<Jugador> jugadores) {
        haEstado(actual, jugadores);
        return true;
    }

    @Override
    public String infoCasilla() {
        // Creamos a cadena a devolver
        StringBuilder info = new StringBuilder();

        info.append("Nombre: ").append(this.getNombre()).append("\n");
        info.append("Posición: ").append(this.getPosicion()).append("\n");
        info.append("Tipo: Especial").append("\n");

        return info.toString();
    }
}