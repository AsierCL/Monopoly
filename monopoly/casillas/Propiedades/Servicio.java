package monopoly.casillas.Propiedades;

import java.util.ArrayList;

import monopoly.Tablero;
import partida.Jugador;

public class Servicio extends Propiedad{

    public Servicio(String nombre, int posicion, Jugador duenho, float valor){
        super(nombre, posicion, duenho, valor);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> jugadores) {
        haEstado(actual, jugadores);
        
        if (this.getHipotecada()){
            return true;
        }
        float pago = 0;
        if(!this.getDuenho().equals(actual) && !this.getDuenho().equals(banca)){// Casilla de otro
            pago = this.getValor() * 0.1f * this.numServicios()*tirada;
            if((actual.getFortuna()-pago)<0){
                System.out.println("Dinero insuficiente para pagar, debes vender propiedades o declararte en bancarrota.");
                return false;
            }else{
                System.out.println("Pagas impuesto de casilla: -" + pago + "€");
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
        info.append("Tipo: Servicio").append("\n");

        info.append("Valor de compra: ").append(this.getValor()).append("\n");
        info.append("Impuesto: ").append(this.getValor() * 0.1f * this.numServicios()).append("x tirada").append("\n");
        if (this.getDuenho() != null) {
            info.append("Dueño: ").append(this.getDuenho().getNombre()).append("\n");
        } else {
            info.append("Dueño: Banca\n");
        }

        return info.toString();
    }
    
    private int numServicios(){
        return this.getGrupo().numEnPropiedadGrupo(this.getDuenho());
    }

}
