/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.DireccionDAO;
import modelo.pojo.Ciudad;
import modelo.pojo.Cliente;
import modelo.pojo.Direccion;
import modelo.pojo.Estado;
import modelo.pojo.Mensaje;

/**
 * REST Web Service
 *
 * @author Dell
 */
@Path("direcciones")
public class DireccionWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DireccionWS
     */
    public DireccionWS() {
    }

    /**
     * Retrieves representation of an instance of ws.DireccionWS
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("obtenerEstados")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estado> obtenerEstados() {

        return DireccionDAO.obtenerEstados();
    }

    @GET
    @Path("obtenerCiudades/{idEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ciudad> obtenerCiudades(@PathParam("idEstado") Integer idEstado) {
        if (idEstado == null && idEstado < 1) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } 
        
        return DireccionDAO.obtenerCiudades(idEstado);
    }
}
