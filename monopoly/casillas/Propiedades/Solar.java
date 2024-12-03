package monopoly.casillas.Propiedades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import monopoly.Juego;
import monopoly.Tablero;
import monopoly.Valor;
import monopoly.edificios.Casa;
import monopoly.edificios.Edificio;
import monopoly.edificios.Hotel;
import monopoly.edificios.Piscina;
import monopoly.edificios.Pista;
import monopoly.exceptions.DuenhoCasilla;
import monopoly.exceptions.DuenhoSolar;
import monopoly.exceptions.EdificioCantidad;
import monopoly.exceptions.EdificioIncorrecto;
import monopoly.exceptions.EstarCasilla;
import monopoly.exceptions.Parametros;
import partida.Jugador;

public class Solar extends Propiedad {
    ArrayList<Casa> casas;
    ArrayList<Hotel> hoteles;
    ArrayList<Piscina> piscinas;
    ArrayList<Pista> pistas;
    int numMax;

    public Solar(String nombre, int posicion, Jugador duenho, float valor){
        super(nombre, posicion, duenho, valor);
        this.casas = new ArrayList<>();
        this.hoteles = new ArrayList<>();
        this.piscinas = new ArrayList<>();
        this.pistas = new ArrayList<>();
    }

    public int getNumMax() {
        return numMax;
    }

    public void setNumMax(int numMax) {
        this.numMax = numMax;
    }


    @Override
    public String infoCasilla() {
        //Creamos a cadena a devolver
        StringBuilder info = new StringBuilder();
        
        info.append("Nombre: ").append(this.getNombre()).append("\n");
        info.append("Posición: ").append(this.getPosicion()).append("\n");
        info.append("Tipo: Solar").append("\n");
    
        info.append("Valor de compra: ").append(this.getValor()).append("\n");
        info.append("Impuesto: ").append(calcularPagoSolar(this.getDuenho())).append("\n");
        if (this.getDuenho() != null) {
            info.append("Dueño: ").append(this.getDuenho().getNombre()).append("\n");
        } else {
            info.append("Dueño: Banca\n");
        }
        info.append("Grupo: ").append(this.getGrupo().getColorGrupo()).append("  ").append(Valor.RESET).append("\n");
        info.append("Construcciones: " + "|Casas=" + this.casas.size() + "|Hoteles=" + this.hoteles.size() + "|Piscinas=" + this.piscinas.size() + "|Pistas=" + this.pistas.size() + "|\n");

        return info.toString();
    }

