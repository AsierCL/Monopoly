package monopoly.edificios;

public abstract class Edificio {
    private int cantidad;
    private int numMax;

    public Edificio(int numMax) {
        this.cantidad = 0;
        this.numMax = numMax;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumMax() {
        return numMax;
    }

    public boolean Construir(){
        return true;
    }
}
