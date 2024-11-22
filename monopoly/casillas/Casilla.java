package monopoly.casillas;

import partida.*;
import partida.avatares.Avatar;
import partida.cartas.Carta;

import monopoly.Grupo;
import monopoly.Tablero;
import monopoly.Valor;
import monopoly.edificios.Edificios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Suerte, Comunidad).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.
    private ArrayList<Integer> hanEstado; //Avatares que están situados en la casilla.
    private Edificios edificios;
    private boolean hipotecada; //
    //Atributos para  estadísticas
    private float ingresosTotales;
    private int contadorVisitas; //Borrar y usar hanEstado

    //Constructores:
    public Casilla() {
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
        this.hipotecada = false;
        if(tipo.equals("Solar")){
            this.impuesto = valor * 0.1f;
        }else if(tipo.equals("Transporte")){
            this.impuesto = valor * 0.25f;
        }else if(tipo.equals("Servicios")){
            this.impuesto = Valor.SUMA_VUELTA/200f;
        }else{
            System.out.println("ERROR ASIGNANDO IMPUESTO CASILLAS");
            System.exit(1);
        }
        this.avatares = new ArrayList<>();
        this.hanEstado = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.hanEstado.add(0);
        }
        this.edificios = new Edificios(this);

        this.ingresosTotales = 0;
        this.contadorVisitas = 0;
    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
    * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.impuesto = impuesto;
        this.duenho = duenho;
        this.tipo = "Especial";
        this.hipotecada = false;
        this.avatares = new ArrayList<>();
        this.hanEstado = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.hanEstado.add(0);
        }

        this.ingresosTotales = 0;
        this.contadorVisitas = 0;
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
    * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        this.avatares = new ArrayList<>();
        this.hanEstado = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.hanEstado.add(0);
        }

        this.hipotecada = false;
    }


    // Métodos Getter
    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPosicion() {
        return posicion;
    }

    public float getValor() {
        return valor;
    }
    
    public Jugador getDuenho() {
        return duenho;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public void setGrupo(Grupo grupo){
        this.grupo = grupo;
    }

    public Grupo getGrupo(){
        return this.grupo;
    }

    public void setAvatares(ArrayList<Avatar> avatares){
        this.avatares = avatares;
    }

    public ArrayList<Avatar> getAvatares(){
        return this.avatares;
    }

    public Edificios getEdificios() {
        return edificios;
    }

    public float getIngresosTotales() {
        return this.ingresosTotales;
    }

    public int getContadorVisitas(){
        return this.contadorVisitas;
    }
    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (av != null && !this.avatares.contains(av)) {
            this.avatares.add(av);
        }
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        if (this.avatares.contains(av)){
            this.avatares.remove(av);
        }
    }

    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> jugadores) {//Solucion prvisional
        if (this.hipotecada == true){
            return true;
        }
        float pago = 0;
        this.contadorVisitas++;
        this.hanEstado.set(jugadores.indexOf(actual), this.hanEstado.get(jugadores.indexOf(actual)) + 1);

        switch(this.tipo){
            case("Solar"):
                pago = calcularPagoSolar(actual);
                if(this.duenho != actual && this.duenho != banca){// Casilla de otro
                    if((actual.getFortuna() - pago)<0){
                        System.out.println("Dinero insuficiente para pagar, debes vender propiedades o declararte en bancarrota.");
                        return false;
                    }else{
                        System.out.println("Pagas impuesto de casilla: -" + pago + "€");
                        actual.incrementarPagoDeAlquileres(pago);
                        this.duenho.incrementarCobroDeAlquileres(pago);
                        registrarIngreso(pago);
                        //////////////// REVISAR  ///////////////////////////
                    }
                }
                break;

            case("Transporte"):
                if(this.duenho != actual && this.duenho != banca){// Casilla de otro
                    pago = this.impuesto * this.numTransporte();
                    if((actual.getFortuna()-pago)<0){
                        System.out.println("Dinero insuficiente para pagar, debes vender propiedades o declararte en bancarrota.");
                        return false;
                    }else{
                        System.out.println("Pagas impuesto de casilla: -" + pago + "€");
                        actual.incrementarPagoTasasEImpuestos(pago);
                        this.duenho.incrementarCobroDeAlquileres(pago);
                        registrarIngreso(pago);
                        ///////////////////// REVISAR  //////////////////////////////
                    }
                }
                break;            
                
            case("Servicios"):
                if(this.duenho != actual && this.duenho != banca){// Casilla de otro
                    pago = this.impuesto * this.numServicios() * tirada;
                    if((actual.getFortuna()-pago)<0){
                        System.out.println("Dinero insuficiente para pagar, debes vender propiedades o declararte en bancarrota.");
                        return false;
                    }else{
                        System.out.println("Pagas impuesto de casilla: -" + pago + "€");
                        actual.incrementarPagoTasasEImpuestos(pago);
                        this.duenho.incrementarCobroDeAlquileres(pago);
                        registrarIngreso(pago);
                    }
                }
                break;

            case("Suerte"):
                System.out.println("TARJETA DE SUERTE\n");
                
                Scanner scannerS = new Scanner(System.in);
                int numeroAccionSuerte;
                
                // Pedir un número entre 1 y 6 al usuario
                do {
                    System.out.print("Introduce un número del 1 al 6 para seleccionar una carta de Suerte: ");
                    numeroAccionSuerte = scannerS.nextInt();
                } while (numeroAccionSuerte < 1 || numeroAccionSuerte > 6);

                // Crear una carta de "Suerte" con el número elegido como acción
                Carta cartaSuerte = new Carta("Suerte",  numeroAccionSuerte);

                // Ejecutar la acción de la carta usando la lista de jugadores y el tablero
                cartaSuerte.ejecutarAccion(actual, banca, tablero, jugadores, tirada);

                break;
                
            case("Comunidad"):
                System.out.println("TARJETA DE COMUNIDAD\n");

                Scanner scannerC = new Scanner(System.in);
                int numeroAccionComunidad;
                
                // Pedir un número entre 1 y 6 al usuario
                do {
                    System.out.print("Introduce un número del 1 al 6 para seleccionar una carta de Suerte: ");
                    numeroAccionComunidad = scannerC.nextInt();
                } while (numeroAccionComunidad < 1 || numeroAccionComunidad > 6);

                // Crear una carta de "Suerte" con el número elegido como acción
                Carta cartaComunidad = new Carta("Comunidad", numeroAccionComunidad);

                // Ejecutar la acción de la carta usando la lista de jugadores y el tablero
                cartaComunidad.ejecutarAccion(actual, banca, tablero, jugadores, tirada);

                break;
        
            case("Especial"):
                switch (this.nombre) {
                    case ("Salida"):
                    
                        break;
                    
                    case ("Carcel"):

                        break;

                    case ("Parking"):
                        System.out.println("Recibes el bote del parking: +" + this.valor + "€");
                        actual.incrementarPremiosInversionesOBote(this.valor);
                        this.valor = 0;
                        break;
                    
                    case ("IrCarcel"):
                        System.out.println("VAS A LA CARCEL");
                        actual.encarcelar(tablero.getPosiciones());
                        break;
                    
                    case ("Impuesto1"):
                    case ("Impuesto2"):
                        if((actual.getFortuna()-this.impuesto)<0){
                            System.out.println("Dinero insuficiente para pagar, debes vender propiedades o declararte en bancarrota.");
                            return false;
                        }else{
                            System.out.println("Pagas impuesto de casilla: -" + this.impuesto + "€");
                            actual.incrementarPagoTasasEImpuestos(this.impuesto);
                            tablero.obtenerCasilla("Parking").sumarValor(this.impuesto);
                            registrarIngreso(this.impuesto);
                        }
                        break;

                    default:
                        System.out.println("Defaul case");
                        break;
                }

                break;

            default:
                System.out.println("Error evaluando casilla");
                break;
        }
        return true;
    }

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

    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if(this.tipo == "Solar" || this.tipo == "Transporte" || this.tipo == "Servicios"){
            if(solicitante.getAvatar().getLugar() == this){
                if(this.duenho == banca){
                    solicitante.incrementarDineroInvertido(this.valor);
                    solicitante.anhadirPropiedad(this);
                    this.duenho = solicitante;
                    System.out.println("Has comprado la casilla " + this.nombre + " por " + this.valor);
                }else if(this.duenho == solicitante){
                    System.out.println("Esta casilla ya te pertenece");
                }else{
                    System.out.println("La casilla es de "+this.duenho.getNombre());
                }
            }else{
                System.out.println("Debes estar en la casilla");
            }
        }else{ // Comprar carcel, salida, etcetc
            System.out.println("Esta casilla no se puede comprar");
        }
    }

    public void hipotecarCasilla(Jugador solicitante) {
        if (this.getDuenho().equals(solicitante)) {
            if (this.hipotecada == true) {
                System.out.println("La casilla ya está hipotecada");
            } else if (this.tipo.equals("Solar") && this.getEdificios().EsSolarEdificado()) {
                System.out.println("Debes vender todos los edificios de tu propiedad antes de hipotecarla.");
            } else if (this.tipo.equals("Solar") || this.tipo.equals("Transporte") || this.tipo.equals("Servicios")) {
                solicitante.setFortuna(solicitante.getFortuna() + this.getValor());
                this.hipotecada = true;
                System.out.println(solicitante.getNombre() + " ha hipotecado la casilla " + this.getNombre() + " y recibe " + this.getValor() + ".");
            }
        } else {
            System.out.println("No eres propietario de esta casilla.");
        }
       
    }


    public void deshipotecarCasilla(Jugador solicitante){
        if(this.hipotecada == true) {
            if (this.getDuenho() != solicitante) {
                System.out.println("No puedes deshipotecar la casilla si no eres su dueño.");    
            } else if (this.getDuenho() == solicitante) {
            solicitante.setFortuna(solicitante.getFortuna() - (this.getValor() + (10*this.getValor()/100)));
            System.out.println(solicitante.getNombre() + " ha deshipotecado la casilla " + this.getNombre() + " y paga " + (this.getValor() + (10*this.getValor()/100)));
            this.hipotecada = false;
            }
        } else {
            System.out.println("La casilla no está hipotecada.");
        } 
    }

    /*Método para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
    * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        this.valor += suma;
    }

    /*Método para mostrar información sobre una casilla.
    * Devuelve una cadena con información específica de cada tipo de casilla.*/
    public String infoCasilla() {
        //Creamos a cadena a devolver
        StringBuilder info = new StringBuilder();
        
        info.append("Nombre: ").append(nombre).append("\n");
        info.append("Posición: ").append(posicion).append("\n");
        info.append("Tipo: ").append(tipo).append("\n");
    
        switch (tipo.toLowerCase()) {
            case "solar":
                info.append("Valor de compra: ").append(valor).append("\n");
                info.append("Impuesto: ").append(calcularPagoSolar(this.getDuenho())).append("\n");
                if (duenho != null) {
                    info.append("Dueño: ").append(duenho.getNombre()).append("\n");
                } else {
                    info.append("Dueño: Banca\n");
                }
                info.append("Grupo: ").append(grupo.getColorGrupo()).append("  ").append(Valor.RESET).append("\n");
                info.append("Construcciones: " + "|Casas=" + this.getEdificios().getCasas() + "|Hoteles=" + this.getEdificios().getHoteles() + "|Piscinas=" + this.getEdificios().getPiscinas() + "|Pistas=" + this.getEdificios().getPistas() + "|\n");
                break;
                
            case "especial":
                info.append("Descripción: Casilla especial.\n");
                break;
                
            case "transporte":
                info.append("Valor de compra: ").append(valor).append("\n");
                info.append("Impuesto: ").append(impuesto).append("\n");
                if (duenho != null) {
                    info.append("Dueño: ").append(duenho.getNombre()).append("\n");
                } else {
                    info.append("Dueño: Banca\n");
                }
                break;
                
            case "servicios":
                info.append("Valor de compra: ").append(valor).append("\n");
                info.append("Impuesto: ").append(impuesto).append("\n");
                if (duenho != null) {
                    info.append("Dueño: ").append(duenho.getNombre()).append("\n");
                } else {
                    info.append("Dueño: Banca\n");
                }
                break;
                
            case "comunidad":
                info.append("Descripción: Casilla de comunidad.\n");
                break;
                
            default:
                info.append("Descripción: Tipo de casilla desconocido.\n");
                break;
        }
    
        return info.toString();
    }

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casaEnVenta() {        //Creamos a cadena a devolver
        StringBuilder info = new StringBuilder();
        
        info.append("Nombre: ").append(nombre).append("\n");
        info.append("Posición: ").append(posicion).append("\n");
        info.append("Tipo: ").append(tipo).append("\n");
        info.append("Precio: ").append(valor).append("\n");
    
        return info.toString();

    }

    public void Construir(Jugador jugador, String construccion, ArrayList<Jugador> jugadores) {
        if(!Edificios.edificiosValidos.contains(construccion)){
            System.out.println("Tipo de edificio incorrecto");
            System.out.println("Tipos permitidos: | casa | hotel | piscina | pista |");
            return;
        }
        if (!this.tipo.equals("Solar")) {
            System.out.println("No puedes edificar aqui");
            return;
        }
        if (!jugador.getAvatar().getLugar().equals(this)) {
            System.out.println("Debes estar en la casilla para edificar");
            return;
        }
        if (!this.getGrupo().esDuenhoGrupo(jugador) && !(this.hanEstado.get(jugadores.indexOf(jugador))>1)) {
            System.out.println("Debes ser dueño de todo el solar para edificar o haber caido 2 veces");
            return;
        }

        Map<String, Float> multiplicadores = new HashMap<>();
        multiplicadores.put("casa", 0.6f);
        multiplicadores.put("hotel", 0.6f);
        multiplicadores.put("piscina", 0.4f);
        multiplicadores.put("pista", 1.25f);

        float multiplicador = multiplicadores.get(construccion);
        if (multiplicador == 0) {
            System.out.println("Construcción incorrecta");
            return;
        }

        float costo = this.valor * multiplicador;
        if (jugador.getFortuna() < costo) {
            System.out.println("Dinero insuficiente para pagar");
            return;
        }

        boolean construccionExitosa = switch (construccion) {
            case "casa" -> edificios.ConstruirCasa();
            case "hotel" -> edificios.ConstruirHotel();
            case "piscina" -> edificios.ConstruirPiscina();
            case "pista" -> edificios.ConstruirPista();
            default -> false;
        };

        if (construccionExitosa) {
            System.out.println("Pagas la construcción: -" + costo + "€");
            jugador.sumarGastos(costo);
        } else {
            System.out.println("Construcción cancelada");
        }
    }

    private int numTransporte(){
        int contador = 0;
        Jugador duenho = this.getDuenho();
        for (Casilla casilla : duenho.getPropiedades()) {
            if(casilla.tipo.equals("Transporte")){
                contador++;
            }
        }
        return contador;
    }

    private int numServicios(){
        int contador = 0;
        Jugador duenho = this.getDuenho();
        for (Casilla casilla : duenho.getPropiedades()) {
            if(casilla.tipo.equals("Servicios")){
                contador++;
            }
        }
        return contador;
    }
    public void registrarIngreso(float cantidad) {
        this.ingresosTotales += cantidad; // Suma la cantidad recibida a los ingresos totales
        if (grupo != null) {
            grupo.registrarIngresosGrupo(cantidad); // Registrar ingresos en el grupo si existe
        }
    }

    public void VenderEdificios(Jugador jugador, String construccion, int cantidad) {
        if (!Edificios.edificiosValidos.contains(construccion)) {
            System.out.println("Tipo de edificio incorrecto");
            System.out.println("Tipos permitidos: | casa | hotel | piscina | pista |");
            return;
        }
        if (!this.getDuenho().equals(jugador)) {
            System.out.println("No eres el dueño de esta casilla");
            return;
        }
    
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero");
            return;
        }
    
        if (this.getTipo().equals("Solar")) {
            if (this.getEdificios().EsSolarEdificado()) {
                int casas = this.getEdificios().getCasas();
                int hoteles = this.getEdificios().getHoteles();
                int piscinas = this.getEdificios().getPiscinas();
                int pistas = this.getEdificios().getPistas();
                System.out.println("Edificios en esta propiedad:");
                System.out.println("Casas: " + casas + ", Hoteles: " + hoteles + ", Piscinas: " + piscinas + ", Pistas: " + pistas);
    
                float valorVenta = 0;
    
                switch (construccion) {
                    case "casa":
                        if (casas >= cantidad) {
                            valorVenta = this.getValor() * 0.3f * cantidad;
                            casas -= cantidad;  // Disminuir la cantidad de casas
                            jugador.setFortuna(jugador.getFortuna() + valorVenta);
                            this.getEdificios().setCasas(casas);  // Actualizar las casas en la propiedad
                            System.out.println("El jugador " + jugador.getNombre() + " ha vendido " + cantidad + " casas y recibe " + valorVenta + "€");
                        } else {
                            System.out.println("No tienes tantas casas para vender");
                            return;
                        }
                        break;
                    case "hotel":
                        if (hoteles >= cantidad) {
                            valorVenta = this.getValor() * 0.6f * cantidad;
                            hoteles -= cantidad;  // Disminuir la cantidad de hoteles
                            jugador.setFortuna(jugador.getFortuna() + valorVenta);
                            this.getEdificios().setHoteles(hoteles);  // Actualizar los hoteles en la propiedad
                            System.out.println("El jugador " + jugador.getNombre() + " ha vendido " + cantidad + " hoteles y recibe " + valorVenta + "€");
                        } else {
                            System.out.println("No tienes tantos hoteles para vender");
                            return;
                        }
                        break;
                    case "piscina":
                        if (piscinas >= cantidad) {
                            valorVenta = this.getValor() * 0.4f * cantidad;
                            piscinas -= cantidad;  // Disminuir la cantidad de piscinas
                            jugador.setFortuna(jugador.getFortuna() + valorVenta);
                            this.getEdificios().setPiscinas(piscinas);  // Actualizar las piscinas en la propiedad
                            System.out.println("El jugador " + jugador.getNombre() + " ha vendido " + cantidad + " piscinas y recibe " + valorVenta + "€");
                        } else {
                            System.out.println("No tienes tantas piscinas para vender");
                            return;
                        }
                        break;
                    case "pista":
                        if (pistas >= cantidad) {
                            valorVenta = this.getValor() * 0.5f * cantidad;
                            pistas -= cantidad;  // Disminuir la cantidad de pistas
                            jugador.setFortuna(jugador.getFortuna() + valorVenta);
                            this.getEdificios().setPistas(pistas);  // Actualizar las pistas en la propiedad
                            System.out.println("El jugador " + jugador.getNombre() + " ha vendido " + cantidad + " pistas y recibe " + valorVenta + "€");
                        } else {
                            System.out.println("No tienes tantas pistas para vender");
                            return;
                        }
                        break;
                    default:
                        System.out.println("Tipo de construcción no válido");
                        break;
                }
            } else {
                System.out.println("Este solar no tiene edificios para vender.");
            }
        } else {
            System.out.println("Esta casilla no es un solar.");
        }
    }

    public void cambiarDuenho(Jugador nuevoduenho){

        this.duenho.getPropiedades().remove(this);
        this.duenho = nuevoduenho;
        nuevoduenho.getPropiedades().add(this);
    }
}