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
public class DatosRegistroEmpresa {
    private Empresa empresa;
    private Persona persona;
    private Direccion direccion;
    private Integer filasAfectadas;

    public DatosRegistroEmpresa() {
    }

    public DatosRegistroEmpresa(Empresa empresa, Persona persona, Direccion direccion, Integer filasAfectadas) {
        this.empresa = empresa;
        this.persona = persona;
        this.direccion = direccion;
        this.filasAfectadas = filasAfectadas;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Integer getFilasAfectadas() {
        return filasAfectadas;
    }

    public void setFilasAfectadas(Integer filasAfectadas) {
        this.filasAfectadas = filasAfectadas;
    }
    
    
}
