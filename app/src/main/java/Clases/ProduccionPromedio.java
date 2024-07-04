package Clases;

public class ProduccionPromedio {
    private String vacaCodigo;
    private double promedio;

    public ProduccionPromedio()
    {

    }
    public ProduccionPromedio(String vacaCodigo, double promedio) {
        this.vacaCodigo = vacaCodigo;
        this.promedio = promedio;
    }

    public String getVacaCodigo() {
        return vacaCodigo;
    }

    public double getPromedio() {
        return promedio;
    }
}
