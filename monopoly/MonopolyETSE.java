package monopoly;

import partida.*;
public class MonopolyETSE {

    public static void main(String[] args) {
        Menu.clearScreen();
        Jugador banca = new Jugador();
        Tablero tablero = new Tablero(banca);
        //Menu.printBanner();
        //Menu.clearScreen();
        System.out.println(tablero);
    }
    
}
