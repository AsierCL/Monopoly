package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.Set;

import monopoly.casillas.Casilla;
import monopoly.casillas.Propiedades.*;

public class Grupo {

    // Atributos
    private ArrayList<Propiedad> miembros; // Casillas miembros del grupo.
    private String colorGrupo; // Color del grupo
    private int numCasillas; // Número de casillas del grupo.
    public static final Set<String> coloresValidos = Set.of("Marron", "Cian", "Morado", "Gris", "Rojo", "Amarillo",
            "Verde", "Azul");

    // Atributos para estadísticas
    private float ingresosTotales;

    // Constructor vacío.
    public Grupo() {
        this.miembros = new ArrayList<>();
        this.colorGrupo = "";
        this.numCasillas = -1;

        this.ingresosTotales = 0;
    }

    // IDEA GPT
    /*
     * public Grupo(ArrayList<Propiedad> casillas, String colorGrupo) {
     * this.miembros = casillas;
     * this.colorGrupo = colorGrupo;
     * this.numCasillas = casillas.size();
     * for (Propiedad casilla : casillas) {
     * casilla.setGrupo(this);
     * // Si es una casilla Solar, inicializa edificios
     * if (casilla instanceof Solar) {
     * ((Solar) casilla).getEdificios().setNum_max(numCasillas);
     * }
     * }
     * this.ingresosTotales = 0;
     * }
     */

    /*
     * Constructor para cuando el grupo está formado por DOS CASILLAS:
     * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Propiedad cas1, Propiedad cas2, String colorGrupo) {
        this.miembros = new ArrayList<>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.colorGrupo = colorGrupo;
        this.numCasillas = 2;
        cas1.setGrupo(this);
        cas2.setGrupo(this);
        for (Propiedad casilla : miembros) { // OPTIMIZABLE if->for
            if (casilla instanceof Solar) {
                ((Solar) casilla).setNumMax(numCasillas);
            }
        }

        this.ingresosTotales = 0;
    }

    /*
     * Constructor para cuando el grupo está formado por TRES CASILLAS:
     * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Propiedad cas1, Propiedad cas2, Propiedad cas3, String colorGrupo) {
        this.miembros = new ArrayList<>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.miembros.add(cas3);
        this.colorGrupo = colorGrupo;
        this.numCasillas = 3;
        cas1.setGrupo(this);
        cas2.setGrupo(this);
        cas3.setGrupo(this);
        for (Propiedad casilla : miembros) { // OPTIMIZABLE if->for
            if (casilla instanceof Solar) {
                ((Solar) casilla).setNumMax(numCasillas);
            }
        }

        this.ingresosTotales = 0;
    }

    /*
     * Constructor para grupo de estaciones
     */
    public Grupo(Propiedad cas1, Propiedad cas2, Propiedad cas3, Propiedad cas4, String colorGrupo) {
        this.miembros = new ArrayList<>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.miembros.add(cas3);
        this.colorGrupo = colorGrupo;
        this.numCasillas = 4;
        cas1.setGrupo(this);
        cas2.setGrupo(this);
        cas3.setGrupo(this);
        cas4.setGrupo(this);

        this.ingresosTotales = 0;
    }

    public String getColorGrupo() {
        return colorGrupo;
    }

    public int getNumCasillas() {
        return numCasillas;
    }

    public ArrayList<Propiedad> getMiembros() {
        return miembros;
    }

    public float getIngresosTotales() {
        return this.ingresosTotales; // Devuelve el total de ingresos acumulados
    }

    /*
     * Método que añade una casilla al array de casillas miembro de un grupo.
     * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Propiedad casilla) {
        this.miembros.add(casilla);
        this.numCasillas++;
        casilla.setGrupo(this);
    }

    /*
     * Método que comprueba si el jugador pasado tiene en su haber todas las
     * casillas del grupo:
     * Parámetro: jugador que se quiere evaluar.
     * Valor devuelto: true si es dueño de todas las casillas del grupo, false en
     * otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) { // Posible implementacion con numEnPropiedadGrupo()//
        for (Propiedad casilla : miembros) {
            if (!casilla.getDuenho().equals(jugador)) {
                return false;
            }
        }
        return true;
    }

    public int numEnPropiedadGrupo(Jugador jugador) {
        int totalPropiedades = 0;
        for (Propiedad casilla : miembros) {
            if (casilla.getDuenho().equals(jugador)) {
                totalPropiedades++;
            }
        }
        return totalPropiedades;
    }

    public int getCasasGrupo() {
        int total = 0;
        for (Propiedad casilla : getMiembros()) {
            total += ((Solar) casilla).getCasas();
        }
        return total;
    }

    public int getHotelesGrupo() {
        int total = 0;
        for (Propiedad casilla : getMiembros()) {
            total += ((Solar) casilla).getHoteles();
        }
        return total;
    }

    public int getPiscinasGrupo() {
        int total = 0;
        for (Propiedad casilla : getMiembros()) {
            total += ((Solar) casilla).getPiscinas();
        }
        return total;
    }

    public int getPistasGrupo() {
        int total = 0;
        for (Propiedad casilla : getMiembros()) {
            total += ((Solar) casilla).getPistas();
        }
        return total;
    }

    public void registrarIngresosGrupo(float ingresos) {
        this.ingresosTotales += ingresos;
    }

    public void listarEdificiosPorColor(Jugador banca) {
        for (Casilla casilla : getMiembros()) {
            ((Solar) casilla).listarEdificios();
            if (casilla.getDuenho().equals(banca)) {
                Juego.consola.print("Casilla en venta");
            } else {
                Juego.consola.print("El pago es de: " + ((Solar) casilla).calcularPagoSolar(casilla.getDuenho()));
            }
        }

        if (getHotelesGrupo() == getNumCasillas()) {
            Juego.consola.print("Puedes construir " + (getNumCasillas() - getCasasGrupo()) + " casas.");
            Juego.consola.print("Puedes construir " + (getNumCasillas() - getHotelesGrupo()) + " hoteles.");
            Juego.consola.print("Puedes construir " + (getNumCasillas() - getPiscinasGrupo()) + " piscinas.");
            Juego.consola.print("Puedes construir " + (getNumCasillas() - getPistasGrupo()) + " pistas.");
        } else {
            Juego.consola.print("Puedes construir "
                    + (4 * getNumCasillas() + getNumCasillas() - (getCasasGrupo() + 4 * getHotelesGrupo()))
                    + " casas.");
            Juego.consola.print("Puedes construir " + (getNumCasillas() - getHotelesGrupo()) + " hoteles.");
            Juego.consola.print("Puedes construir " + (getNumCasillas() - getPiscinasGrupo()) + " piscinas.");
            Juego.consola.print("Puedes construir " + (getNumCasillas() - getPistasGrupo()) + " pistas.");
        }
    }

}
