package monopoly.interfaces;


import java.util.Scanner;

public class ConsolaNormal implements Consola {
    private final Scanner scanner;

    // Constructor para inicializar el Scanner
    public ConsolaNormal() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void print(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public void error(String mensaje) {
        System.out.println("\u001B[31m" + mensaje + "\u001B[0m");
    }

    @Override
    public String read(String descripcion) {
        print(descripcion); // Muestra el mensaje antes de leer
        return scanner.nextLine(); // Lee el input del usuario
    }
}

