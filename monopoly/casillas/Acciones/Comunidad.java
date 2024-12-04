package monopoly.casillas.Acciones;

import java.util.ArrayList;

import monopoly.Juego;
import monopoly.Tablero;
import partida.Jugador;
import partida.cartas.*;

public class Comunidad extends Acciones {

    public Comunidad(String nombre, int posicion, Jugador duenho) {
        super(nombre, posicion, duenho);
    }

    @Override
    public String infoCasilla() {
        StringBuilder info = new StringBuilder();
        info.append("Nombre: ").append(this.getNombre()).append("\n");
        info.append("Posici\u00f3n: ").append(this.getPosicion()).append("\n");
        info.append("Tipo: ").append(this.getClass().getName()).append("\n");
        info.append("Descripci\u00f3n: Casilla de comunidad.\n");

        return info.toString();
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero,
            ArrayList<Jugador> jugadores) {
        Juego.consola.print("TARJETA DE COMUNIDAD\n");
        int var13;
        do {
            do {
                var13 = Integer.parseInt(Juego.consola
                        .read("Introduce un n\u00famero del 1 al 6 para seleccionar una carta de Comunidad: "));
            } while (var13 < 1);
        } while (var13 > 6);

        Carta var14 = new CartaCajaComunidad(var13);
        var14.accion(actual, banca, tablero, jugadores, tirada);
        return true;
    }

}