package partida.cartas;

import java.util.ArrayList;
import java.util.Collections;

import monopoly.Juego;
import monopoly.Tablero;
import partida.Jugador;

public class Baraja {

    private ArrayList<Carta> cartasSuerte;
    private ArrayList<Carta> cartasComunidad;

    public Baraja(){
        cartasSuerte = new ArrayList<>();
        cartasComunidad = new ArrayList<>();
        crearCartas(); //Crear cartas al inicializar
    }

    private void crearCartas(){
        //Cartas suerte
        for (int i=0; i < 6; i++){
        cartasSuerte.add(new CartaSuerte(i));
        }

        //Cartas comunidad
        for (int i=0; i < 6; i++){
            cartasComunidad.add(new CartaCajaComunidad(i));
        }
    }

    public void ejecutarAccionCarta(String tipoCarta, int indice, boolean barajar, Jugador jugador, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores, int tirada) {
        ArrayList<Carta> cartas;

        // Determinar qué tipo de cartas usar
        if (tipoCarta.equalsIgnoreCase("suerte")) {
            cartas = cartasSuerte;
        } else if (tipoCarta.equalsIgnoreCase("comunidad")) {
            cartas = cartasComunidad;
        } else {
            Juego.consola.print("Tipo de carta no reconocido.");
            return;
        }

        // Verificar si el índice es válido
        if (indice < 1 || indice > cartas.size()) {
            Juego.consola.print("Debes introducir un número del 1 al 6.");
            return;
        }

        // Barajar el array si se especifica
        if (barajar) {
            Collections.shuffle(cartas);
            Juego.consola.print("Se han barajado las cartas.");
        }

        // Ejecutar la acción de la carta seleccionada
        Carta cartaSeleccionada = cartas.get(indice - 1); // Convertir índice a base 0
        Juego.consola.print("Has seleccionado la carta de " + cartaSeleccionada.getTipo() + ".");
        cartaSeleccionada.accion(jugador, banca, tablero, jugadores, tirada);
    }
}
