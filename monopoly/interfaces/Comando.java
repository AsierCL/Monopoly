package monopoly.interfaces;

import partida.avatares.Avatar;

public interface Comando {

    // Métodos abstractos de comandos
    void verTurno();

    void listarJugadores();

    void listarAvatares();

    void listarVenta();

    void listarEdificios();

    void listarEdificiosPorColor(String color);

    void realizarLanzamiento();

    void realizarLanzamientoTrucado();

    void acabarTurno();

    void salirCarcel();

    void descJugador(String[] partes);

    void descAvatar(String ID);

    void descCasilla(String nombre);

    void hipotecar(String nombre);

    void deshipotecar(String nombre);

    void bancarrota(String nombre);

    void comprar(String nombre);

    void vender(String[] partes);

    void cambiarModo(Avatar avatar);

    void printAyuda();
}

