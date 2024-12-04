package monopoly.casillas;

import java.util.ArrayList;

import monopoly.Juego;
import partida.Jugador;
import monopoly.Tablero;

public class Impuesto extends Casilla {
    float impuesto;

    public Impuesto(String nombre, int posicion, Jugador duenho, float impuesto) {
        super(nombre, posicion, duenho);
        this.impuesto = impuesto;
    }

    @Override
    public String infoCasilla() {
        // Creamos a cadena a devolver
        StringBuilder info = new StringBuilder();

        info.append("Nombre: ").append(this.getNombre()).append("\n");
        info.append("Posición: ").append(this.getPosicion()).append("\n");
        info.append("Tipo: Impuesto").append("\n");

        info.append("Impuesto: ").append(this.impuesto).append("\n");

        return info.toString();
    }

    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero,
            ArrayList<Jugador> jugadores) {// Solucion prvisional
        haEstado(actual, jugadores);
        if ((actual.getFortuna() - this.impuesto) < 0) {
            Juego.consola.print("Dinero insuficiente para pagar, debes vender propiedades o declararte en bancarrota.");
            return false;
        } else {
            Juego.consola.print("Pagas impuesto de casilla: -" + this.impuesto + "€");
            actual.incrementarPagoTasasEImpuestos(this.impuesto);
            ////////////////////////////////  REVISAR ESTO  ////////////////////////////////
            //////////tablero.obtenerCasilla("Parking").sumarValor(this.impuesto);//////////
            ////////////////////////////////////////////////////////////////////////////////
            registrarIngreso(this.impuesto);
        }
        return true;
    }
}
