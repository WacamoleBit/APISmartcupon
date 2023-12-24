/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import modelo.RolDAO;
import modelo.pojo.Rol;

/**
 * REST Web Service
 *
 * @author Manuel Hernandez
 */
@Path("roles")
public class RolWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RolWS
     */
    public RolWS() {
    }

    @GET
    @Path("obtenerRoles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rol> obtenerRoles() {
        return RolDAO.obtenerRoles();
    }
}
