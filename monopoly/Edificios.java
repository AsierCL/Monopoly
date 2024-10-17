package monopoly;

import partida.*;

public class Edificios {
    private Casilla casilla;
    private int casas;
    private int hoteles;
    private int piscinas;
    private int pistas;
    private int num_max;

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

    public boolean EstaEdificado(){
        if(casas==0 && hoteles==0 && piscinas==0 && piscinas==0){
            return false;
        }else{
            return true;
        }
    }

    public boolean ConstruirCasa(){
        if(casas == num_max && hoteles == num_max){
            System.out.println("No puedes construir más casas en este solar");
            return false;
        }else if(casas == 4 && hoteles < num_max){
            System.out.println("Debes construir un hotel");
            return false;
        }else{
            casas += 1;
            return true;
        }
    }

    public boolean ConstruirHotel(){
        if(hoteles == num_max){
            System.out.println("No puedes construir más hoteles en este solar");
            return false;
        }else if(hoteles < num_max && casas != 4){
            System.out.println("Debes tener 4 casas antes");
            return false;
        }else{
            hoteles += 1;
            casas = 0;
            return true;
        }
    }

    public boolean ConstruirPiscina(){
        if(piscinas == num_max){
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
        if(pistas == num_max){
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
