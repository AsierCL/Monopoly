package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;


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
    }
    
    
    //Método para insertar las casillas del lado norte.
    private ArrayList<Casilla> insertarLadoNorte() {
    ArrayList<Casilla> ladoNorte = new ArrayList<>(); // Creamos un nuevo lado del tablero (sur)

    ladoNorte.add(new Casilla("Parking", "Especial", 20, banca));
    ladoNorte.add(new Casilla("Solar12", "Solar", 21, 1142440, banca));
    ladoNorte.add(new Casilla("Suerte", "Especial", 22, banca));
    ladoNorte.add(new Casilla("Solar13", "Solar", 23, 1142440, banca));
    ladoNorte.add(new Casilla("Solar14", "Solar", 24, 1142440, banca));
    ladoNorte.add(new Casilla("Estacion3", "Transporte", 25, Valor.SUMA_VUELTA, banca));
    ladoNorte.add(new Casilla("Solar15", "Solar", 26, 4455516, banca));
    ladoNorte.add(new Casilla("Solar16", "Solar", 27, 4455516, banca));
    ladoNorte.add(new Casilla("Servicio2", "Servicios", 28, banca));
    ladoNorte.add(new Casilla("Solar17", "Solar", 29, 4455516, banca));
    ladoNorte.add(new Casilla("IrCarcel", "Especial", 30, banca));
    new Grupo(ladoNorte.get(1), ladoNorte.get(3), ladoNorte.get(4), Valor.RED);
    new Grupo(ladoNorte.get(6), ladoNorte.get(7), ladoNorte.get(9), Valor.YELLOW);
    
    return ladoNorte;
    }

    //Método para insertar las casillas del lado sur.
    private ArrayList<Casilla> insertarLadoSur() {
    ArrayList<Casilla> ladoSur = new ArrayList<>(); // Creamos un nuevo lado del tablero (sur)

    ladoSur.add(new Casilla("Carcel", "Especial", 10, banca));
    ladoSur.add(new Casilla("Solar5", "Solar", 9, 520000, banca));
    ladoSur.add(new Casilla("Solar4", "Solar", 8, 520000, banca));
    ladoSur.add(new Casilla("Suerte1", "Suerte", 7, banca));
    ladoSur.add(new Casilla("Solar3", "Solar", 6, 520000, banca));
    ladoSur.add(new Casilla("Estacion1", "Transporte", 5, Valor.SUMA_VUELTA, banca));
    ladoSur.add(new Casilla("Impuesto1", 4, (Valor.SUMA_VUELTA/2f), banca));
    ladoSur.add(new Casilla("Solar2", "Solar", 3, 600000, banca));
    ladoSur.add(new Casilla("Caja1", "Comunidad", 2, banca));
    ladoSur.add(new Casilla("Solar1", "Solar", 1, 600000, banca));
    ladoSur.add(new Casilla("Salida", "Especial", 0, banca));
    new Grupo(ladoSur.get(1), ladoSur.get(2), ladoSur.get(4), Valor.CYAN);
    new Grupo(ladoSur.get(7), ladoSur.get(9), Valor.BROWN);
    
    return ladoSur;

}

    //Método que inserta casillas del lado oeste.
    private ArrayList<Casilla> insertarLadoOeste() {
        ArrayList<Casilla> ladoOeste = new ArrayList<>();
        
        ladoOeste.add(new Casilla("Solar11", "Solar", 19, 878000, banca));
        ladoOeste.add(new Casilla("Solar10", "Solar", 18, 878000, banca));
        ladoOeste.add(new Casilla("Caja2", "Especial", 17, banca));
        ladoOeste.add(new Casilla("Solar9", "Solar", 16, 878000, banca));
        ladoOeste.add(new Casilla("Estacion2", "Transporte", 15, Valor.SUMA_VUELTA, banca));
        ladoOeste.add(new Casilla("Solar8", "Solar", 14, 676000, banca));
        ladoOeste.add(new Casilla("Solar7", "Solar", 13, 676000, banca));
        ladoOeste.add(new Casilla("Servicio1", "Servicios", 12, banca));
        ladoOeste.add(new Casilla("Solar6", "Solar", 11, 676000, banca));
        new Grupo(ladoOeste.get(0), ladoOeste.get(1), ladoOeste.get(3), Valor.WHITE); //Puse white porque no hay naranja y es el que falta aquí
        new Grupo(ladoOeste.get(5), ladoOeste.get(6),ladoOeste.get(8), Valor.PURPLE);
    
        return ladoOeste;
    }
    
    //Método que inserta las casillas del lado este.
    private ArrayList<Casilla> insertarLadoEste() {
        ArrayList<Casilla> ladoEste = new ArrayList<>();

        ladoEste.add(new Casilla("Solar18", "Solar", 31, 2970344, banca));
        ladoEste.add(new Casilla("Solar19", "Solar", 32, 2970344,  banca));
        ladoEste.add(new Casilla("Caja3", "Especial", 33, banca));
        ladoEste.add(new Casilla("Solar20", "Solar", 34, 2970344,  banca));
        ladoEste.add(new Casilla("Estación4", "Transporte",35, Valor.SUMA_VUELTA ,banca));
        ladoEste.add(new Casilla("Suerte3", "Especial", 36, banca));
        ladoEste.add(new Casilla("Solar21", "Solar", 37, 5792170, banca));
        ladoEste.add(new Casilla("Impuesto2", 38, Valor.SUMA_VUELTA, banca));
        ladoEste.add(new Casilla("Solar22", "Solar", 39,  5792170, banca));
        new Grupo(ladoEste.get(0), ladoEste.get(1), ladoEste.get(3), Valor.GREEN);
        new Grupo(ladoEste.get(6), ladoEste.get(8), Valor.BLUE);
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

        ArrayList<Grupo> grupos = new ArrayList<>();

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
                if (casilla.getNombre().equals(nombre)) {
                    return casilla;  
                }
            }
        }
        return null;
    }
}





