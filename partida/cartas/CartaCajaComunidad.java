package partida.cartas;

import monopoly.*;
import partida.Jugador;
import monopoly.casillas.*;

import java.util.ArrayList;

public class CartaCajaComunidad extends Carta {
    private int accion;

    public CartaCajaComunidad(int accion) {
        super("Comunidad");
        this.accion = accion;
    }

    public int getAccion() {
        return accion;
    }

    @Override
    public void accion(Jugador jugador, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores, int tirada) {
        switch (accion) {
            case 1:
                jugador.incrementarPagoTasasEImpuestos(500000);
                System.out.println("\nPagas 500000€ por un fin de semana en un balneario.");
                break;

            case 2:
                jugador.encarcelar(tablero.getPosiciones());
                System.out.println("\nTe investigan por fraude de identidad. Vas a la Cárcel.");
                System.out.println("Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                break;

            case 3:
                Casilla casillaOrigen = jugador.getAvatar().getLugar();
                Casilla casillaSalida = tablero.obtenerCasilla("Salida");

                jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                jugador.getAvatar().setLugar(casillaSalida);
                casillaSalida.anhadirAvatar(jugador.getAvatar());
                casillaSalida.evaluarCasilla(jugador, banca, tirada, tablero, jugadores);

                System.out.println("\nTe colocas en la casilla de Salida y cobras la cantidad habitual.");
                jugador.cobrarPasoPorSalida(casillaOrigen, casillaSalida);
                break;
            
            case 4:
                jugador.incrementarPremiosInversionesOBote(2000000);
                System.out.println("\nTu compañía de Internet obtiene beneficios. Recibes 2000000€.");
                break;

            case 5:
                jugador.incrementarPagoTasasEImpuestos(1000000);
                System.out.println("\nInvitas a tus amigos a un viaje a Solar14. Pagas 1000000€.");
                break;

            case 6:
                System.out.println("\nAlquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador\n");
                pagarATodosLosJugadores(jugador, jugadores, 200000);
                break;


            default:
                System.out.println("Acción de carta Comunidad no reconocida.");
                break;
        }
    }

    /**
     * Paga una cantidad a todos los jugadores excepto al jugador que realiza el pago.
     * @param jugadorPagador El jugador que realiza el pago.
     * @param jugadores La lista de jugadores en la partida.
     * @param cantidad La cantidad a pagar a cada jugador.
     */
    private void pagarATodosLosJugadores(Jugador jugadorPagador, ArrayList<Jugador> jugadores, int cantidad) {
        for (Jugador jugador : jugadores) {
            if (!jugador.equals(jugadorPagador)) {
                jugadorPagador.incrementarPagoTasasEImpuestos(cantidad); 
                jugador.incrementarPremiosInversionesOBote(cantidad); 
                System.out.println(jugadorPagador.getNombre() + " ha pagado " + cantidad + "€ a " + jugador.getNombre());
            }
        }
    }
}
