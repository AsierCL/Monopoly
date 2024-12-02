package monopoly.interfaces;

import partida.avatares.Avatar;

public interface Comando {

    // Métodos abstractos de comandos
    public void verTurno();

    public void listarJugadores();

    public void listarAvatares();

    public void listarVenta();

    public void listarEdificios();

    public void listarEdificiosPorColor(String color);

    public void lanzarDados(boolean trucados);

    public void acabarTurno();

    public void salirCarcel();

    public void descJugador(String[] partes);

    public void descAvatar(String ID);

    public void descCasilla(String nombre);

    public void hipotecar(String nombre);

    public void deshipotecar(String nombre);

    public void bancarrota(String nombre);

    public void comprar(String nombre);

    public void vender(String[] partes);

    public void mostrarEstadisticasJuego();

    public void mostrarEstadisticasJugadorPorNombre(String nombreJugador);

    public void cambiarModo(Avatar avatar);

    public void printAyuda();
}
