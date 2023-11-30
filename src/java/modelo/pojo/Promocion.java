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
public class Promocion {
    private Integer idPromocion;
    private String nombre;
    private String descripcion;
    private byte [] imagen;
    private String imagenBase64;
    private String fechaInicio;
    private String fechaTermino;
    private String restricciones;
    private Integer tipoPromocion;
    private Integer porcentajeDescuento;
    private Integer categoria;
    private Integer cuponesDisponibles;
    private String codigoPromocion;
    private Integer estatus;
    private Integer empresa;

    public Promocion() {
    }

    public Promocion(Integer idPromocion, String nombre, String descripcion, byte[] imagen, String imagenBase64, String fechaInicio, String fechaTermino, String restricciones, Integer tipoPromocion, Integer porcentajeDescuento, Integer categoria, Integer cuponesDisponibles, String codigoPromocion, Integer estatus, Integer empresa) {
        this.idPromocion = idPromocion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.imagenBase64 = imagenBase64;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.restricciones = restricciones;
        this.tipoPromocion = tipoPromocion;
        this.porcentajeDescuento = porcentajeDescuento;
        this.categoria = categoria;
        this.cuponesDisponibles = cuponesDisponibles;
        this.codigoPromocion = codigoPromocion;
        this.estatus = estatus;
        this.empresa = empresa;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public String getRestricciones() {
        return restricciones;
    }

    public Integer getTipoPromocion() {
        return tipoPromocion;
    }

    public Integer getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public Integer getCuponesDisponibles() {
        return cuponesDisponibles;
    }

    public String getCodigoPromocion() {
        return codigoPromocion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public Integer getEmpresa() {
        return empresa;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public void setRestricciones(String restricciones) {
        this.restricciones = restricciones;
    }

    public void setTipoPromocion(Integer tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public void setPorcentajeDescuento(Integer porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public void setCuponesDisponibles(Integer cuponesDisponibles) {
        this.cuponesDisponibles = cuponesDisponibles;
    }

    public void setCodigoPromocion(String codigoPromocion) {
        this.codigoPromocion = codigoPromocion;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = empresa;
    }
    
}