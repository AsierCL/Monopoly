package partida.avatares;

import monopoly.Juego;
import monopoly.Tablero;
import monopoly.casillas.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Sombrero extends Avatar {

    public Sombrero(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("sombrero", jugador, lugar, avCreados);
    }

    @Override
    public int moverEnAvanzado(int resultadoTotal, int faltaPorMover, ArrayList<ArrayList<Casilla>> casillas, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores) {
        // Lógica específica para Sombrero
        Juego.consola.error("El movimiento avanzado del sombrero no ha sido implementado");
        return 0;
    }
}