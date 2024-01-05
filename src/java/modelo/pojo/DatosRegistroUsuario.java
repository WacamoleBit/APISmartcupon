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
public class DatosRegistroUsuario {

    private Usuario usuario;
    private Integer filasAfectadas;
    private String error;

    public DatosRegistroUsuario() {
    }

    public DatosRegistroUsuario(Usuario usuario, Integer filasAfectadas, String error) {
        this.usuario = usuario;
        this.filasAfectadas = filasAfectadas;
        this.error = error;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getFilasAfectadas() {
        return filasAfectadas;
    }

    public void setFilasAfectadas(Integer filasAfectadas) {
        this.filasAfectadas = filasAfectadas;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
