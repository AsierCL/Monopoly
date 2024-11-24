package monopoly;

import partida.*;
import partida.avatares.Avatar;

import java.util.ArrayList;
import java.util.HashMap;

import monopoly.casillas.Casilla;
import monopoly.casillas.Impuesto;
import monopoly.casillas.Propiedades.*;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.banca = banca;
        // No hace falta respecificar que es un array de arrays por ¿inferencia de tipos?
        this.posiciones = new ArrayList<>();
        this.grupos = new HashMap<>();
        generarCasillas(); // Para crear las casillas del tablero
    }

    public ArrayList<ArrayList<Casilla>> getPosiciones() {
        return this.posiciones;
    }

    public HashMap<String, Grupo> getGrupos() {
        return grupos;
    }
    
    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        // Crear y agregar cada lado a la lista 'posiciones'
        ArrayList<Casilla> ladoSur = insertarLadoSur();   // Retorna el lado sur
        posiciones.add(ladoSur);
    
        ArrayList<Casilla> ladoOeste = insertarLadoOeste(); // Retorna el lado oeste
        posiciones.add(ladoOeste);
    
        ArrayList<Casilla> ladoNorte = insertarLadoNorte(); // Retorna el lado norte
        posiciones.add(ladoNorte);
    
        ArrayList<Casilla> ladoEste = insertarLadoEste(); // Retorna el lado este
        posiciones.add(ladoEste);

        Grupo Servicios = new Grupo((Propiedad)obtenerCasilla(12),(Propiedad)obtenerCasilla(28), "Servicios");
        Grupo Estaciones = new Grupo((Propiedad)obtenerCasilla(5),(Propiedad)obtenerCasilla(15),(Propiedad)obtenerCasilla(25), (Propiedad)obtenerCasilla(35), "Servicios");
        this.grupos.put("Rojo", Servicios);
        this.grupos.put(Valor.BLACK, Estaciones);
    }
    
    
    //Método para insertar las casillas del lado norte.
    private ArrayList<Casilla> insertarLadoNorte() {
    ArrayList<Casilla> ladoNorte = new ArrayList<>(); // Creamos un nuevo lado del tablero (sur)

    ladoNorte.add(new Casilla("Parking", "Especial", 20, banca));
    ladoNorte.add(new Solar("Solar12", 21, banca,1142440));
    ladoNorte.add(new Casilla("Suerte", "Especial", 22, banca));
    ladoNorte.add(new Solar("Solar13",23, banca, 1142440));
    ladoNorte.add(new Solar("Solar14",24,banca, 1142440));
    ladoNorte.add(new Transporte("Estacion3", 25, banca, Valor.SUMA_VUELTA));
    ladoNorte.add(new Solar("Solar15",26,banca, 1485172));
    ladoNorte.add(new Solar("Solar16", 27, banca, 1485172));
    ladoNorte.add(new Servicio("Servicio2", 28, banca, Valor.SUMA_VUELTA*0.75f));
    ladoNorte.add(new Solar("Solar17", 29, banca, 1485172));
    ladoNorte.add(new Casilla("IrCarcel", "Especial", 30, banca));
    Grupo G5 = new Grupo((Propiedad)ladoNorte.get(1), (Propiedad)ladoNorte.get(3), (Propiedad)ladoNorte.get(4), Valor.RED);
    Grupo G6 = new Grupo((Propiedad)ladoNorte.get(6), (Propiedad)ladoNorte.get(7), (Propiedad)ladoNorte.get(9), Valor.YELLOW);

    this.grupos.put("Rojo", G5);
    this.grupos.put("Amarillo", G6);
    
    return ladoNorte;
    }

    //Método para insertar las casillas del lado sur.
    private ArrayList<Casilla> insertarLadoSur() {
    ArrayList<Casilla> ladoSur = new ArrayList<>(); // Creamos un nuevo lado del tablero (sur)

    ladoSur.add(new Casilla("Carcel", "Especial", 10, banca));
    ladoSur.add(new Solar("Solar5", 9, banca, 520000));
    ladoSur.add(new Solar("Solar4", 8, banca, 520000));
    ladoSur.add(new Casilla("Suerte1", "Suerte", 7, banca));
    ladoSur.add(new Solar("Solar3", 6, banca, 520000));
    ladoSur.add(new Transporte("Estacion1", 5, banca, Valor.SUMA_VUELTA));
    ladoSur.add(new Impuesto("Impuesto1", 4, banca, (Valor.SUMA_VUELTA/2f)));
    ladoSur.add(new Solar("Solar2", 3, banca, 600000));
    ladoSur.add(new Casilla("Caja1", "Comunidad", 2, banca));
    ladoSur.add(new Solar("Solar1", 1, banca, 600000));
    ladoSur.add(new Casilla("Salida", "Especial", 0, banca));
    Grupo G1 = new Grupo((Propiedad)ladoSur.get(7), (Propiedad)ladoSur.get(9), Valor.BROWN);
    Grupo G2 = new Grupo((Propiedad)ladoSur.get(1), (Propiedad)ladoSur.get(2), (Propiedad)ladoSur.get(4), Valor.CYAN);

    this.grupos.put("Marron", G1);
    this.grupos.put("Cian", G2);
    
    return ladoSur;

}

    //Método que inserta casillas del lado oeste.
    private ArrayList<Casilla> insertarLadoOeste() {
        ArrayList<Casilla> ladoOeste = new ArrayList<>();
        
        ladoOeste.add(new Solar("Solar11", 19, banca, 878800));
        ladoOeste.add(new Solar("Solar10", 18, banca, 878800));
        ladoOeste.add(new Casilla("Caja2", "Especial", 17, banca));
        ladoOeste.add(new Casilla("Solar9", "Solar", 16, 878800, banca));
        ladoOeste.add(new Transporte("Estacion2", 15, banca, Valor.SUMA_VUELTA));
        ladoOeste.add(new Solar("Solar8", 14, banca, 676000));
        ladoOeste.add(new Solar("Solar7", 13, banca, 676000));
        ladoOeste.add(new Servicio("Servicio1", 12, banca, Valor.SUMA_VUELTA*0.75f));
        ladoOeste.add(new Solar("Solar6", 11, banca,676000));
        Grupo G3 = new Grupo((Propiedad)ladoOeste.get(5), (Propiedad)ladoOeste.get(6),(Propiedad)ladoOeste.get(8), Valor.PURPLE);
        Grupo G4 = new Grupo((Propiedad)ladoOeste.get(0), (Propiedad)ladoOeste.get(1), (Propiedad)ladoOeste.get(3), Valor.WHITE); //Puse white porque no hay naranja y es el que falta aquí

        this.grupos.put("Morado", G3);
        this.grupos.put("Gris", G4);
    
        return ladoOeste;
    }
    
    //Método que inserta las casillas del lado este.
    private ArrayList<Casilla> insertarLadoEste() {
        ArrayList<Casilla> ladoEste = new ArrayList<>();

        ladoEste.add(new Solar("Solar18", 31, banca, 1930723.6f));
        ladoEste.add(new Solar("Solar19", 32, banca, 1930723.6f));
        ladoEste.add(new Casilla("Caja3", "Especial", 33, banca));
        ladoEste.add(new Solar("Solar20", 34, banca, 1930723.6f));
        ladoEste.add(new Transporte("Estación4", 35, banca, Valor.SUMA_VUELTA));
        ladoEste.add(new Casilla("Suerte3", "Especial", 36, banca));
        ladoEste.add(new Solar("Solar21", 37,banca, 3764911.02f));
        ladoEste.add(new Impuesto("Impuesto2", 38, banca, Valor.SUMA_VUELTA));
        ladoEste.add(new Solar("Solar22", 39,  banca, 3764911.02f));
        Grupo G7 = new Grupo((Propiedad)ladoEste.get(0), (Propiedad)ladoEste.get(1), (Propiedad)ladoEste.get(3), Valor.GREEN);
        Grupo G8 = new Grupo((Propiedad)ladoEste.get(6), (Propiedad)ladoEste.get(8), Valor.BLUE);

        this.grupos.put("Verde", G7);
        this.grupos.put("Azul", G8);

        return ladoEste;
    }

    //Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        int primeraCasilla = 0;
    
        // Asumiendo que las posiciones se almacenan en el orden: Sur, Oeste, Norte, Este
        ArrayList<Casilla> ladoSur = posiciones.get(0);   // Primer lado
        ArrayList<Casilla> ladoOeste = posiciones.get(1); // Segundo lado
        ArrayList<Casilla> ladoNorte = posiciones.get(2); // Tercer lado
        ArrayList<Casilla> ladoEste = posiciones.get(3);  // Cuarto lado

        String color;
        String colorOeste;
        String colorEste;

        sb.append("┌──────────┬──────────┬──────────┬──────────┬──────────┬──────────┬──────────┬──────────┬──────────┬──────────┬──────────┐\n");

        // 1. Lado Norte
        sb.append("│");
        for (Casilla casilla : ladoNorte) {

            if(casilla.getTipo()=="Transporte"){
                color = Valor.BLACK;
            }else if(casilla.getGrupo() == null){
                color = Valor.RESET; // Valor por defecto (sin color)
            }else{
                color = casilla.getGrupo().getColorGrupo(); 
            }

            //Formatear nombre de la casilla
            String nombreFormateado = String.format("%-10s", casilla.getNombre()); // %-10s alinea el texto a la izquierda en un campo de 10 caracteres
            sb.append(color).append(nombreFormateado).append(Valor.RESET).append("│");
        }
        sb.append("\n");
        sb.append("│");
        
        for (Casilla casilla : ladoNorte) {
            //Miramos el color
            if(casilla.getTipo().equals("Transporte")){
                color = Valor.BLACK;
            }else if(casilla.getGrupo() == null){
                color = Valor.RESET; // Valor por defecto (sin color)
            }else{
                color = casilla.getGrupo().getColorGrupo(); 
            }
            // Si la casilla tiene avatares, los mostramos
            if (casilla.getAvatares() != null && !casilla.getAvatares().isEmpty()) {
                StringBuilder avataresStr = new StringBuilder();
                for (Avatar avatar : casilla.getAvatares()) {
                    avataresStr.append(avatar.getId());
                }
                String avataresFormateados = String.format("%-10s", avataresStr.toString()); //No estoy seguro de si esto esta bien
                sb.append(color).append(avataresFormateados).append(Valor.RESET).append("│");
            } else{
                // Si no hay avatares, imprimir espacio vacío
                sb.append(color).append(String.format("%-10s", "")).append(Valor.RESET).append("│");
            }
        }
        
        sb.append("\n");
        sb.append("├──────────┼──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┼──────────│\n");
        
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 2. Lados Oeste y Este impresos en paralelo con formato
        for (int i = 0; i < ladoOeste.size(); i++) {
            // Línea superior de cada celda
            if (primeraCasilla==1) {
                sb.append("├──────────┤").append("\t\t\t\t\t\t\t\t\t\t\t\t      ").append("├──────────┤\n");
            }
            
            // Lado Oeste
            if(ladoOeste.get(i).getTipo().equals("Transporte")){
                colorOeste = Valor.BLACK;
            }else if(ladoOeste.get(i).getGrupo() == null){
                colorOeste = Valor.RESET; // Valor por defecto (sin color)
            }else{
                colorOeste = ladoOeste.get(i).getGrupo().getColorGrupo(); 
            }
            
            sb.append("│"); // Separador izquierdo para el lado Oeste
            String nombreOesteFormateado = String.format("%-10s", ladoOeste.get(i).getNombre());
            sb.append(colorOeste).append(nombreOesteFormateado).append(Valor.RESET).append("│");
            
            // Espacios entre los lados
            sb.append("\t\t\t\t\t\t\t\t\t\t\t\t      ");
            
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Lado Este
            if(ladoEste.get(i).getTipo().equals("Transporte")){
                colorEste = Valor.BLACK;
            }else if(ladoEste.get(i).getGrupo() == null){
                colorEste = Valor.RESET; // Valor por defecto (sin color)
            }else{
                colorEste = ladoEste.get(i).getGrupo().getColorGrupo(); 
            }

            sb.append("│"); // Separador izquierdo para el lado Este
            String nombreEsteFormateado = String.format("%-10s", ladoEste.get(i).getNombre());
            sb.append(colorEste).append(nombreEsteFormateado).append(Valor.RESET).append("│");

            sb.append("\n");
            sb.append("│");//Separados izquierdo lado oeste linea 2

            //Imprimimos avatares lado Oeste en caso de que tenga
            if (ladoOeste.get(i).getAvatares() != null && !ladoOeste.get(i).getAvatares().isEmpty()) {
                StringBuilder avataresStr = new StringBuilder();
                for (Avatar avatar : ladoOeste.get(i).getAvatares()) {
                    avataresStr.append(avatar.getId());
                }
                String avataresFormateados = String.format("%-10s", avataresStr.toString());
                sb.append(colorOeste).append(avataresFormateados);
            } else{
                sb.append(colorOeste).append("          ");
            }
            sb.append(Valor.RESET).append("│");
            sb.append("\t\t\t\t\t\t\t\t\t\t\t\t      "); //Tabulación para imprimir en la siguiente columna
            sb.append("│");

            //Imprimimos avatares lado Este en caso de que tenga
            if (ladoEste.get(i).getAvatares() != null && !ladoEste.get(i).getAvatares().isEmpty()) {
                StringBuilder avataresStr = new StringBuilder();
                for (Avatar avatar : ladoEste.get(i).getAvatares()) {
                    avataresStr.append(avatar.getId());
                }
                String avataresFormateados = String.format("%-10s", avataresStr.toString());
                sb.append(colorEste).append(avataresFormateados);
            } 
            else{
                sb.append(colorEste).append("          ");
            }
            sb.append(Valor.RESET).append("│\n");

            primeraCasilla = 1;
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 3. Lado Sur - Formato con separadores como el lado norte
        sb.append(Valor.RESET).append("├──────────┼──────────┬──────────┬──────────┬──────────┬──────────┬──────────┬──────────┬──────────┬──────────┼──────────┤\n");
        sb.append("│");
        for (Casilla casilla : ladoSur) {

            if(casilla.getTipo().equals("Transporte")){
                color = Valor.BLACK;
            }else if(casilla.getGrupo() == null){
                color = Valor.RESET; // Valor por defecto (sin color)
            }else{
                color = casilla.getGrupo().getColorGrupo(); 
            }

            //Formatear nombre casilla
            String nombreFormateado = String.format("%-10s", casilla.getNombre());
            sb.append(color).append(nombreFormateado).append(Valor.RESET).append("│");
        }

        sb.append("\n");
        sb.append("│");
        
        
        for (Casilla casilla : ladoSur) {
            //Miramos el color
            if(casilla.getTipo().equals("Transporte")){
                color = Valor.BLACK;
            }else if(casilla.getGrupo() == null){
                color = Valor.RESET; // Valor por defecto (sin color)
            }else{
                color = casilla.getGrupo().getColorGrupo(); 
            }
            // Si la casilla tiene avatares, los mostramos
            if (casilla.getAvatares() != null && !casilla.getAvatares().isEmpty()) {
                StringBuilder avataresStr = new StringBuilder();
                for (Avatar avatar : casilla.getAvatares()) {
                    avataresStr.append(avatar.getId());
                }
                String avataresFormateados = String.format("%-10s", avataresStr.toString());
                sb.append(color).append(avataresFormateados).append(Valor.RESET).append("│");
            } else{
                // Si no hay avatares, imprimir espacio vacío
                sb.append(color).append(String.format("%-10s", "")).append(Valor.RESET).append("│");
            }
        }
        
        sb.append("\n");


        sb.append("└──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┘\n");

        return sb.toString();
    }
    


    //Funcion para obtener casilla por índice
    public Casilla obtenerCasilla(int posicion) {
        for (ArrayList<Casilla> lado : this.getPosiciones()) {
            for (Casilla casilla : lado) {
                if (casilla.getPosicion()==posicion) {
                    return casilla;  
                }
            }
        }
        return null;
    }

    //Funcion para obtener casilla por nombre.
    public Casilla obtenerCasilla(String nombre) {
        for (ArrayList<Casilla> lado : this.getPosiciones()) {
            for (Casilla casilla : lado) {
                if (casilla.getNombre().equalsIgnoreCase(nombre)) {
                    return casilla;  
                }
            }
        }
        return null;
    }
}