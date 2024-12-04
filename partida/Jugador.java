package partida;

import java.util.ArrayList;

import monopoly.*;
import monopoly.casillas.Casilla;
import monopoly.casillas.Propiedades.Solar;
import monopoly.casillas.Propiedades.*;
import partida.avatares.*;
import partida.avatares.Coche;


public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.
    private int turnosBloqueado;

    // Atributos para estadísticas
    private float dineroInvertido;
    private float pagoTasasEImpuestos;
    private float pagoDeAlquileres;
    private float cobroDeAlquileres;
    private float pasarPorCasillaDeSalida;
    private float premiosInversionesOBote;
    private int vecesEnLaCarcel;
    private int lanzamientos;

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre = "banca";
        this.avatar = null;
        //Realmente pode non ser necesario asignar fortuna
        this.fortuna = 999999999; //Usar variabe global FORTUNA_BANCA¿?
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.turnosBloqueado = 0;
        this.propiedades = new ArrayList<>();

        // Inicializar estadísticas a cero
        this.dineroInvertido = 0;
        this.pagoTasasEImpuestos = 0;
        this.pagoDeAlquileres = 0;
        this.cobroDeAlquileres = 0;
        this.pasarPorCasillaDeSalida = 0;
        this.premiosInversionesOBote = 0;
        this.vecesEnLaCarcel = 0;
        this.lanzamientos = 0;
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;

        if(Avatar.avataresValidos.contains(tipoAvatar)){
            switch (tipoAvatar) {
                case "coche":
                    this.avatar = new Coche(this, inicio, avCreados);
                    break;
                case "esfinge":
                    this.avatar = new Esfinge(this, inicio, avCreados);
                    break;
                case "pelota":
                    this.avatar = new Pelota(this, inicio, avCreados);
                    break;
                case "sombrero":
                    this.avatar = new Sombrero(this, inicio, avCreados);
                    break;
                default:
                    Juego.consola.print("DEBUGGGG, error creando avatar");
                    break;
            }
        }else{
            Juego.consola.print("\nTipo de avatar incorrecto\n");
        }
        this.fortuna = Valor.FORTUNA_INICIAL;

        //Aquí alomejor se puede sustituir todo esto por this();
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.dineroInvertido = 0;
        this.pagoTasasEImpuestos = 0;
        this.pagoDeAlquileres = 0;
        this.cobroDeAlquileres = 0;
        this.pasarPorCasillaDeSalida = 0;
        this.premiosInversionesOBote = 0;
        this.vecesEnLaCarcel = 0;
        this.lanzamientos = 0;
        this.turnosBloqueado = 0;

        /*Non é necesario especificar <Casilla>,
        *por "diamond operator", e inferencia de tipos */
        this.propiedades = new ArrayList<Casilla>();
    }

    //Getters y setters
    public String getNombre(){
        return this.nombre;
    }

    public float getGastos() {
        return gastos;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public float getFortuna(){
        return this.fortuna;
    }

    public void setFortuna(float fortuna){
        this.fortuna = fortuna;
    }

    public boolean getEnCarcel(){
        return this.enCarcel;
    }

    public void setEnCarcel(boolean enCarcel){
        this.enCarcel = enCarcel;
    }

    public int getTiradasCarcel(){
        return this.tiradasCarcel;
    }

    public void setTiradasCarcel(int tiradasCarcel){
        this.tiradasCarcel = tiradasCarcel;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public int getVueltas(){
        return this.vueltas;
    }

    public int getLanzamientos(){
        return this.lanzamientos;
    }

    public int getTurnosBloqueado() {
        return this.turnosBloqueado;
    }

    public void setTurnosBloqueado(int turnosBloqueado) {
        this.turnosBloqueado = turnosBloqueado;
    }

    //Otros métodos:
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        this.propiedades.add(casilla);
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if (this.propiedades.contains(casilla)) {
            this.propiedades.remove(casilla);
        }else{
            Juego.consola.error("La casilla no pertenece al jugador\n");
        }
    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }
    

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    private void sumarGastos(float valor) {
        this.gastos += valor;
        this.fortuna -= valor;
    }

    /*Método para establecer al jugador en la cárcel. 
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        this.enCarcel = true;
        this.avatar.getLugar().eliminarAvatar(this.avatar);
        this.avatar.setLugar(pos.get(0).get(0));
        pos.get(0).get(0).anhadirAvatar(this.avatar);
        this.vecesEnLaCarcel++;
    }

    // Métodos para actualizar estadísticas
    public void incrementarDineroInvertido(float cantidad) {
        this.dineroInvertido += cantidad;
        this.sumarGastos(cantidad);  // Añadido también a los gastos
    }

    public void incrementarPagoTasasEImpuestos(float cantidad) {
        this.pagoTasasEImpuestos += cantidad;
        this.sumarGastos(cantidad);
    }

    public void incrementarPagoDeAlquileres(float cantidad) {
        this.pagoDeAlquileres += cantidad;
        this.sumarGastos(cantidad);
    }

    public void incrementarCobroDeAlquileres(float cantidad) {
        this.cobroDeAlquileres += cantidad;
        this.sumarFortuna(cantidad);
    }

    public void incrementarPasarPorCasillaDeSalida(float cantidad) {
        this.pasarPorCasillaDeSalida += cantidad;
        this.sumarFortuna(cantidad);
    }

    public void incrementarPremiosInversionesOBote(float cantidad) {
        this.premiosInversionesOBote += cantidad;
        this.sumarFortuna(cantidad);
    }

    // Método para mostrar estadísticas
    public void mostrarEstadisticasJugador(Jugador jugador) {
        Juego.consola.print("Estadísticas de " + jugador.nombre + ":");
        Juego.consola.print("{");
        Juego.consola.print("  dineroInvertido: " + jugador.dineroInvertido + ",");
        Juego.consola.print("  pagoTasasEImpuestos: " + jugador.pagoTasasEImpuestos + ",");
        Juego.consola.print("  pagoDeAlquileres: " + jugador.pagoDeAlquileres + ",");
        Juego.consola.print("  cobroDeAlquileres: " + jugador.cobroDeAlquileres + ",");
        Juego.consola.print("  pasarPorCasillaDeSalida: " + jugador.pasarPorCasillaDeSalida + ",");
        Juego.consola.print("  premiosInversionesOBote: " + jugador.premiosInversionesOBote + ",");
        Juego.consola.print("  vecesEnLaCarcel: " + jugador.vecesEnLaCarcel);
        Juego.consola.print("}");
    }

    public void incrementarVueltas(){
        this.vueltas++;
    }

    public void decrementarVueltas(){
        this.vueltas--;
    }

    // Método para incrementar lanzamientos
    public void incrementarLanzamientos() {
        this.lanzamientos++;
    }

    /**
     * Calcula la fortuna total del jugador, incluyendo su efectivo y el valor de sus propiedades y edificios.
     * @return la fortuna total del jugador.
     */
    public float calcularFortunaTotal() {
        float fortunaTotal = this.fortuna;  // Comienza con el efectivo del jugador

        // Sumar el valor de todas las propiedades y edificios que posee el jugador
        for (Casilla propiedad : this.propiedades) {
            fortunaTotal += ((Propiedad)propiedad).getValor();           // Valor de la propiedad
            //fortunaTotal += propiedad.getValorEdificios();  // Valor de los edificios en la propiedad
        }

        return fortunaTotal;
    }
    public void decrementarTurnosBloqueados() {
        if (turnosBloqueado > 0) {
            turnosBloqueado--;
        }
    }

    public boolean estaBloqueado() {
        return turnosBloqueado > 0;
    }

    public void cobrarPasoPorSalida(Casilla origen, Casilla destino){

        if(origen.getPosicion() > destino.getPosicion()){

            float vuelta = Valor.SUMA_VUELTA;
            this.incrementarVueltas();
            this.incrementarPasarPorCasillaDeSalida(vuelta);

            Juego.consola.print(this.getNombre() + " ha pasado por la casilla de Salida y cobra " + vuelta + "€.");
        }

    }

    // En principio solo debe usarse en movimientos hacia atrás
    public void PasoPorSalidaInverso(Casilla origen, Casilla destino){ 

        if(origen.getPosicion() < destino.getPosicion()){

            float vuelta = Valor.SUMA_VUELTA;
            this.decrementarVueltas();
            this.incrementarPasarPorCasillaDeSalida(-vuelta);

            Juego.consola.print(this.getNombre() + " ha retrocedido por la casilla de Salida y paga " + vuelta + "€.");
        }

    }

    public void DescribirJugador() {
        Juego.consola.print("Nombre del Jugador: " + nombre);
        Juego.consola.print("Fortuna: " + fortuna + "€" );
        
        if (!propiedades.isEmpty()) {
            Juego.consola.print("Propiedades: ");
            for (Casilla propiedad : propiedades) {
                Juego.consola.print(" - " + propiedad.getNombre());
                if(propiedad instanceof Solar){
                    Juego.consola.print("\t" + ((Solar)propiedad).listarEdificios());
                }
            }
        } else {
            Juego.consola.print("El jugador no tiene propiedades.");
        }

        if (avatar != null) {
            Juego.consola.print("Avatar: " + avatar.getId());
        } else {
            Juego.consola.print("El jugador no tiene un avatar asignado.");
        }
    }

    public void InicializarBanca(Tablero tablero){
        for (int index = 0; index < 40; index++) {
            this.anhadirPropiedad(tablero.obtenerCasilla(index));
        }
    }

}

/* 

1
coche
2
coche
3
coche
fin
dados
0
1
comprar Solar1
acabar 
dados 
6
0
comprar Solar3
acabar
dados 
5
0
comprar Estacion1
acabar
dados
0
2
comprar Solar2
construir casa
construir casa
construir casa
construir casa
construir hotel
construir casa
construir casa
construir casa
construir casa
construir hotel
construir piscina 
construir piscina
construir pista
construir pista
acabar
dados 
-3
0
 */