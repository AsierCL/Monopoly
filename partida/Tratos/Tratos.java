package partida.Tratos;

import java.util.ArrayList;

import monopoly.casillas.Casilla;
import monopoly.casillas.Propiedades.Propiedad;
import monopoly.Juego;
import monopoly.Tablero;
import partida.Jugador;
import monopoly.exceptions.*;

public class Tratos {
    private ArrayList<Trato> tratos;
    private int id;
    
    public Tratos(){
        this.tratos = new ArrayList<>();
        this.id = 0;
    }

    public void proponerTrato(Jugador jugadorOferta, ArrayList<Jugador> jugadores, Tablero tablero) {
        Jugador jugadorAcepta;
        float cantidadOferta;
        float cantidadAcepta;
        ArrayList<Propiedad> propiedadesOferta = new ArrayList<>();
        ArrayList<Propiedad> propiedadesAcepta = new ArrayList<>();

        Juego.consola.print("Nuevo trato...");

        // Solicitar el jugador que recibe la oferta
        String nombreJugadorAcepta = Juego.consola.read("Ingrese el nombre del jugador que recibe la oferta: ");
        jugadorAcepta = Juego.buscarJugadorPorNombre(nombreJugadorAcepta, jugadores);

        if(jugadorAcepta == null) {
            Juego.consola.print("El jugador que recibe la oferta no existe.");
            return;
        }
        if(jugadorAcepta.equals(jugadorOferta)){
            Juego.consola.error("No puedes proponerte un trato a ti mismo");
            return;
        }

        // Solicitar la cantidad de dinero que ofrece el jugador de oferta
        cantidadOferta = Float.parseFloat(Juego.consola.read("Ingrese la cantidad que ofreces: "));

        // Solicitar la cantidad de dinero que espera recibir el jugador de oferta
        cantidadAcepta = Float.parseFloat(Juego.consola.read("Ingrese la cantidad que esperas recibir: "));

        // Solicitar las propiedades ofrecidas por el jugador de oferta
        Juego.consola.print("Ingrese los nombres de las propiedades que ofreces, para terminar escribe \"fin\": ");
        while(true) {
            String propiedadesOfertaInput = Juego.consola.read();
            if(propiedadesOfertaInput.equals("fin"))
                break;
            
            Casilla propiedad = tablero.obtenerCasilla(propiedadesOfertaInput);
            
            if(propiedad==null){
                Juego.consola.error("Propiedad no encontrada");
                continue;
            }else if(!(propiedad instanceof Propiedad)){
                Juego.consola.error("La casilla " + propiedad.getNombre() + " no se puede transferir");
                continue;
            }else{
                propiedadesOferta.add((Propiedad)propiedad);
            }
        }

        // Solicitar las propiedades esperadas del jugador que acepta
        Juego.consola.print("Ingrese los nombres de las propiedades que el jugador espera recibir, para terminar escribe \"fin\": ");
        while(true) {
            String propiedadesAceptaInput = Juego.consola.read();
            if(propiedadesAceptaInput.equals("fin"))
                break;
            
            Casilla propiedad = tablero.obtenerCasilla(propiedadesAceptaInput);
            
            if(propiedad==null){
                Juego.consola.error("Propiedad no encontrada");
                continue;
            }else if(!(propiedad instanceof Propiedad)){
                Juego.consola.error("La casilla " + propiedad.getNombre() + " no se puede transferir");
                continue;
            }else{
                propiedadesAcepta.add((Propiedad)propiedad);
            }
        }

        // Crear el nuevo trato
        Trato nuevoTrato = new Trato(id, jugadorOferta, jugadorAcepta, cantidadOferta, cantidadAcepta, propiedadesOferta, propiedadesAcepta);

        // Agregarlo a la lista de tratos
        tratos.add(nuevoTrato);

        Juego.consola.print("Trato propuesto con éxito. ID del trato: " + id);
        id++;
    }
    
    public void aceptarTrato(Jugador jugador){
        String input;
        boolean valido = false;
    
        // Validar si hay tratos disponibles
        if (tratos.isEmpty()) {
            Juego.consola.print("No hay tratos disponibles para rechazar.");
            return;
        }
    
        do {
            input = Juego.consola.read("Introduce el número del trato que quieres aceptar (? para listarlos / x para salir): "); // Leer la entrada como String
    
            if (input.equals("?")) {
                listarTratos(); // Listar los tratos disponibles
            } else if (input.equalsIgnoreCase("x")) {
                Juego.consola.print("Saliendo de tratos...");
                break;
            } else {
                int idAcepta = Integer.parseInt(input); // Convertir la entrada a un número

                for (Trato trato : tratos) {
                    if(trato.getId()==idAcepta){
                        if(!trato.getJugador_acepta().equals(jugador)){
                            Juego.consola.error("El trato " + trato.getId() + " es para el jugador " + trato.getJugador_acepta().getNombre());
                            break;
                        }
                        if(!propiedadesHipotecadas(trato)){
                            return;
                        }

                        try {
                            ejecucionTrato(trato);
                            valido = true;
                            Juego.consola.print("El trato con ID " + idAcepta + " ha sido aceptado.");
                            return;
                        } catch (TratosException e) {
                            Juego.consola.error(e.getMessage());
                        }
                    }
                }
                
                // Validar si el idRechazo es válido
                if (!valido) {
                    Juego.consola.error("El número del trato no es válido. Inténtalo de nuevo.");
                }
            }
        } while (true);
    }

