package partida.avatares;

import monopoly.casillas.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Esfinge extends Avatar {

    public Esfinge(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("esfinge", jugador, lugar, avCreados);
    }

    @Override
    public void moverEnAvanzado(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        // Lógica específica para Esfinge
    }
}