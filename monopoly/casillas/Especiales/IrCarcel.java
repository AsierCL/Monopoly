package monopoly.casillas.Especiales;

import java.util.ArrayList;

import monopoly.Juego;
import monopoly.Tablero;
import partida.Jugador;

public class IrCarcel extends Especial {
    public IrCarcel(String nombre, int posicion, Jugador duenho) {
        super(nombre, posicion, duenho);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero,
            ArrayList<Jugador> jugadores) {
        haEstado(actual, jugadores);

        Juego.consola.print("VAS A LA CARCEL");
        actual.encarcelar(tablero.getPosiciones());

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
