package monopoly.interfaces;

public interface Consola {
    void print(String mensaje); // Para imprimir mensajes al usuario
    void error(String mensaje); // Para imprimir mensajes de error
    String read(String descripcion); // Para leer datos desde el usuario
}
