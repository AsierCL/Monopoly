package partida.avatares;

import monopoly.Juego;
import monopoly.Tablero;
import monopoly.casillas.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Coche extends Avatar {

    public Coche(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("coche", jugador, lugar, avCreados);
    }

    @Override
    public int moverEnAvanzado(int resultadoTotal, int faltaPorMover, ArrayList<ArrayList<Casilla>> casillas, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores){
        Jugador jugador = this.getJugador();
        if(resultadoTotal <= 4){
            if(faltaPorMover == resultadoTotal){
                Juego.consola.print("Has sacado menos de 4, no podrás tirar en los próximos dos turnos");
                jugador.setTurnosBloqueado(2); // Bloqueamos los próximos dos turnos
                moverEnBasico(casillas, -resultadoTotal, banca, tablero, jugadores);
                return 0;  //Al sacar menos de 4 no se vuelve a tirar
            } else {
                moverEnBasico(casillas, resultadoTotal, banca, tablero, jugadores);
                Juego.consola.print("Has sacado menos de 4, no sigues tirando");
                return 0;  //Al sacar menos de 4 no se vuelve a tirar
            }
        } else {
            moverEnBasico(casillas, resultadoTotal, banca, tablero, jugadores);
            faltaPorMover = 1; //Mientras saque más de cuatro queda una tirada
            return faltaPorMover;
        }
    }
}