package partida.avatares;

import monopoly.casillas.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Pelota extends Avatar {

    public Pelota(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("pelota", jugador, lugar, avCreados);
    }

    @Override
    public void moverEnAvanzado(ArrayList<ArrayList<Casilla>> casillas, int resultadoTotal) {
        // Lógica específica del movimiento avanzado para "Pelota"
        if (resultadoTotal > 4){ // El avatar avanza hasta resultadoTotal parando en las casillas intermedias
            moverAvatarYEvaluar(this, 5, resultadoTotal, casillas); // Primero avanza 5 casillas (primer impar mayor que 4, parará siempre ahí)
            //System.out.println(tablero);
            turnoIntermedio(this, this.getLugar(), false); //turno intermedio en la misma tirada para dar la opción de comprar
            if (this.getJugador().getEnCarcel()) return;
    
            for (int i=7; i <= resultadoTotal; i+=2){ // Mover el avatar por los números impares hasta llegar a resultadoTotal.
                moverAvatarYEvaluar(this, 2, resultadoTotal, casillas);
                if (i!= resultadoTotal) {
                    //System.out.println(tablero); 
                    turnoIntermedio(this, this.getLugar(), false); // Turno intermedio para poder comprar en cada una de las tiradas
                    if (this.getJugador().getEnCarcel()) return;
                }
            }
            if (resultadoTotal % 2 == 0){ // Si el resultado total es par avanzar una casilla más para terminar en él
                this.moverAvatarYEvaluar(this, 1, resultadoTotal, casillas);
                //System.out.println(tablero);
            }
        } else { // Si el resultadoTotal es menor que 4
            moverAvatarYEvaluar(this, -1, resultadoTotal, casillas); // retroceder una casilla para empezar en 3
            //System.out.println(tablero);
            turnoIntermedio(this, this.getLugar(), false); // Turno intermedio para poder comprar entre tiradas
            if (this.getJugador().getEnCarcel()) return;
            
            for (int i=3; i <= resultadoTotal; i+=2){ // Ir retrocediendo de dos en dos a partir de ahí
                moverAvatarYEvaluar(this, -2, resultadoTotal, casillas);
                if (i!= resultadoTotal) {
                    //System.out.println(tablero); 
                    turnoIntermedio(this, this.getLugar(), false); // Turno intermedio para poder comprar entre tiradas
                    if (this.getJugador().getEnCarcel()) return;
                }
            }
            if (resultadoTotal % 2 == 0){ // Si el resultado total es par retroceder una casilla más para terminar en él
                moverAvatarYEvaluar(this, -1, resultadoTotal, casillas);
                //System.out.println(tablero);
            }
        }
    }
}