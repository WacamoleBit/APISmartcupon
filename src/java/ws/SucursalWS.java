/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.SucursalDAO;
import modelo.pojo.DatosRegistroSucursal;
import modelo.pojo.Direccion;
import modelo.pojo.Mensaje;
import modelo.pojo.Persona;
import modelo.pojo.Sucursal;

/**
 * REST Web Service
 *
 * @author Dell
 */
@Path("sucursales")
public class SucursalWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SucursalWS
     */
    public SucursalWS() {
    }

    @POST
    @Path("registrarSucursal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarSucursal(String json){
        Gson gson = new Gson();
        DatosRegistroSucursal datos = gson.fromJson(json, DatosRegistroSucursal.class);
        Sucursal sucursal = datos.getSucursal();
        Direccion direccion = datos.getDireccion();
        Persona persona = datos.getPersona();
        if (datos == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        /*
        if (sucursal.getNombre() == null || sucursal.getNombre().trim().isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (sucursal.getTelefono() == null || sucursal.getTelefono().trim().isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if(sucursal.getLatitud() == null ){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
        if(sucursal.getLongitud() == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (direccion == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (direccion.getCalle() == null || direccion.getCalle().trim().isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (direccion.getNumero() == null || direccion.getNumero() < 1) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        //Falta validar que lo que manda eson numeros
        if(direccion.getCodigoPostal() == null || direccion.getCodigoPostal().trim().isEmpty() || (direccion.getCodigoPostal().length()<5 || direccion.getCodigoPostal().length()>5)){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if(direccion.getColonia() == null || direccion.getColonia().trim().isEmpty()){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
        if (direccion.getCiudad() == null || direccion.getCiudad()<1) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
        if (direccion.getTipoDireccion() == null || direccion.getTipoDireccion() != 2) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        */
        return SucursalDAO.registrarSucursal(datos);
    }
    
    @PUT
    @Path("editar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarSucursal(String json){
        Gson gson = new Gson();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);
        if(sucursal!=null){
            return SucursalDAO.modificarSucursal(sucursal);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    
    //Servicio de eliminar queda pendiente dado que hay que verificar que no tenga ninguna promocion asociada para la eliminacion
    @DELETE
    @Path("eliminar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje eliminarSucursal(String json){
        Gson gson = new Gson();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);
        if(sucursal != null){
            return SucursalDAO.eliminarSucursal(sucursal);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
}
