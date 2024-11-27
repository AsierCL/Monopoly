package partida.Tratos;

import java.util.ArrayList;
import java.util.Scanner;

import monopoly.casillas.Casilla;
import monopoly.casillas.Propiedades.Propiedad;
import monopoly.Juego;
import monopoly.Tablero;
import partida.Jugador;

public class Tratos {
    ArrayList<Trato> tratos;
    int id;
    
    public Tratos(){
        this.tratos = new ArrayList<>();
        this.id = 0;
    }

    public void proponerTrato(Jugador jugadorOferta, ArrayList<Jugador> jugadores, Tablero tablero) {
        Scanner scanner = new Scanner(System.in);
        Jugador jugadorAcepta;
        int cantidadOferta;
        int cantidadAcepta;
        ArrayList<Propiedad> propiedadesOferta = new ArrayList<>();
        ArrayList<Propiedad> propiedadesAcepta = new ArrayList<>();

        System.out.println("Proponiendo un nuevo trato...");

        // Solicitar el jugador que recibe la oferta
        System.out.print("Ingrese el nombre del jugador que recibe la oferta: ");
        String nombreJugadorAcepta = scanner.nextLine();
        jugadorAcepta = Juego.buscarJugadorPorNombre(nombreJugadorAcepta, jugadores);

        if (jugadorAcepta == null) {
            System.out.println("El jugador que recibe la oferta no existe.");
            return;
        }
        if(jugadorAcepta.equals(jugadorOferta)){
            System.out.println("No puedes proponerte un trato a ti mismo");
            return;
        }

        // Solicitar la cantidad de dinero que ofrece el jugador de oferta
        System.out.print("Ingrese la cantidad que el jugador ofrece: ");
        cantidadOferta = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Solicitar la cantidad de dinero que espera recibir el jugador de oferta
        System.out.print("Ingrese la cantidad que el jugador espera recibir: ");
        cantidadAcepta = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Solicitar las propiedades ofrecidas por el jugador de oferta
        System.out.println("Ingrese los nombres de las propiedades que el jugador ofrece, para terminar escribe \"fin\": ");
        while(true) {
            String propiedadesOfertaInput = scanner.nextLine();
            if(propiedadesOfertaInput.equals("fin"))
                break;
            
            Casilla propiedad = tablero.obtenerCasilla(propiedadesOfertaInput);
            
            if(propiedad==null){
                System.out.println("Propiedad no encontrada");
                break;
            }if(!(propiedad instanceof Propiedad)){
                System.out.println("La casilla " + propiedad.getNombre() + "no se puede transferir");
                break;
            }if(!jugadorOferta.getPropiedades().contains(propiedad)){
                System.out.println("No posees esa propiedad aún");
                break;
            }

            /// QUIZAS HABIA QUE DESTRUIR TODOS OS EDIFICIOS ANTES ///
            propiedadesOferta.add((Propiedad)propiedad);
        }

        // Solicitar las propiedades esperadas del jugador que acepta
        System.out.println("Ingrese los nombres de las propiedades que el jugador espera recibir (separadas por comas): ");
        while(true) {
            String propiedadesAceptaInput = scanner.nextLine();
            if(propiedadesAceptaInput.equals("fin"))
                break;
            
            Casilla propiedad = tablero.obtenerCasilla(propiedadesAceptaInput);
            if(propiedad==null)
                System.out.println("Propiedad no encontrada");
            if(!(propiedad instanceof Propiedad))
                System.out.println("La casilla " + propiedad.getNombre() + "no se puede transferir");
            if(!jugadorAcepta.getPropiedades().contains(propiedad))
                System.out.println("No posees esa propiedad aún");
            /// QUIZAS HABIA QUE DESTRUIR TODOS OS EDIFICIOS ANTES ///
            propiedadesAcepta.add((Propiedad)propiedad);
        }

        // Crear el nuevo trato
        Trato nuevoTrato = new Trato(id, jugadorOferta, jugadorAcepta, cantidadOferta, cantidadAcepta, propiedadesOferta, propiedadesAcepta);

        // Agregarlo a la lista de tratos
        tratos.add(nuevoTrato);

        System.out.println("Trato propuesto con éxito. ID del trato: " + id);
        id++;
    }

    
    public void aceptarTrato(){
        //REVISAR QUE A PROPIEDAD INDA PERTENEZCA A OS XOGADORES
    }

    public void rechazarTrato(){

    }

    public void listarTratos(){
        boolean hayTratos = false;
        for (Trato trato : tratos) {
            System.out.println(trato.toString() + "\n");
            hayTratos = true;
        }
        if(!hayTratos)
            System.out.println("No hay tratos propuestos");
        System.out.println("\n");
    }
}