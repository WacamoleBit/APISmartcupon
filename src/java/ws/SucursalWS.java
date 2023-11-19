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
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.SucursalDAO;
import modelo.pojo.Mensaje;
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
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarSucursal(String json){
        Gson gson = new Gson();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);
        if(sucursal!=null && !sucursal.getNombre().isEmpty() && sucursal.getDireccion()!=0 && !sucursal.getTelefono().isEmpty() && 
                sucursal.getLatitud()!=0 && sucursal.getLongitud()!=0 && sucursal.getEncargado()!=0){
            return SucursalDAO.registrarSucursal(sucursal);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
