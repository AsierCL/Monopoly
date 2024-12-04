package partida.avatares;

import monopoly.Juego;
import monopoly.Tablero;
import monopoly.casillas.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Esfinge extends Avatar {

    public Esfinge(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("esfinge", jugador, lugar, avCreados);
    }

    @Override
    public int moverEnAvanzado(int resultadoTotal, int faltaPorMover, ArrayList<ArrayList<Casilla>> casillas, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores) {
        // Lógica específica para Esfinge
        Juego.consola.error("El movimiento avanzado de la esfige no ha sido implementado");
        return 0;
    }
}