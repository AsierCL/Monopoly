package partida.Tratos;

import java.util.ArrayList;

import monopoly.casillas.Propiedades.Propiedad;
import partida.Jugador;

public class Trato {
    private int id;
    private Jugador jugador_oferta;
    private Jugador jugador_acepta;
    private float cantidad_oferta;
    private float cantidad_acepta;
    private ArrayList<Propiedad> propiedades_oferta;
    private ArrayList<Propiedad> propiedades_acepta;

    public Trato(int id, Jugador jugador_oferta, Jugador jugador_acepta,
            float cantidad_oferta, float cantidad_acepta,
            ArrayList<Propiedad> propiedades_oferta, ArrayList<Propiedad> propiedades_acepta) {
        this.id = id;
        this.jugador_oferta = jugador_oferta;
        this.jugador_acepta = jugador_acepta;
        this.cantidad_oferta = cantidad_oferta;
        this.cantidad_acepta = cantidad_acepta;
        this.propiedades_oferta = propiedades_oferta;
        this.propiedades_acepta = propiedades_acepta;
    }

    public int getId() {
        return id;
    }

    public Jugador getJugador_oferta() {
        return jugador_oferta;
    }

    public Jugador getJugador_acepta() {
        return jugador_acepta;
    }

    public float getCantidad_oferta() {
        return cantidad_oferta;
    }

    public float getCantidad_acepta() {
        return cantidad_acepta;
    }

    public ArrayList<Propiedad> getPropiedades_oferta() {
        return propiedades_oferta;
    }

    public ArrayList<Propiedad> getPropiedades_acepta() {
        return propiedades_acepta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("El trato con id " + id + " propuesto por " + jugador_oferta.getNombre() + " consiste en:\n");
        sb.append("\t" + jugador_oferta.getNombre() + " ofrece: \n");
        sb.append("\t\t");
        sb.append(cantidad_oferta + "€");
        sb.append("\t\t");
        if (propiedades_oferta != null) {
            for (Propiedad propiedad : propiedades_oferta)
                sb.append("|" + propiedad.getNombre());
        }
        sb.append("|\n");

        sb.append("\t" + jugador_acepta.getNombre() + " ofrece: \n");
        sb.append("\t\t");
        sb.append(cantidad_acepta + "€");
        sb.append("\t\t");
        if (propiedades_oferta != null) {
            for (Propiedad propiedad : propiedades_acepta)
                sb.append("|" + propiedad.getNombre());
        }
        sb.append("|\n");
        return sb.toString();
    }
}