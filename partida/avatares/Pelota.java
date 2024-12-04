package partida.avatares;

import monopoly.Tablero;
import monopoly.casillas.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Pelota extends Avatar {

    public Pelota(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("pelota", jugador, lugar, avCreados);
    }

    @Override
    public int moverEnAvanzado(int resultadoTotal, int faltaPorMover, ArrayList<ArrayList<Casilla>> casillas,
            Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores) {
        if (resultadoTotal > 4) {
            if (resultadoTotal == faltaPorMover) {
                moverEnBasico(casillas, 5, banca, tablero, jugadores);
                faltaPorMover -= 5;
                return faltaPorMover;
            } else if (faltaPorMover > 1) {
                moverEnBasico(casillas, 2, banca, tablero, jugadores);
                faltaPorMover -= 2;
                return faltaPorMover;
            } else if (faltaPorMover == 1) {
                moverEnBasico(casillas, 1, banca, tablero, jugadores);
                faltaPorMover -= 1;
                return faltaPorMover;
            }
        } else {
            if (resultadoTotal == faltaPorMover) {
                moverEnBasico(casillas, -1, banca, tablero, jugadores);
                faltaPorMover -= 1;
                return faltaPorMover;
            } else if (faltaPorMover > 1) {
                moverEnBasico(casillas, -2, banca, tablero, jugadores);
                faltaPorMover -= 2;
                return faltaPorMover;
            } else if (faltaPorMover == 1) {
                moverEnBasico(casillas, -1, banca, tablero, jugadores);
                faltaPorMover -= 1;
                return faltaPorMover;
            }
        }
        return faltaPorMover;
    }
}