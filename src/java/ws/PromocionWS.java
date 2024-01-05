/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.PromocionDAO;
import modelo.PromocionesSucursalesDAO;
import modelo.SucursalDAO;
import modelo.pojo.Categoria;
import modelo.pojo.DatosRegistroPromocion;
import modelo.pojo.FiltroBuscarPromocion;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
import modelo.pojo.PromocionSucursal;
import modelo.pojo.Sucursal;
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
    @Path("editarPromocion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje editarPromocion(String json) {
        Gson gson = new Gson();
        DatosRegistroPromocion datos = gson.fromJson(json, DatosRegistroPromocion.class);

        return PromocionDAO.editarPromocion(datos);
    }

    @DELETE
    @Path("eliminarPromocion/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarPromocion(@PathParam("idPromocion") Integer idPromocion) {
        if (idPromocion == null || idPromocion <= 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        Promocion promocion = new Promocion();
        promocion.setIdPromocion(idPromocion);

        DatosRegistroPromocion datos = new DatosRegistroPromocion();
        datos.setPromocion(promocion);

        return PromocionDAO.eliminarPromocion(datos);
    }

    @GET
    @Path("obtenerPorId/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Promocion obtenerPorId(@PathParam("idPromocion") Integer idPromocion) {
        if (idPromocion == null || idPromocion <= 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return PromocionDAO.obtenerPorId(idPromocion);
    }

    @GET
    @Path("obtenerPromociones/id{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> obtenerPromociones(@PathParam("idEmpresa") Integer idEmpresa) {
        
        return PromocionDAO.obtenerPromociones(idEmpresa);
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

    @GET
    @Path("obtenerPromocionesDisponibles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> obtenerPromocionesDisponibles() {
        return PromocionDAO.obtenerPromocionesDisponibles();
    }

    @GET
    @Path("obtenerPromocionesDisponiblesPorEmpresa/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> obtenerPromocionesDisponiblesPorEmpresa(@PathParam("idEmpresa") Integer idEmpresa) {

        if (idEmpresa == null || idEmpresa < 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return PromocionDAO.obtenerPromocionesDisponiblesPorEmpresa(idEmpresa);
    }

    @GET
    @Path("obtenerSucursalesPorPromocion/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> obtenerSucursalesPorPromocion(@PathParam("idPromocion") Integer idPromocion) {

        if (idPromocion < 0 || idPromocion == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return SucursalDAO.obtenerSucursalesPorPromocion(idPromocion);
    }

    @GET
    @Path("obtenerSucursalesSinPromocion/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> obtenerSucursalesSinPromocion(@PathParam("idPromocion") Integer idPromocion) {

        if (idPromocion < 0 || idPromocion == null) {

            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return SucursalDAO.obtenerSucursalesSinPromocion(idPromocion);
    }

    @GET
    @Path("obtenerPromocionesPorCategoria/{idCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> obtenerPromocionesCategoria(@PathParam("idCategoria") Integer idCategoria) {
        if (idCategoria == null || idCategoria < 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return PromocionDAO.obtenerPromocionesPorCategoria(idCategoria);
    }

    @GET
    @Path("obtenerLogoPorId/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Promocion obtenerLogoPromocion(@PathParam("idPromocion") Integer idPromocion) {
        if (idPromocion == null || idPromocion <= 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return PromocionDAO.obtenerLogoPorId(idPromocion);
    }

    @GET
    @Path("buscarPorFiltro")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> buscarUsuarioPorFiltro(
            @QueryParam("fecha") String fecha,
            @QueryParam("porFechaInicio") Boolean porFechaInicio,
            @QueryParam("nombre") String nombre) {

        if (fecha != null && fecha.matches("^(?:19|20)\\d\\d-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12][0-9]|3[01])$")) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        FiltroBuscarPromocion filtro = new FiltroBuscarPromocion();

        filtro.setFecha(fecha);
        filtro.setPorFechaInicio(porFechaInicio);
        filtro.setNombre(nombre);

        return PromocionDAO.buscarPorFiltro(filtro);
    }

    @POST
    @Path("registroPromocionSucursal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarPromocionSucursales(String json) {
        if (!json.isEmpty()) {
            Gson gson = new Gson();

            PromocionSucursal promocionSucursal = gson.fromJson(json, PromocionSucursal.class);
            if (promocionSucursal == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            return PromocionesSucursalesDAO.registrarPromocionesSucursales(promocionSucursal);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("eliminarPromocionSucursal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje eliminarPromocionSucursal(String json) {
        if (!json.isEmpty()) {
            Gson gson = new Gson();
            PromocionSucursal promocionesSucursales = gson.fromJson(json, PromocionSucursal.class);
            if (promocionesSucursales.getIdPromocion() < 0 || promocionesSucursales.getIdSucursal() < 0 || promocionesSucursales.getIdPromocion() == null || promocionesSucursales.getIdSucursal() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            return PromocionesSucursalesDAO.eliminarPromocionSucursal(promocionesSucursales);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
