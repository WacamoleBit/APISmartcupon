/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.pojo;

/**
 *
 * @author jegal
 */
public class Empresa {
    private Integer idEmpresa;
    private String noombre;
    private String nombreComercial;
    private byte [] logo;
    private String logoBase64;
    private String representante;
    private String email;
    private String direccion;
    private Integer telefono;
    private String paginaWeb;
    private String rfc;
    private Integer estatus;

    public Empresa() {
    }

    public Empresa(Integer idEmpresa, String noombre, String nombreComercial, byte[] logo, String logoBase64, String representante, String email, String direccion, Integer telefono, String paginaWeb, String rfc, Integer estatus) {
        this.idEmpresa = idEmpresa;
        this.noombre = noombre;
        this.nombreComercial = nombreComercial;
        this.logo = logo;
        this.logoBase64 = logoBase64;
        this.representante = representante;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.rfc = rfc;
        this.estatus = estatus;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public String getNoombre() {
        return noombre;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public byte[] getLogo() {
        return logo;
    }

    public String getLogoBase64() {
        return logoBase64;
    }

    public String getRepresentante() {
        return representante;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public String getRfc() {
        return rfc;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setNoombre(String noombre) {
        this.noombre = noombre;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public void setLogoBase64(String logoBase64) {
        this.logoBase64 = logoBase64;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
}
