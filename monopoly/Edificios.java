package monopoly;

import partida.*;

public class Edificios {
    private Casilla casilla;
    private int casas;
    private int hoteles;
    private int piscinas;
    private int pistas;

    public Edificios(Casilla casilla){
        this.casilla = casilla;
        this.casas = 0;
        this.hoteles = 0;
        this.piscinas = 0;
        this.piscinas = 0;
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

    public boolean EstaEdificado(){
        if(casas==0 && hoteles==0 && piscinas==0 && piscinas==0){
            return true;
        }else{
            return false;
        }
    }

    public boolean ConstruirCasa(){
        if(this.casas < 4){
            this.casas += 1;
            return true;
        }else{
            System.out.println("Debes construir un hotel");
            return false;
        }
    }

    public boolean ConstruirHotel(){
        if(this.hoteles < 3){
            if(this.casas == 4){
                this.hoteles += 1;
                this.casas = 0;
                return true;
            }else{
                System.out.println("Debes tener 4 casas antes");
                return false;
            }
        }else{
            System.out.println("No puedes construir más hoteles");
            return false;
        }
    }

    public boolean ConstruirPiscina(){
        if(this.hoteles >= 1 && this.casas >= 2 || this.hoteles >= 2){
            if(this.piscinas < 2){
                this.piscinas += 1;
                return true;
            }else{
                System.out.println("No puedes tener más piscinas");
                return false;
            }
        }else{
            System.out.println("Debes tener, como mínimo, 1 hotel y 2 casas");
            return false;
        }
    }

    public boolean ConstruirPista(){
        if(this.hoteles >= 2){
            if(this.pistas < 2){
                this.pistas += 1;
                return true;
            }else{
                System.out.println("No puedes tener más pistas");
                return false;
            }
        }else{
            System.out.println("Debes tener, como mínimo, 2 hoteles");
            return false;
        }
    }
}