    public void rechazarTrato(Jugador jugador) {
        String input;
        boolean rechazado = false;
    
        // Validar si hay tratos disponibles
        if (tratos.isEmpty()) {
            Juego.consola.print("No hay tratos disponibles para rechazar.");
            return;
        }
    
        do {
            input = Juego.consola.read("Introduce el número del trato que quieres rechazar (? para listarlos / x para salir): "); // Leer la entrada como String
    
            if (input.equals("?")) {
                listarTratos(); // Listar los tratos disponibles
            } else if (input.equalsIgnoreCase("x")) {
                Juego.consola.print("Saliendo del rechazo de tratos...");
                break;
            } else {
                int idRechazo = Integer.parseInt(input); // Convertir la entrada a un número

                for (Trato trato : tratos) {
                    if(trato.getId()==idRechazo){
                        if(!(trato.getJugador_oferta().equals(jugador)||trato.getJugador_acepta().equals(jugador))){
                            Juego.consola.print("El jugador " + jugador.getNombre() + " no forma parte del trato " + trato.getId());
                        }
                        rechazado = true;
                        tratos.remove(trato);
                        Juego.consola.print("El trato con ID " + idRechazo + " ha sido rechazado.");
                        break;
                    }
                }
                
                // Validar si el idRechazo es válido
                if (!rechazado) {
                    Juego.consola.print("El número del trato no es válido. Inténtalo de nuevo.");
                }
            }
        } while (!rechazado);
    }

    public void listarTratos(){
        boolean hayTratos = false;
        for (Trato trato : tratos) {
            Juego.consola.print(trato.toString() + "\n");
            hayTratos = true;
        }
        if(!hayTratos)
            Juego.consola.print("No hay tratos propuestos");
    }

    //Aux
    private void ejecucionTrato(Trato trato) throws TratosException{
        for (Propiedad propiedad : trato.getPropiedades_oferta()) {
            if(!trato.getJugador_oferta().getPropiedades().contains(propiedad)){
                throw new TratosException("El jugador " + trato.getJugador_oferta().getNombre() + " no posee la propiedad " + propiedad.getNombre());
            }
        }

        for (Propiedad propiedad : trato.getPropiedades_acepta()) {
            if(!trato.getJugador_acepta().getPropiedades().contains(propiedad)){
                throw new TratosException("El jugador " + trato.getJugador_acepta().getNombre() + " no posee la propiedad " + propiedad.getNombre());
            }
        }

        if(trato.getJugador_oferta().getFortuna()<trato.getCantidad_oferta()){
            throw new TratosException("El jugador " + trato.getJugador_oferta().getNombre() + " no tiene el dinero suficiente para el trato (" + trato.getCantidad_oferta() +")");
        }

        if(trato.getJugador_acepta().getFortuna()<trato.getCantidad_acepta()){
            throw new TratosException(("El jugador " + trato.getJugador_acepta().getNombre() + " no tiene el dinero suficiente para el trato (" + trato.getCantidad_acepta() +")"));
        }

        trato.getJugador_acepta().incrementarDineroInvertido(trato.getCantidad_acepta());
        trato.getJugador_oferta().sumarFortuna(trato.getCantidad_acepta());

        trato.getJugador_oferta().incrementarDineroInvertido(trato.getCantidad_oferta());
        trato.getJugador_acepta().sumarFortuna(trato.getCantidad_oferta());

        for (Propiedad propiedad : trato.getPropiedades_acepta()) {
            propiedad.cambiarDuenho(trato.getJugador_oferta());
        }
        for (Propiedad propiedad : trato.getPropiedades_oferta()) {
            propiedad.cambiarDuenho(trato.getJugador_acepta());
        }

        tratos.remove(trato);
    }

    private boolean propiedadesHipotecadas(Trato trato){
        boolean hayPropHipot = false;
        String continuar;
        for (Propiedad propiedad : trato.getPropiedades_acepta()) {
            if(propiedad.getHipotecada()){
                hayPropHipot = true;
                Juego.consola.print("La propiedad " + propiedad.getNombre() + " está hipotecada");
            }
        }
        
        for (Propiedad propiedad : trato.getPropiedades_oferta()) {
            if(propiedad.getHipotecada()){
                hayPropHipot = true;
                Juego.consola.print("La propiedad " + propiedad.getNombre() + " está hipotecada");
            }
        }

        if(hayPropHipot){
            while(true){
                Juego.consola.print("Quieres continuar el trato con estas hipotecas?");
                continuar = Juego.consola.read("[si/no]:");
                if(continuar.equals("si"))
                    return true;
                else if(continuar.equals("no"))
                    return false;
                else{
                    Juego.consola.error("Opción incorrecta");
                }
            }
        }else{
            return true;
        }
    }
}