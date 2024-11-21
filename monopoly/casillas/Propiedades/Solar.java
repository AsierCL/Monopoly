package monopoly.casillas.Propiedades;

import partida.Jugador;

public class Solar {








    public float calcularPagoSolar(Jugador actual) {
        float multiSolar = this.getGrupo().esDuenhoGrupo(this.duenho) ? 2 : 1;
        float multiCasa = calcularMultiplicadorCasa(this.getEdificios().getCasas());
        float multiHotel = this.getEdificios().getHoteles() * 70;
        float multiPiscina = this.getEdificios().getPiscinas() * 25;
        float multiPista = this.getEdificios().getPistas() * 25;
    
        return this.impuesto * (multiSolar + multiCasa + multiHotel + multiPiscina + multiPista);
    }
    
    private float calcularMultiplicadorCasa(int numCasas) {
        switch (numCasas) {
            case 1: return 5;
            case 2: return 15;
            case 3: return 35;
            case 4: return 50;
            default: return 0;
        }
    }    
}