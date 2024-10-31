package monopoly;

import java.util.Set;

import partida.*;

public class Edificios {
    private Casilla casilla;
    private int casas;
    private int hoteles;
    private int piscinas;
    private int pistas;
    private int num_max;

    public static final Set<String> edificiosValidos = Set.of("casa", "hotel", "piscina", "pista");


    public Edificios(Casilla casilla){
        this.casilla = casilla;
        casas = 0;
        hoteles = 0;
        piscinas = 0;
        piscinas = 0;
    }

    public int getCasas() {
        return casas;
    }

    public int getHoteles() {
        return hoteles;
    }

    public int getPiscinas() {
        return piscinas;
    }
    
    public int getPistas() {
        return pistas;
    }
    
    public Casilla getCasilla() {
        return casilla;
    }
    
    public int getNum_max() {
        return num_max;
    }
    
    public void setNum_max(int num_max) {
        this.num_max = num_max;
    }

    public boolean EsSolarEdificado(){
        if(casas==0 && hoteles==0 && piscinas==0 && piscinas==0){
            return false;
        }else{
            return true;
        }
    }

    public boolean ConstruirCasa(){
        if(casilla.getGrupo().getCasasGrupo() == num_max && casilla.getGrupo().getHotelesGrupo() == num_max){
            System.out.println("No puedes construir más casas en este solar");
            return false;
        }else if(casas == 4 && casilla.getGrupo().getHotelesGrupo() < num_max){
            System.out.println("Debes construir un hotel");
            return false;
        }else{
            casas += 1;
            return true;
        }
    }

    public boolean ConstruirHotel(){
        if(casilla.getGrupo().getHotelesGrupo() == num_max){
            System.out.println("No puedes construir más hoteles en este solar");
            return false;
        }else if(casilla.getGrupo().getHotelesGrupo() < num_max && casas != 4){
            System.out.println("Debes tener 4 casas antes");
            return false;
        }else if(casilla.getGrupo().getHotelesGrupo() == 1 && casilla.getGrupo().getCasasGrupo() > num_max){
            System.out.println("Superas el máximo de casas. Debes eliminarlas antes de construir el hotel");
            return false;
        }else if(casas == 4 && casilla.getGrupo().getHotelesGrupo() < num_max && casilla.getGrupo().getCasasGrupo() <= num_max+4){
            hoteles += 1;
            casas = 0;
            return true;
        }else{
            System.out.println("CASO NON CONTEMPLADO, REVISAR CODIGO");
            return false;
        }
    }

    public boolean ConstruirPiscina(){
        if(casilla.getGrupo().getPiscinasGrupo() == num_max){
            System.out.println("No puedes tener más piscinas en este solar");
            return false;
        }else if(hoteles == 0 || hoteles == 1 && casas < 2){
            System.out.println("Debes tener, como mínimo, 1 hotel y 2 casas");
            return false;
        }else{
            piscinas += 1;
            return true;
        }
    }

    public boolean ConstruirPista(){
        if(casilla.getGrupo().getPistasGrupo() == num_max){
            System.out.println("No puedes tener más pistas en este solar");
            return false;
        }else if(hoteles < 2){
            System.out.println("Debes tener, como mínimo, 2 hoteles");
            return false;
        }else{
            pistas += 1;
            return true;
        }
    }
}