    public String listarEdificios(){
        return(this.getNombre() + "|Casas=" + casas.size() + "|Hoteles=" + hoteles.size() + "|Piscinas=" + piscinas.size() + "|Pistas=" + pistas.size());
    }
    
    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> jugadores){
        haEstado(actual, jugadores);
        float pago = 0;
        
        if (this.getHipotecada()){
            return true;
        }

        pago = calcularPagoSolar(actual);
        if(this.getDuenho() != actual && this.getDuenho() != banca){// Casilla de otro
            if((actual.getFortuna() - pago)<0){
                Juego.consola.print("Dinero insuficiente para pagar, debes vender propiedades o declararte en bancarrota.");
                return false;
            }else{
                Juego.consola.print("Pagas impuesto de casilla: -" + pago + "€");
                actual.incrementarPagoDeAlquileres(pago);
                this.getDuenho().incrementarCobroDeAlquileres(pago);
                registrarIngreso(pago);
                //////////////// REVISAR  ///////////////////////////
            }
        }
        return true;
    }

    public float calcularPagoSolar(Jugador actual) {
        float multiSolar = this.getGrupo().esDuenhoGrupo(this.getDuenho()) ? 2 : 1;
        float multiCasa = calcularMultiplicadorCasa(this.casas.size());
        float multiHotel = this.hoteles.size() * 70;
        float multiPiscina = this.piscinas.size() * 25;
        float multiPista = this.pistas.size() * 25;
    
        return this.getValor() * 0.1f * (multiSolar + multiCasa + multiHotel + multiPiscina + multiPista);
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

    
    public void Construir(Jugador jugador, String construccion, ArrayList<Jugador> jugadores) throws DuenhoCasilla, EdificioIncorrecto, EstarCasilla, DuenhoSolar {
        if(!getDuenho().equals(jugador)){
            throw new DuenhoCasilla();
        }
        if(!Edificio.edificiosValidos.contains(construccion)){
            throw new EdificioIncorrecto();
        }
        
        if (!jugador.getAvatar().getLugar().equals(this)) {
            throw new EstarCasilla();
        }
        if (!this.getGrupo().esDuenhoGrupo(jugador) && !(this.getHanEstado().get(jugadores.indexOf(jugador))>1)) {
            throw new DuenhoSolar();
        }

        Map<String, Float> multiplicadores = new HashMap<>();
        multiplicadores.put("casa", 0.6f);
        multiplicadores.put("hotel", 0.6f);
        multiplicadores.put("piscina", 0.4f);
        multiplicadores.put("pista", 1.25f);

        float multiplicador = multiplicadores.get(construccion);
        if (multiplicador == 0) {
            Juego.consola.print("Construcción incorrecta");
            return;
        }

        float costo = this.getValor() * multiplicador;
        if (jugador.getFortuna() < costo) {
            Juego.consola.print("Dinero insuficiente para pagar");
            return;
        }

        boolean construccionExitosa = switch (construccion) {
            case "casa" -> ConstruirCasa();
            case "hotel" -> ConstruirHotel();
            case "piscina" -> ConstruirPiscina();
            case "pista" -> ConstruirPista();
            default -> false;
        };

        if (construccionExitosa) {
            Juego.consola.print("Pagas la construcción: -" + costo + "€");
            jugador.sumarGastos(costo);
        } else {
            Juego.consola.print("Construcción cancelada");
        }
    }
    
    public void VenderEdificios(Jugador jugador, String construccion, int cantidad) throws EdificioIncorrecto, DuenhoCasilla, Parametros {
        if (!Edificio.edificiosValidos.contains(construccion)) {
            throw new EdificioIncorrecto();
        }
        if (!this.getDuenho().equals(jugador)) {
            throw new DuenhoCasilla();
        }
        if (cantidad <= 0) {
            throw new Parametros();
        }
    
        Map<String, Float> multiplicadores = new HashMap<>();
        multiplicadores.put("casa", 0.6f);
        multiplicadores.put("hotel", 0.6f);
        multiplicadores.put("piscina", 0.4f);
        multiplicadores.put("pista", 1.25f);

        float multiplicador = multiplicadores.get(construccion);
        if (multiplicador == 0) {
            Juego.consola.print("Construcción incorrecta");
            return;
        }
        float valor_venta = this.getValor() * multiplicador;


        if (this.EsSolarEdificado()) {
            Juego.consola.print("Edificios en esta propiedad:");
            Juego.consola.print("Casas: " + casas.size() + ", Hoteles: " + hoteles.size() + ", Piscinas: " + piscinas.size() + ", Pistas: " + pistas.size());

            for (int i = 0; i < cantidad; i++) {
                switch (construccion) {
                    case "casa":
                        if (casas.size() >= cantidad)
                            DestruirCasa(cantidad);
                            jugador.sumarFortuna(valor_venta);
                        break;
                    case "hotel":
                        if (hoteles.size() >= cantidad)
                            DestruirHotel(cantidad);
                            jugador.sumarFortuna(valor_venta);
                        break;
                    case "piscina":
                        if (piscinas.size() >= cantidad) 
                            jugador.sumarFortuna(valor_venta);
                            DestruirPiscina(cantidad);
                        break;
                    case "pista":
                        if (pistas.size() >= cantidad) 
                            jugador.sumarFortuna(valor_venta);
                            DestruirPista(cantidad);
                        break;
                    default:
                        Juego.consola.print("Tipo de construcción no válido");
                        break;
                }
            }

        } else {
            Juego.consola.print("Este solar no tiene edificios para vender.");
        }
    }
    
    public boolean EsSolarEdificado(){
        if(casas.size()==0 && hoteles.size()==0 && piscinas.size()==0 && piscinas.size()==0){
            return false;
        }else{
            return true;
        }
    }


    /* 
     * GESTION DE EDIFICIOS:
     * CONSTRUIR, DESTRUIR,
     * EdificiosGrupo, getEdificios
     */
    private boolean ConstruirCasa() {
        if(this.getCasasGrupo() >= numMax+4 && this.getHotelesGrupo() == numMax){
            Juego.consola.print("No puedes construir más casas en este solar");
            return false;
        }else if(casas.size() == 4 && this.getHotelesGrupo() < numMax){
            Juego.consola.print("Debes construir un hotel");
            return false;
        }else{
            casas.add(new Casa());
            //casas += 1;
            return true;
        }
    }

    private boolean ConstruirHotel(){
        if(this.getHotelesGrupo() == numMax){
            Juego.consola.print("No puedes construir más hoteles en este solar");
            return false;
        }else if(this.getHotelesGrupo() < numMax && casas.size() != 4){
            Juego.consola.print("Debes tener 4 casas antes");
            return false;
        }else if(this.getHotelesGrupo() == 1 && this.getCasasGrupo() > numMax+4){
            Juego.consola.print("Superas el máximo de casas. Debes eliminarlas antes de construir el hotel");
            return false;
        }else if(casas.size() == 4 && this.getHotelesGrupo() < numMax && this.getCasasGrupo() <= numMax+4){
            hoteles.add(new Hotel());
            casas.removeAll(casas);
            return true;
        }else{
            Juego.consola.print("CASO NON CONTEMPLADO, REVISAR CODIGO");
            return false;
        }
    }

    private boolean ConstruirPiscina(){
        if(this.getPiscinasGrupo() == numMax){
            Juego.consola.print("No puedes tener más piscinas en este solar");
            return false;
        }else if(hoteles.size() == 0 || hoteles.size() == 1 && casas.size() < 2){
            Juego.consola.print("Debes tener, como mínimo, 1 hotel y 2 casas");
            return false;
        }else{
            piscinas.add(new Piscina());
            return true;
        }
    }

    private boolean ConstruirPista(){
        if(this.getPistasGrupo() == numMax){
            Juego.consola.print("No puedes tener más pistas en este solar");
            return false;
        }else if(hoteles.size() < 2){
            Juego.consola.print("Debes tener, como mínimo, 2 hoteles");
            return false;
        }else{
            pistas.add(new Pista());
            return true;
        }
    }

    private boolean DestruirCasa(int cantidad){
        if (casas.size() < cantidad) {
            float valorVenta = this.getValor() * 0.3f * cantidad;
            for (int index = 0; index < cantidad; index++) {
                casas.removeLast();
            }
            this.getDuenho().sumarFortuna(valorVenta);
            Juego.consola.print("El jugador " + this.getDuenho().getNombre() + " ha vendido " + cantidad + " casas y recibe " + valorVenta + "€");
            return true;
        } else {
            Juego.consola.print("No tienes tantas casas para vender");
            return false;
        }
    }
    
    private boolean DestruirHotel(int cantidad){
        if (hoteles.size() < cantidad) {
            float valorVenta = this.getValor() * 0.6f * cantidad;
            for (int index = 0; index < cantidad; index++) {
                hoteles.removeLast();
            }
            this.getDuenho().sumarFortuna(valorVenta);
            Juego.consola.print("El jugador " + this.getDuenho().getNombre() + " ha vendido " + cantidad + " hoteles y recibe " + valorVenta + "€");
            return true;
        } else {
            Juego.consola.print("No tienes tantos hoteles para vender");
            return false;
        }
    }

    private boolean DestruirPiscina(int cantidad){
        if (piscinas.size() < cantidad) {
            float valorVenta = this.getValor() * 0.3f * cantidad;
            for (int index = 0; index < cantidad; index++) {
                piscinas.removeLast();
            }
            this.getDuenho().sumarFortuna(valorVenta);
            Juego.consola.print("El jugador " + this.getDuenho().getNombre() + " ha vendido " + cantidad + " piscinas y recibe " + valorVenta + "€");
            return true;
        } else {
            Juego.consola.print("No tienes tantas piscinas para vender");
            return false;
        }
    }

    private boolean DestruirPista(int cantidad){
        if (pistas.size() < cantidad) {
            float valorVenta = this.getValor() * 0.3f * cantidad;
            for (int index = 0; index < cantidad; index++) {
                pistas.removeLast();
            }
            this.getDuenho().sumarFortuna(valorVenta);
            Juego.consola.print("El jugador " + this.getDuenho().getNombre() + " ha vendido " + cantidad + " pistas y recibe " + valorVenta + "€");
            return true;
        } else {
            Juego.consola.print("No tienes tantas pistas para vender");
            return false;
        }
    }

    public int getCasasGrupo(){
        int total = 0;
        for (Propiedad casilla : this.getGrupo().getMiembros()) {
            total += ((Solar) casilla).getCasas();
        }
        return total;
    }
    
    public int getHotelesGrupo(){
        int total = 0;
        for (Propiedad casilla : this.getGrupo().getMiembros()) {
            total += ((Solar) casilla).getHoteles();
        }
        return total;
    }
    
    public int getPiscinasGrupo(){
        int total = 0;
        for (Propiedad casilla : this.getGrupo().getMiembros()) {
            total += ((Solar) casilla).getPiscinas();
        }
        return total;
    }
    
    public int getPistasGrupo(){
        int total = 0;
        for (Propiedad casilla : this.getGrupo().getMiembros()) {
            total += ((Solar) casilla).getPistas();
        }
        return total;
    }
    
    public int getCasas(){
        return casas.size();
    }
    
    public int getHoteles(){
        return hoteles.size();
    }
    
    public int getPiscinas(){
        return piscinas.size();
    }
    
    public int getPistas(){
        return pistas.size();
    }
}