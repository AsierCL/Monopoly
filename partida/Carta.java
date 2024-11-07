package partida;

import monopoly.*;

import java.util.ArrayList;

public class Carta {
    private String tipo;
    private int accion;

    public Carta(String tipo, int accion) {
        this.tipo = tipo;
        this.accion = accion;
    }

    public String getTipo(){
        return this.tipo;
    }

    public int getAccion() {
        return this.accion;
    }

    public void ejecutarAccion(Jugador jugador, Tablero tablero, ArrayList<Jugador> jugadores) {
        switch (accion) {
            case 1:
                if (tipo.equals("Suerte")) {
                    Casilla casillaOrigen = jugador.getAvatar().getLugar();
                    Casilla casillaTransporte = tablero.obtenerCasilla("Estacion1");

                    jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                    jugador.getAvatar().setLugar(casillaTransporte);
                    casillaTransporte.anhadirAvatar(jugador.getAvatar());

                    // Verificar paso por la casilla de salida
                    jugador.cobrarPasoPorSalida(casillaOrigen, casillaTransporte);

                    System.out.println("Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");


                } else if (tipo.equals("Caja de Comunidad")) {
                    jugador.incrementarPagoTasasEImpuestos(500000);
                    System.out.println("Pagas 500000€ por un fin de semana en un balneario.");
                }
                break;

            case 2:
                if (tipo.equals("Suerte")) {

                    Casilla casillaSolar = tablero.obtenerCasilla("Solar15");
                    jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                    jugador.getAvatar().setLugar(casillaSolar);
                    casillaSolar.anhadirAvatar(jugador.getAvatar());

                    System.out.println("Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                } else if (tipo.equals("Caja de Comunidad")) {
                    jugador.encarcelar(tablero.getPosiciones()); // Ve a la Cárcel
                    System.out.println("Te investigan por fraude de identidad. Vas a la Cárcel.");
                    System.out.println("Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual");
                }
                break;

            case 3:
                if (tipo.equals("Suerte")) {
                    jugador.incrementarPagoTasasEImpuestos(500000); //Falta venderlo para Solar17
                    System.out.println("Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€");
                } else if (tipo.equals("Caja de Comunidad")) {

                    Casilla casillaSalida = tablero.obtenerCasilla("Salida");
                    jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                    jugador.getAvatar().setLugar(casillaSalida);
                    casillaSalida.anhadirAvatar(jugador.getAvatar());

                    System.out.println("Te colocas en la casilla de Salida y cobras la cantidad habitual.");
                }
                break;

            case 4:
                if (tipo.equals("Suerte")) {

                    Casilla casillaSolar = tablero.obtenerCasilla("Solar3");
                    jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                    jugador.getAvatar().setLugar(casillaSolar);
                    casillaSolar.anhadirAvatar(jugador.getAvatar());

                    System.out.println("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");


                } else if (tipo.equals("Caja de Comunidad")) {
                    jugador.incrementarPremiosInversionesOBote(2000000);
                    System.out.println("Tu compañía de Internet obtiene beneficios. Recibes 2000000€.");
                }
                break;

            case 5:
                if (tipo.equals("Suerte")) {
                    jugador.encarcelar(tablero.getPosiciones());
                    System.out.println("Los acreedores te persiguen. Vas a la Cárcel.");
                } else if (tipo.equals("Caja de Comunidad")) {
                    jugador.incrementarPagoTasasEImpuestos(1000000);
                    System.out.println("Invitas a tus amigos a un viaje a Solar14. Pagas 1000000€.");
                }
                break;

            case 6:
                if (tipo.equals("Suerte")) {
                    jugador.incrementarPremiosInversionesOBote(1000000);
                    System.out.println("¡Has ganado el bote de la lotería! Recibes 1000000€!");
                } else if (tipo.equals("Caja de Comunidad")) {
                    pagarATodosLosJugadores(jugador, jugadores, 200000);
                    System.out.println(" Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador");
                }
                break;

            default:
                System.out.println("Acción no reconocida.");
                break;
        }
    }

    public void pagarATodosLosJugadores(Jugador jugadorPagador, ArrayList<Jugador> jugadores, int cantidad) {

        for (Jugador jugador : jugadores) {
            if (!jugador.equals(jugadorPagador)) {
                jugadorPagador.incrementarPagoTasasEImpuestos(cantidad); // Descontamos el monto al jugador que paga
                jugador.incrementarPremiosInversionesOBote(cantidad); // Agregamos el monto a cada jugador
                System.out.println(jugadorPagador.getNombre() + " ha pagado " + cantidad + "€ a " + jugador.getNombre());
            }
        }
    }
}