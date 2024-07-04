package Clases;

public class Establo {
    private String codigo;
    private int usuario;
    private double ancho;
    private double largo;

    public Establo(String codigo, int usuario, double ancho, double largo) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.ancho = ancho;
        this.largo = largo;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getUsuario() {
        return usuario;
    }

    public double getAncho() {
        return ancho;
    }

    public double getLargo() {
        return largo;
    }
}
