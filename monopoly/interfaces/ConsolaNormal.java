package monopoly.interfaces;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConsolaNormal implements Consola {
    private final Scanner scanner;

    // Constructor para inicializar el Scanner
    public ConsolaNormal() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void print(String mensaje) {
        if (mensaje != null)
            System.out.println(mensaje);
    }

    @Override
    public void error(String mensaje) {
        System.out.println("\u001B[31m" + mensaje + "\u001B[0m");
    }

    @Override
    public String read(String descripcion) {
        System.out.print(descripcion);
        return scanner.nextLine();
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }

    // Podríamos levar un rexistro do xogo nun archivo, solo eventos importantes:
    // Movimientos, pagos e cobros, compras e ventas...
    public void log(String mensaje) {
        try (FileWriter writer = new FileWriter("log.txt", true)) { // con "true" sobreescribimos no archivo
            writer.write(mensaje + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("\u001B[31mError al escribir en el archivo de log: " + e.getMessage() + "\u001B[0m");
        }
    }
}
