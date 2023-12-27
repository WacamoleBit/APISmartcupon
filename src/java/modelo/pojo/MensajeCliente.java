/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.pojo;

/**
 *
 * @author Dell
 */
public class MensajeCliente {
    
    private Boolean error;
    private String mensaje;
    private Cliente cliente;
    
    public MensajeCliente() {
    }

    public MensajeCliente(Boolean error, String mensaje, Cliente cliente) {
        this.error = error;
        this.mensaje = mensaje;
        this.cliente = cliente;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
