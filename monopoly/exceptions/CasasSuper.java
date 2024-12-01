package monopoly.exceptions;

public class CasasSuper extends PropiedadException {
    public CasasSuper() {
        super("Superas el máximo de casas. Debes eliminarlas antes de construir un hotel\".");
    }
}