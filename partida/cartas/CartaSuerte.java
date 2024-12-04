package partida.cartas;

import monopoly.*;
import partida.Jugador;
import monopoly.casillas.*;

import java.util.ArrayList;

public class CartaSuerte extends Carta {
    private int accion;

    public CartaSuerte(int accion) {
        super("Suerte");
        this.accion = accion;
    }

    public int getAccion() {
        return accion;
    }

    @Override
    public void accion(Jugador jugador, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores, int tirada) {
        Casilla casillaOrigen;
        Casilla casillaSolar;
        switch (accion) {
            case 1:
                casillaOrigen = jugador.getAvatar().getLugar();
                Casilla casillaTransporte = tablero.obtenerCasilla("Estacion1");

                jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                jugador.getAvatar().setLugar(casillaTransporte);
                casillaTransporte.anhadirAvatar(jugador.getAvatar());
                casillaTransporte.evaluarCasilla(jugador, banca, tirada, tablero, jugadores);

                Juego.consola.print("\nVe al Estacion1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
                jugador.cobrarPasoPorSalida(casillaOrigen, casillaTransporte);
                break;

            case 2:
                casillaSolar = tablero.obtenerCasilla("Solar15");
                jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                jugador.getAvatar().setLugar(casillaSolar);
                casillaSolar.anhadirAvatar(jugador.getAvatar());
                casillaSolar.evaluarCasilla(jugador, banca, tirada, tablero, jugadores);

                Juego.consola.print("\nDecides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                Juego.consola.print(tablero.toString());
                break;

            case 3:
                jugador.incrementarPremiosInversionesOBote(500000);
                Juego.consola.print("\nVendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€");
                break;
            
            case 4:
                casillaOrigen = jugador.getAvatar().getLugar();
                casillaSolar = tablero.obtenerCasilla("Solar3");

                jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                jugador.getAvatar().setLugar(casillaSolar);
                casillaSolar.anhadirAvatar(jugador.getAvatar());

                casillaSolar.evaluarCasilla(jugador, banca, tirada, tablero, jugadores);

                Juego.consola.print("\nVe a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
                // Verificar paso por la casilla de salida
                jugador.cobrarPasoPorSalida(casillaOrigen, casillaSolar);
                break;

            case 5:
                jugador.encarcelar(tablero.getPosiciones());
                Juego.consola.print("\nLos acreedores te persiguen. Vas a la Cárcel.");
                break;
            
            case 6:
                jugador.incrementarPremiosInversionesOBote(1000000);
                Juego.consola.print("\n¡Has ganado el bote de la lotería! Recibes 1000000€!");
                break;
    
            default:
                Juego.consola.print("Acción de carta Suerte no reconocida.");
                break;
        }
    }
}
