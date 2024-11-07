package partida;

import java.util.ArrayList;

import monopoly.*;


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
        this.avatar = new Avatar();
        //Realmente pode non ser necesario asignar fortuna
        this.fortuna = 999999999; //Usar variabe global FORTUNA_BANCA¿?
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        // Asignar array propiedades
        //this.propiedades ...

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
            this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
        }else{
            System.out.println("\nTipo de avatar incorrecto\n");
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

        /*Non é necesario especificar <Casilla>,
        *por "diamond operator", e inferencia de tipos */
        this.propiedades = new ArrayList<Casilla>();
    }

    //Getters y setters
    public String getNombre(){
        return this.nombre;
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
            System.err.println("La casilla no pertenece al jugador\n");
            System.exit(1);
        }
    }

    public ArrayList<Casilla> getPropiedades(Jugador jugador) {
        ArrayList<Casilla> propiedadesJugador = new ArrayList<>();
        for (Casilla casilla : this.propiedades) {         
            if (casilla.getDuenho() != null && casilla.getDuenho().equals(jugador)) {
                propiedadesJugador.add(casilla);
            }   
        }
        return propiedadesJugador;
    }
    

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
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
        System.out.println("Estadísticas de " + jugador.nombre + ":");
        System.out.println("{");
        System.out.println("  dineroInvertido: " + jugador.dineroInvertido + ",");
        System.out.println("  pagoTasasEImpuestos: " + jugador.pagoTasasEImpuestos + ",");
        System.out.println("  pagoDeAlquileres: " + jugador.pagoDeAlquileres + ",");
        System.out.println("  cobroDeAlquileres: " + jugador.cobroDeAlquileres + ",");
        System.out.println("  pasarPorCasillaDeSalida: " + jugador.pasarPorCasillaDeSalida + ",");
        System.out.println("  premiosInversionesOBote: " + jugador.premiosInversionesOBote + ",");
        System.out.println("  vecesEnLaCarcel: " + jugador.vecesEnLaCarcel);
        System.out.println("}");
    }

    public void incrementarVueltas(){
        this.vueltas++;
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
            fortunaTotal += propiedad.getValor();           // Valor de la propiedad
            //fortunaTotal += propiedad.getValorEdificios();  // Valor de los edificios en la propiedad
        }

        return fortunaTotal;
    }

}