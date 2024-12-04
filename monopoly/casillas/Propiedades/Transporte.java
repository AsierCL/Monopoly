package monopoly.casillas.Propiedades;

import java.util.ArrayList;

import monopoly.Juego;
import monopoly.Tablero;
import partida.Jugador;

public class Transporte extends Propiedad {

    public Transporte(String nombre, int posicion, Jugador duenho, float valor) {
        super(nombre, posicion, duenho, valor);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero,
            ArrayList<Jugador> jugadores) {
        haEstado(actual, jugadores);

        if (this.getHipotecada()) {
            return true;
        }
        float pago = 0;
        if (!this.getDuenho().equals(actual) && !this.getDuenho().equals(banca)) {// Casilla de otro
            pago = this.getValor() * 0.1f * this.numTransporte();
            if ((actual.getFortuna() - pago) < 0) {
                Juego.consola
                        .print("Dinero insuficiente para pagar, debes vender propiedades o declararte en bancarrota.");
                return false;
            } else {
                Juego.consola.print("Pagas impuesto de casilla: -" + pago + "€");
                actual.incrementarPagoTasasEImpuestos(pago);
                this.getDuenho().incrementarCobroDeAlquileres(pago);
                registrarIngreso(pago);
                ///////////////////// REVISAR  //////////////////////////////
            }
        }
        return true;
    }

    @Override
    public String infoCasilla() {
        StringBuilder info = new StringBuilder();

        info.append("Nombre: ").append(this.getNombre()).append("\n");
        info.append("Posición: ").append(this.getPosicion()).append("\n");
        info.append("Tipo: Transporte").append("\n");

        info.append("Valor de compra: ").append(this.getValor()).append("\n");
        info.append("Impuesto: ").append(this.getValor() * 0.1f * this.numTransporte()).append("\n");
        if (this.getDuenho() != null) {
            info.append("Dueño: ").append(this.getDuenho().getNombre()).append("\n");
        } else {
            info.append("Dueño: Banca\n");
        }

        return info.toString();
    }

    private int numTransporte() {
        return this.getGrupo().numEnPropiedadGrupo(this.getDuenho());
    }

}
