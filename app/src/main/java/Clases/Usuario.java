package Clases;

public class Usuario {
    private int codigoUsuario;
    private String nombreUsuario;
    private String contraseña;

    public Usuario(int codigoUsuario, String nombreUsuario, String contraseña) {
        this.codigoUsuario = codigoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
