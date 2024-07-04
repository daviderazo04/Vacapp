package Clases;

public class Produccion {

    private int codigoProduccion;
    private String codigoVaca;
    private String fecha;
    private int litros;

    public Produccion(int codigoProduccion, String codigoVaca, String fecha, int litros) {
        this.codigoProduccion = codigoProduccion;
        this.codigoVaca = codigoVaca;
        this.fecha = fecha;
        this.litros = litros;
    }

    public int getCodigoProduccion() {
        return codigoProduccion;
    }

    public String getCodigoVaca() {
        return codigoVaca;
    }

    public String getFecha() {
        return fecha;
    }

    public int getLitros() {
        return litros;
    }

}
