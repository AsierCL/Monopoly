package monopoly;

import partida.*;
import java.util.ArrayList;


public class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.

    //Constructor vacío.
    public Grupo() {
        this.miembros = new ArrayList<>();
        this.colorGrupo = "";
        this.numCasillas = 0;
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        this.miembros = new ArrayList<>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.colorGrupo = colorGrupo;
        this.numCasillas = 2;
        cas1.setGrupo(this);
        cas2.setGrupo(this);
        for (Casilla casilla : miembros) {
            casilla.getEdificios().setNum_max(numCasillas);
        }
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
        this.miembros = new ArrayList<>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.miembros.add(cas3);
        this.colorGrupo = colorGrupo;
        this.numCasillas = 3;
        cas1.setGrupo(this);
        cas2.setGrupo(this);
        cas3.setGrupo(this);
        for (Casilla casilla : miembros) {
            casilla.getEdificios().setNum_max(numCasillas);
        }
    }

    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla casilla) {
        this.miembros.add(casilla);
        this.numCasillas++;
        casilla.setGrupo(this);
    }

    /*Método que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        for (Casilla casilla : miembros) {
            if (!casilla.getDuenho().equals(jugador)) {
                return false;
            }
        }
        return true;
    }

    public void setMiembros(ArrayList<Casilla> miembros) {
        this.miembros = miembros; 
        this.numCasillas = miembros.size();
    }

    public String getColorGrupo() {
        return colorGrupo;
    }

    public void setColorGrupo(String colorGrupo) {
        this.colorGrupo = colorGrupo;
    }
    
    public int getNumCasillas() {
        return numCasillas;
    }

    public void setNumCasillas(int numCasillas) {
        this.numCasillas = numCasillas;
    }

    public int getCasasGrupo(){
        int total = 0;
        for (Casilla casilla : miembros) {
            total += casilla.getEdificios().getCasas();
        }
        return total;
    }
    
    public int getHotelesGrupo(){
        int total = 0;
        for (Casilla casilla : miembros) {
            total += casilla.getEdificios().getHoteles();
        }
        return total;
    }
    
    public int getPiscinasGrupo(){
        int total = 0;
        for (Casilla casilla : miembros) {
            total += casilla.getEdificios().getPiscinas();
        }
        return total;
    }
    
    public int getPistasGrupo(){
        int total = 0;
        for (Casilla casilla : miembros) {
            total += casilla.getEdificios().getPistas();
        }
        return total;
    }
}
