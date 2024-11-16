package partida.cartas;

import monopoly.Tablero;
import partida.Jugador;

import java.util.ArrayList;

public abstract class Carta {
    private String tipo;

    public Carta(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    // Método abstracto que debe ser implementado por las subclases
    public abstract void ejecutarAccion(Jugador jugador, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores, int tirada);
}