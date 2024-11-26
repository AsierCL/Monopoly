package monopoly.casillas.Acciones;

import java.util.ArrayList;
import java.util.Scanner;

import monopoly.Tablero;
import partida.Jugador;
import partida.cartas.Carta;



public class Comunidad extends Acciones {

    public Comunidad(String nombre, int posicion,  Jugador duenho){
        super(nombre, posicion, duenho);
    }

    @Override
    public String infoCasilla() {
        StringBuilder info = new StringBuilder();
        info.append("Nombre: ").append(this.getNombre()).append("\n");
        info.append("Posici\u00f3n: ").append(this.getPosicion()).append("\n");
        info.append("Tipo: ").append(this.getClass()).append("\n");
        info.append("Descripci\u00f3n: Casilla de comunidad.\n");

        return info.toString();
    }

    @Override
    public boolean evaluarCasilla(Jugador var1, Jugador var2, int var3, Tablero var4, ArrayList<Jugador> var5) {
        System.out.println("TARJETA DE COMUNIDAD\n");
        Scanner var12 = new Scanner(System.in);

        int var13;
        do {
            do {
                System.out.print("Introduce un n\u00famero del 1 al 6 para seleccionar una carta de Suerte: ");
                var13 = var12.nextInt();
                } while(var13 < 1);
            } while(var13 > 6);

            Carta var14 = new Carta("Comunidad", var13);
            var14.ejecutarAccion(var1, var2, var4, var5, var3);
        
    }
    


}