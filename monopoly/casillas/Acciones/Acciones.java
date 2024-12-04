package monopoly.casillas.Acciones;

import monopoly.casillas.Casilla;
import partida.Jugador;
import monopoly.Tablero;
import java.util.ArrayList;

public abstract class Acciones extends Casilla {

    public Acciones(String nombre, int posicion, Jugador duenho) {
        super(nombre, posicion, duenho);
    }

    @Override
    public abstract String infoCasilla();

    @Override
    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero,
            ArrayList<Jugador> jugadores);

}
