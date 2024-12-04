package monopoly.casillas.Acciones;

import java.util.ArrayList;

import monopoly.Juego;
import monopoly.Tablero;
import partida.Jugador;
import partida.cartas.*;

public class Suerte extends Acciones {

    public Suerte(String nombre, int posicion, Jugador duenho) {
        super(nombre, posicion, duenho);
    }

    @Override
    public String infoCasilla() {
        StringBuilder info = new StringBuilder();
        info.append("Nombre: ").append(this.getNombre()).append("\n");
        info.append("Posici\u00f3n: ").append(this.getPosicion()).append("\n");
        info.append("Tipo: ").append(this.getClass().getName()).append("\n");

        return info.toString();
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> jugadores) {
        Juego.consola.print("TARJETA DE SUERTE\n");

        int var10;
        do {
            do {
                var10 = Integer.parseInt(Juego.consola.read("Introduce un n\u00famero del 1 al 6 para seleccionar una carta de Suerte: "));
            } while (var10 < 1);
        } while (var10 > 6);

        Carta var11 = new CartaSuerte(var10);
        var11.accion(actual, banca, tablero, jugadores, tirada);
        return true;
    }
}
