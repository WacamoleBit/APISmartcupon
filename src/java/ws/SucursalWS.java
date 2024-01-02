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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.SucursalDAO;
import modelo.pojo.DatosRegistroSucursal;
import modelo.pojo.Direccion;
import modelo.pojo.FiltroBuscarSucursal;
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

    @GET
    @Path("obtenerSucursales")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> mostrarSucursales() {

        return SucursalDAO.obtenerSucursales();
    }

    @GET
    @Path("obtenerPorId/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public DatosRegistroSucursal obtenerPorId(@PathParam("idSucursal") Integer idSucursal) {
        return SucursalDAO.obtenerSucursalPorId(idSucursal);
    }

    @POST
    @Path("registrarSucursal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarSucursal(String json) {
        Gson gson = new Gson();
        if (!json.isEmpty()) {
            DatosRegistroSucursal datos = gson.fromJson(json, DatosRegistroSucursal.class);
            Sucursal sucursal = datos.getSucursal();
            Direccion direccion = datos.getDireccion();
            Persona persona = datos.getPersona();
            if (datos == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (sucursal.getNombre() == null || sucursal.getNombre().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (sucursal.getTelefono() == null || sucursal.getTelefono().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (sucursal.getLatitud() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (sucursal.getLongitud() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (sucursal.getEmpresa() == null || sucursal.getEmpresa() < 1) {
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

            if (direccion.getCodigoPostal() == null || direccion.getCodigoPostal().trim().isEmpty()
                    || direccion.getCodigoPostal().length() != 5) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (direccion.getColonia() == null || direccion.getColonia().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (direccion.getCiudad() == null || direccion.getCiudad() < 1) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            return SucursalDAO.registrarSucursal(datos);

        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @Path("editarSucursal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarSucursal(String json) {
        Gson gson = new Gson();
        if (json != null) {
            DatosRegistroSucursal datos = gson.fromJson(json, DatosRegistroSucursal.class);
            Sucursal sucursal = datos.getSucursal();
            Direccion direccion = datos.getDireccion();
            Persona persona = datos.getPersona();

            if (sucursal == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (sucursal.getIdSucursal() == null || sucursal.getIdSucursal() < 1) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (sucursal.getEmpresa() == null || sucursal.getEmpresa() < 1) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (direccion == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (direccion.getIdDireccion() == null || direccion.getIdDireccion() < 1) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (direccion.getCiudad() == null || direccion.getCiudad() < 1) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (persona == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (persona.getIdPersona() == null || persona.getIdPersona() < 1) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            return SucursalDAO.modificarSucursal(datos);

        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("eliminarSucursal/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje eliminarSucursal(@PathParam("idSucursal") Integer idSucursal) {
        if (idSucursal == null || idSucursal <= 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(idSucursal);

        DatosRegistroSucursal datos = new DatosRegistroSucursal();
        datos.setSucursal(sucursal);

        return SucursalDAO.eliminarSucursal(datos);
    }

    @GET
    @Path("buscarPorFiltro")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> buscarPorFiltro(
            @QueryParam("nombre") String nombre,
            @QueryParam("direccion") String direccion) {
        FiltroBuscarSucursal filtro = new FiltroBuscarSucursal();
        
        filtro.setNombre(nombre);
        filtro.setDireccion(direccion);

        return SucursalDAO.buscarPorFiltro(filtro);
    }

}
