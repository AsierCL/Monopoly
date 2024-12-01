package monopoly.exceptions;

public class EdificioIncorrecto extends PropiedadException {
    public EdificioIncorrecto() {
        super("No existe este tipo de edificio. \n Tipos: casa | hotel | piscina | pista ");
    }
    
}
