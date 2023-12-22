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
public class DatosRegistroSucursal {
    
    private Sucursal sucursal;
    private Direccion direccion;
    private Persona persona;
    private Integer filasAfectadas;
    private String error;
    
    public DatosRegistroSucursal() {
    }

    public DatosRegistroSucursal(Sucursal sucursal, Direccion direccion, Persona persona, Integer filasAfectadas, String error) {
        this.sucursal = sucursal;
        this.direccion = direccion;
        this.persona = persona;
        this.filasAfectadas = filasAfectadas;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Integer getFilasAfectadas() {
        return filasAfectadas;
    }

    public void setFilasAfectadas(Integer filasAfectadas) {
        this.filasAfectadas = filasAfectadas;
    }

   
    

}
