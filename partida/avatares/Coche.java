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

    /*@Override
    public boolean moverEnAvanzado(ArrayList<ArrayList<Casilla>> casillas, int resultadoTotal, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores) {
        // Lógica específica del movimiento avanzado para "Coche"
        Scanner scanDado = new Scanner(System.in);
        int contador = 0; // Contador del número de veces que se ha sacado más de 4
        int resultadoDado1 = 0, resultadoDado2 = 0;
        boolean haComprado = false; // Controla si ya ha comprado una propiedad en este turno 

        Jugador jugador = this.getJugador();

        if (resultadoTotal <= 4){ // Si el resultado total es menor o igual a 4 retrocede esa cantidada
            if(!moverEnBasico(casillas, -resultadoTotal, banca, tablero, jugadores)){
                return false;
            }
            Juego.consola.print("Has sacado menos de 4, no podrás tirar en los próximos dos turnos");
            jugador.setTurnosBloqueado(2); // Bloqueamos los próximos dos turnos
        } else {
            while (resultadoTotal > 4 && contador < 4){ // Mientras se siga sacando más de 4 y no suceda más de 3 veces
                if(!moverEnBasico(casillas, resultadoTotal, banca, tablero, jugadores)){
                    return false;
                }
                //Juego.consola.print(tablero);
                
                haComprado = turnoIntermedio(this, this.getLugar(), haComprado);

                if (contador < 3){ // En la tirada adicional 1 y 2 no se tienen en cuenta los dados dobles
                    haComprado = turnoIntermedio(this, this.getLugar(), haComprado);
                    System.out.print("Introduzca el valor de la tirada del dado 1: ");
                    resultadoDado1 = scanDado.nextInt();
                    System.out.print("Introduzca el valor de la tirada del dado 2: ");
                    resultadoDado2 = scanDado.nextInt();
                    resultadoTotal = resultadoDado1 + resultadoDado2;
                    Juego.consola.print("\nDADOS: [" + resultadoDado1 + "] " + " [" + resultadoDado2 + "]\n");
                }
                contador++;
    
                if(contador == 3 && resultadoDado1 == resultadoDado2){ // En la última tirada adicional se gestionan los dados dobles
                    if(!moverEnBasico(casillas, resultadoTotal, banca, tablero, jugadores)){
                        return false;
                    }
                    while(resultadoDado1 == resultadoDado2){
                        Juego.consola.print("LLevas " + this.lanzamientos + " dobles");
                        tirado = false;
                        if(this.lanzamientos<3){
                            Juego.consola.print("Vuelve a tirar");
    
                            System.out.print("Introduzca el valor de la tirada del dado 1: ");
                            resultadoDado1 = scanDado.nextInt();
                            System.out.print("Introduzca el valor de la tirada del dado 2: ");
                            resultadoDado2 = scanDado.nextInt();
                            resultadoTotal = resultadoDado1 + resultadoDado2;
                            Juego.consola.print("\nDADOS: [" + resultadoDado1 + "] " + " [" + resultadoDado2 + "]\n");
                            
                            if(!moverEnBasico(casillas, resultadoTotal, banca, tablero, jugadores)){
                                return false;
                            }
                        }else{
                            Juego.consola.print("VAS A LA CARCEL");
                            this.getJugador().encarcelar(casillas);
                            tirado = true;
                            contador++;
                            break;
                        }
                        this.lanzamientos++;
                    }
                }
            }
        }
    }*/

    @Override
    public int moverEnAvanzado2(int resultadoTotal, int faltaPorMover, ArrayList<ArrayList<Casilla>> casillas, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores){
        Jugador jugador = this.getJugador();
        if(resultadoTotal <= 4){
            if(faltaPorMover == resultadoTotal){
                Juego.consola.print("Has sacado menos de 4, no podrás tirar en los próximos dos turnos");
                jugador.setTurnosBloqueado(2); // Bloqueamos los próximos dos turnos
                moverEnBasico(casillas, -resultadoTotal, banca, tablero, jugadores);
                return 0;  //Al sacar menos de 4 no se vuelve a tirar
            } else {
                moverEnBasico(casillas, resultadoTotal, banca, tablero, jugadores);
                return 0;  //Al sacar menos de 4 no se vuelve a tirar
            }
        } else {
            moverEnBasico(casillas, resultadoTotal, banca, tablero, jugadores);
            faltaPorMover = 1; //Mientras saque más de cuatro queda una tirada
            return faltaPorMover;
        }
    }
}