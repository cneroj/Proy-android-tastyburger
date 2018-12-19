package com.example.alumno.proytastyburger.Entidades;

/**
 * Created by Alumno on 22/06/2018.
 */

public class Usuario {
    private String idUsuario;
    private String user;
    private String contra;
    private String direccion;
    private String telefono;
    private String correo;
    private String foto;



    public Usuario() {
    }

    public Usuario(String idUsuario, String user, String contra, String direccion, String telefono, String correo,String foto) {
        this.idUsuario = idUsuario;
        this.user = user;
        this.contra = contra;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.foto = foto;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
