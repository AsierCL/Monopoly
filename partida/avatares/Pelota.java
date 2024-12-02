package partida.avatares;

import monopoly.Tablero;
import monopoly.casillas.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Pelota extends Avatar {

    public Pelota(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("pelota", jugador, lugar, avCreados);
    }

    /*@Override
    public boolean moverEnAvanzado(ArrayList<ArrayList<Casilla>> casillas, int resultadoTotal, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores) {
        // Lógica específica del movimiento avanzado para "Pelota"
        if (resultadoTotal > 4){ // El avatar avanza hasta resultadoTotal parando en las casillas intermedias
            if(!moverEnBasico(casillas, 5, banca, tablero, jugadores)){ // Primero avanza 5 casillas (primer impar mayor que 4, parará siempre ahí)
                return false;
            }    
            //System.out.println(tablero);
            turnoIntermedio(this, this.getLugar(), false); //turno intermedio en la misma tirada para dar la opción de comprar
            if (this.getJugador().getEnCarcel()) return true;
    
            for (int i=7; i <= resultadoTotal; i+=2){ // Mover el avatar por los números impares hasta llegar a resultadoTotal.
                if(!moverEnBasico(casillas, 2, banca, tablero, jugadores)){
                    return false;
                }
                if (i!= resultadoTotal) {
                    //System.out.println(tablero); 
                    turnoIntermedio(this, this.getLugar(), false); // Turno intermedio para poder comprar en cada una de las tiradas
                    if (this.getJugador().getEnCarcel()) return true;
                }
            }
            if (resultadoTotal % 2 == 0){ // Si el resultado total es par avanzar una casilla más para terminar en él
                if(!moverEnBasico(casillas, 1, banca, tablero, jugadores)){
                    return false;
                }
                //System.out.println(tablero);
            }
        } else { // Si el resultadoTotal es menor que 4
            if(!moverEnBasico(casillas, -1, banca, tablero, jugadores)){ // retroceder una casilla para empezar en 3
                return false;
            }
            //System.out.println(tablero);
            turnoIntermedio(this, this.getLugar(), false); // Turno intermedio para poder comprar entre tiradas
            if (this.getJugador().getEnCarcel()) return true;
            
            for (int i=3; i <= resultadoTotal; i+=2){ // Ir retrocediendo de dos en dos a partir de ahí
                moverEnBasico(casillas, -2, banca, tablero, jugadores);
                if (i!= resultadoTotal) {
                    //System.out.println(tablero); 
                    turnoIntermedio(this, this.getLugar(), false); // Turno intermedio para poder comprar entre tiradas
                    if (this.getJugador().getEnCarcel()) return true;
                }
            }
            if (resultadoTotal % 2 == 0){ // Si el resultado total es par retroceder una casilla más para terminar en él
                if(!moverEnBasico(casillas, -1, banca, tablero, jugadores)){
                    return false;
                }
                //System.out.println(tablero);
            }
        }
    }*/

    @Override
    public int moverEnAvanzado(int resultadoTotal, int faltaPorMover, ArrayList<ArrayList<Casilla>> casillas, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores){
        if(resultadoTotal > 4){
            if(resultadoTotal == faltaPorMover){
                moverEnBasico(casillas, 5, banca, tablero, jugadores);
                faltaPorMover -= 5;
                return faltaPorMover;
            }
            else if(faltaPorMover > 1){
                moverEnBasico(casillas, 2, banca, tablero, jugadores);
                faltaPorMover -= 2;
                return faltaPorMover;
            }
            else if(faltaPorMover == 1){
                moverEnBasico(casillas, 1, banca, tablero, jugadores);
                faltaPorMover -= 1;
                return faltaPorMover;
            }
        } else {
            if(resultadoTotal == faltaPorMover){
                moverEnBasico(casillas, -1, banca, tablero, jugadores);
                faltaPorMover -= 1;
                return faltaPorMover;
            }
            else if(faltaPorMover > 1){
                moverEnBasico(casillas, -2, banca, tablero, jugadores);
                faltaPorMover -= 2;
                return faltaPorMover;
            }
            else if(faltaPorMover == 1){
                moverEnBasico(casillas, -1, banca, tablero, jugadores);
                faltaPorMover -= 1;
                return faltaPorMover;
            }
        }
        return faltaPorMover;
    }
}