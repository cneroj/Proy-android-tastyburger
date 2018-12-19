package com.example.alumno.proytastyburger.Entidades;

/**
 * Created by Alumno-CT on 27/06/2018.
 */

public class Categoria {
    private String idCategoria,nombreCate,fotoCate;

    public Categoria(String idCategoria, String nombreCate, String fotoCate) {
        this.idCategoria = idCategoria;
        this.nombreCate = nombreCate;
        this.fotoCate = fotoCate;
    }

    public Categoria() {
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCate() {
        return nombreCate;
    }

    public void setNombreCate(String nombreCate) {
        this.nombreCate = nombreCate;
    }

    public String getFotoCate() {
        return fotoCate;
    }

    public void setFotoCate(String fotoCate) {
        this.fotoCate = fotoCate;
    }
}
