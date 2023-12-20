/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.pojo;

/**
 *
 * @author Manuel Hernandez
 */
public class MensajeUsuario {

    private Boolean error;
    private String contenido;
    private Usuario usuario;

    public MensajeUsuario() {
    }

    public MensajeUsuario(Boolean error, String contenido, Usuario usuario) {
        this.error = error;
        this.contenido = contenido;
        this.usuario = usuario;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String mensaje) {
        this.contenido = mensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
