package Clases;

public class Vaca {
    private String codigo;
    private String raza;
    private double peso;
    private int edad;
    private String establo;

    public Vaca(String codigo, String raza, double peso, int edad, String establo) {
        this.codigo = codigo;
        this.raza = raza;
        this.peso = peso;
        this.edad = edad;
        this.establo = establo;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getRaza() { return raza; }
    public double getPeso() { return peso; }
    public int getEdad() { return edad; }
    public String getEstablo() { return establo; }

}
