package monopoly.casillas.Propiedades;

import java.util.ArrayList;

import monopoly.Juego;
import monopoly.Grupo;
import monopoly.Tablero;
import monopoly.exceptions.*;
import partida.Jugador;

public abstract class Propiedad extends monopoly.casillas.Casilla {
    private float valor;
    private boolean hipotecada;
    private Grupo grupo;

    public Propiedad(String nombre, int posicion, Jugador duenho, float valor) {
        super(nombre, posicion, duenho);
        this.valor = valor;
        this.hipotecada = false;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public boolean getHipotecada() {
        return hipotecada;
    }

    public void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero,
            ArrayList<Jugador> jugadores);

    public void hipotecarCasilla(Jugador solicitante) throws DuenhoCasilla, CasillaHipotecada { // SOBREESCRIBIR EN
                                                                                                // SOLAR PARA CHECKEAR
                                                                                                // SI ESTÁ CONSTRUIDA
        if (this.getDuenho().equals(solicitante)) {
            if (this.hipotecada == true) {
                throw new CasillaHipotecada();
            } else {
                solicitante.setFortuna(solicitante.getFortuna() + this.getValor());
                this.hipotecada = true;
                Juego.consola.print(solicitante.getNombre() + " ha hipotecado la casilla " + this.getNombre()
                        + " y recibe " + this.getValor() + ".");
            }
        } else {
            throw new DuenhoCasilla();
        }
    }

    public void deshipotecarCasilla(Jugador solicitante) throws DuenhoCasilla, CasillaDeshipotecada {
        if (this.hipotecada == true) {
            if (this.getDuenho() != solicitante) {
                throw new DuenhoCasilla();
            } else if (this.getDuenho() == solicitante) {
                solicitante.setFortuna(solicitante.getFortuna() - (this.getValor() + (10 * this.getValor() / 100)));
                Juego.consola.print(solicitante.getNombre() + " ha deshipotecado la casilla " + this.getNombre()
                        + " y paga " + (this.getValor() + (10 * this.getValor() / 100)));
                this.hipotecada = false;
            }
        } else {
            throw new CasillaDeshipotecada();
        }
    }

    public void cambiarDuenho(Jugador nuevoduenho) {
        this.getDuenho().getPropiedades().remove(this);
        this.setDuenho(nuevoduenho);
        nuevoduenho.anhadirPropiedad(this);
    }

    public boolean comprarPropiedad(Jugador jugador, Jugador banca) throws EstarCasilla, EnVenta, DineroInsuficiente {
        if (!jugador.getAvatar().getLugar().equals(this)) {
            throw new EstarCasilla();
        }
        if (!this.getDuenho().equals(banca)) {
            throw new EnVenta();
        }
        if (jugador.getFortuna() < valor) {
            throw new DineroInsuficiente();
        }
        banca.eliminarPropiedad(this);
        banca.sumarFortuna(valor);
        jugador.anhadirPropiedad(this);
        jugador.incrementarDineroInvertido(this.valor);
        setDuenho(jugador);
        Juego.consola.print("Has comprado la casilla " + this.getNombre() + " por " + this.valor);
        return true;
    }
}
