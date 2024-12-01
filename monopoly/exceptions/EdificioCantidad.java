package monopoly.exceptions;

public class EdificioCantidad extends PropiedadException {
    public EdificioCantidad() {
        super("No puedes construír más edificios de este tipo en el solar.");
    }
    
}
