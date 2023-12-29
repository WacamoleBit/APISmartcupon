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
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.PromocionDAO;
import modelo.pojo.Categoria;
import modelo.pojo.DatosRegistroPromocion;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
import modelo.pojo.TipoPromocion;

/**
 * REST Web Service
 *
 * @author Manuel Hernandez
 */
@Path("promociones")
public class PromocionWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PromocionWS
     */
    public PromocionWS() {
    }

    @POST
    @Path("registrarPromocion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarPromocion(String json) {
        Gson gson = new Gson();
        DatosRegistroPromocion datos = gson.fromJson(json, DatosRegistroPromocion.class);
        Promocion promocion = datos.getPromocion();

        if (promocion == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (promocion.getNombre() == null || promocion.getNombre().trim().isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (promocion.getDescripcion() == null || promocion.getDescripcion().trim().isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (promocion.getFechaInicio() == null || promocion.getFechaInicio().trim().isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (promocion.getFechaTermino() == null || promocion.getFechaTermino().trim().isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (promocion.getTipoPromocion() == null || promocion.getTipoPromocion() < 1) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (promocion.getPorcentajeDescuento() == null || promocion.getPorcentajeDescuento() < 1) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (promocion.getCategoria() == null || promocion.getCategoria() < 1) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (promocion.getMaximoCupones() == null || promocion.getMaximoCupones() < 1) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (promocion.getCodigoPromocion() == null || promocion.getCodigoPromocion().trim().isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (promocion.getEmpresa() == null || promocion.getEmpresa() < 1) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return PromocionDAO.registrarPromocion(datos);
    }

    @PUT
    @Path("modificarPromocion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarPromocion(String json) {
        Gson gson = new Gson();
        DatosRegistroPromocion datos = gson.fromJson(json, DatosRegistroPromocion.class);

        return PromocionDAO.modificarPromocion(datos);
    }

    @GET
    @Path("obtenerPromociones")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> obtenerPromociones() {
        return PromocionDAO.obtenerPromociones();
    }
    
    @GET
    @Path("obtenerTiposPromocion")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoPromocion> obtenerTiposPromocion() {
        return PromocionDAO.obtenerTiposPromocion();
    }
    
    @GET
    @Path("obtenerCategorias")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Categoria> obtenerCategirias() {
        return PromocionDAO.obtenerCategorias();
    }
}
