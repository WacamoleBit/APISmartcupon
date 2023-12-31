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
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.CuponDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;

/**
 * REST Web Service
 *
 * @author Dell
 */
@Path("cupones")
public class CuponWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CuponWS
     */
    public CuponWS() {
    }

    /**
     * Retrieves representation of an instance of ws.CuponWS
     *
     * @return an instance of java.lang.String
     */
    @PUT
    @Path("canjearCupon")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje canjearCupon(String json) {

        if (!json.isEmpty()) {
            Gson gson = new Gson();
            Promocion promocion = gson.fromJson(json, Promocion.class);

            if (promocion == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (promocion.getCodigoPromocion() == null || promocion.getCodigoPromocion().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            return CuponDAO.canjearCupon(promocion);
        } else {

            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

}
