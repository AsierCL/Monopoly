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
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;

        if(Avatar.tiposValidos.contains(tipoAvatar)){
            this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
        }else{
            System.out.println("\nTipo de avatar incorrecto\n");
        }
        this.fortuna = 14000000;//Usar variable global FORTUNA_INICIAL
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;

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
        this.propiedades = new ArrayList<>();
        for (Casilla casilla : this.propiedades) {         
            if (casilla.getDuenho() != null && casilla.getDuenho().equals(jugador)) {
                propiedades.add(casilla);
            }   
        }
    
        return propiedades;
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
        this.avatar.setLugar(pos.get(2).get(10));
        pos.get(2).get(10).anhadirAvatar(this.avatar);
    }

}
