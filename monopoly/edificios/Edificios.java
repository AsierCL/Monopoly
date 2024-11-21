package monopoly.edificios;

import monopoly.casillas.Casilla;
import partida.*;

import java.util.Set;

public class Edificios {
    private Casilla casilla;
    private Casa casas;
    private Hotel hoteles;
    private Piscina piscinas;
    private Pista pistas;
    int numMax;

    public Edificios(Casilla casilla) {
        this.casilla = casilla;
        numMax = casilla.getGrupo().getNumCasillas();
        this.casas = new Casa(numMax);
        this.hoteles = new Hotel(numMax);
        this.piscinas = new Piscina(numMax);
        this.pistas = new Pista(numMax);
    }

    public boolean construirCasa() {
        return casas.Construir();
    }

    public boolean construirHotel() {
        return hoteles.Construir();
    }

    public boolean construirPiscina() {
        return piscinas.Construir();
    }

    public boolean construirPista() {
        return pistas.Construir();
    }

    public int getCantidadCasas() {
        return casas.getCantidad();
    }

    public int getCantidadHoteles() {
        return hoteles.getCantidad();
    }

    public int getCantidadPiscinas() {
        return piscinas.getCantidad();
    }

    public int getCantidadPistas() {
        return pistas.getCantidad();
    }
    
    public Casilla getCasilla() {
        return casilla;
    }
    
    public int getnumMax() {
        return numMax;
    }
    
    public void setnumMax(int numMax) {
        this.numMax = numMax;
    }

    public boolean EsSolarEdificado(){
        if(casas.getCantidad()==0 && hoteles.getCantidad()==0 && piscinas.getCantidad()==0 && piscinas.getCantidad()==0){
            return false;
        }else{
            return true;
        }
    }

    public boolean ConstruirCasa(){
        if(casilla.getGrupo().getCasasGrupo() >= numMax+4 && casilla.getGrupo().getHotelesGrupo() == numMax){
            System.out.println("No puedes construir más casas en este solar");
            return false;
        }else if(casas.getCantidad() == 4 && casilla.getGrupo().getHotelesGrupo() < numMax){
            System.out.println("Debes construir un hotel");
            return false;
        }else{
            casas += 1;
            return true;
        }
    }

    public boolean ConstruirHotel(){
        if(casilla.getGrupo().getHotelesGrupo() == numMax){
            System.out.println("No puedes construir más hoteles en este solar");
            return false;
        }else if(casilla.getGrupo().getHotelesGrupo() < numMax && casas.getCantidad() != 4){
            System.out.println("Debes tener 4 casas antes");
            return false;
        }else if(casilla.getGrupo().getHotelesGrupo() == 1 && casilla.getGrupo().getCasasGrupo() > numMax+4){
            System.out.println("Superas el máximo de casas. Debes eliminarlas antes de construir el hotel");
            return false;
        }else if(casas.getCantidad() == 4 && casilla.getGrupo().getHotelesGrupo() < numMax && casilla.getGrupo().getCasasGrupo() <= numMax+4){
            hoteles += 1;
            casas = 0;
            return true;
        }else{
            System.out.println("CASO NON CONTEMPLADO, REVISAR CODIGO");
            return false;
        }
    }

    public boolean ConstruirPiscina(){
        if(casilla.getGrupo().getPiscinasGrupo() == numMax){
            System.out.println("No puedes tener más piscinas en este solar");
            return false;
        }else if(hoteles.getCantidad() == 0 || hoteles.getCantidad() == 1 && casas.getCantidad() < 2){
            System.out.println("Debes tener, como mínimo, 1 hotel y 2 casas");
            return false;
        }else{
            piscinas += 1;
            return true;
        }
    }

    public boolean ConstruirPista(){
        if(casilla.getGrupo().getPistasGrupo() == numMax){
            System.out.println("No puedes tener más pistas en este solar");
            return false;
        }else if(hoteles.getCantidad() < 2){
            System.out.println("Debes tener, como mínimo, 2 hoteles");
            return false;
        }else{
            pistas += 1;
            return true;
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "\t" + "|Casas=" + casas.getCantidad() + "|Hoteles=" + hoteles.getCantidad() + "|Piscinas=" + piscinas.getCantidad() + "|Pistas=" + pistas.getCantidad();
    }
}