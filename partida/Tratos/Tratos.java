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
        System.out.print("Ingrese la cantidad que ofreces: ");
        cantidadOferta = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Solicitar la cantidad de dinero que espera recibir el jugador de oferta
        System.out.print("Ingrese la cantidad que esperas recibir: ");
        cantidadAcepta = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Solicitar las propiedades ofrecidas por el jugador de oferta
        System.out.println("Ingrese los nombres de las propiedades que ofreces, para terminar escribe \"fin\": ");
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
            }else{
                /// QUIZAS HABIA QUE DESTRUIR TODOS OS EDIFICIOS ANTES ///
                propiedadesOferta.add((Propiedad)propiedad);
            }
        }

        // Solicitar las propiedades esperadas del jugador que acepta
        System.out.println("Ingrese los nombres de las propiedades que el jugador espera recibir (separadas por comas): ");
        while(true) {
            String propiedadesAceptaInput = scanner.nextLine();
            if(propiedadesAceptaInput.equals("fin"))
                break;
            
            Casilla propiedad = tablero.obtenerCasilla(propiedadesAceptaInput);
            if(propiedad==null){
                System.out.println("Propiedad no encontrada");
                break;
            }
            if(!(propiedad instanceof Propiedad)){
                System.out.println("La casilla " + propiedad.getNombre() + "no se puede transferir");
                break;
            }
            if(!jugadorAcepta.getPropiedades().contains(propiedad)){
                System.out.println(jugadorAcepta.getNombre() + " no posee esa propiedad aún");
                break;
            }else{
                /// QUIZAS HABIA QUE DESTRUIR TODOS OS EDIFICIOS ANTES ///
                propiedadesAcepta.add((Propiedad)propiedad);
            }
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

    public void rechazarTrato(Jugador jugador) {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean rechazado = false;
    
        // Validar si hay tratos disponibles
        if (tratos.isEmpty()) {
            System.out.println("No hay tratos disponibles para rechazar.");
            return;
        }
    
        do {
            System.out.print("Introduce el número del trato que quieres rechazar (? para listarlos / x para salir): ");
            input = scanner.nextLine().trim(); // Leer la entrada como String
    
            if (input.equals("?")) {
                listarTratos(); // Listar los tratos disponibles
            } else if (input.equalsIgnoreCase("x")) {
                System.out.println("Saliendo del rechazo de tratos...");
                break;
            } else {
                int idRechazo = Integer.parseInt(input); // Convertir la entrada a un número

                for (Trato trato : tratos) {
                    if(trato.getId()==idRechazo){
                        if(!(trato.getJugador_oferta().equals(jugador)||trato.getJugador_acepta().equals(jugador))){
                            System.out.println("El jugador " + jugador.getNombre() + " no forma parte del trato " + trato.getId());
                        }
                        rechazado = true;
                        tratos.remove(trato);
                        System.out.println("El trato con ID " + idRechazo + " ha sido rechazado.");
                    }
                }
                
                // Validar si el idRechazo es válido
                if (!rechazado) {
                    System.out.println("El número del trato no es válido. Inténtalo de nuevo.");
                }
            }
        } while (rechazado);
    } 

    public void listarTratos(){
        boolean hayTratos = false;
        for (Trato trato : tratos) {
            System.out.println(trato.toString() + "\n");
            hayTratos = true;
        }
        if(!hayTratos)
            System.out.println("No hay tratos propuestos");
    }
}