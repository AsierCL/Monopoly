package monopoly.casillas.Especiales;

import java.util.ArrayList;

import monopoly.Juego;
import monopoly.Tablero;
import partida.Jugador;

public class Parking extends Especial {
    float bote;

    public Parking(String nombre, int posicion, Jugador duenho) {
        super(nombre, posicion, duenho);
        this.bote = 0;
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero,
            ArrayList<Jugador> jugadores) {
        haEstado(actual, jugadores);

        Juego.consola.print("Recibes el bote del parking: +" + this.bote + "€");
        actual.incrementarPremiosInversionesOBote(this.bote);
        this.bote = 0;

        return true;
    }

    @Override
    public String infoCasilla() {
        // Creamos a cadena a devolveParkingr
        StringBuilder info = new StringBuilder();

        info.append("Nombre: ").append(this.getNombre()).append("\n");
        info.append("Posición: ").append(this.getPosicion()).append("\n");
        info.append("Tipo: Especial").append("\n");

        return info.toString();
    }

    public void aumentarBote(int cantidad, Tablero tablero) {
        bote += cantidad;
    }
}