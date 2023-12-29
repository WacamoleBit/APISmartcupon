/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.ClienteDAO;
import modelo.DireccionDAO;
import modelo.pojo.Cliente;
import modelo.pojo.DatosRegistroCliente;
import modelo.pojo.Direccion;
import modelo.pojo.Mensaje;

/**
 * REST Web Service
 *
 * @author Dell
 */
@Path("clientes")
public class ClienteWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ClienteWS
     */
    public ClienteWS() {
    }

    @GET
    @Path("obtenerDireccion/{idDireccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Direccion obtenerDireccionPorId(@PathParam("idDireccion") Integer idDireccion) {

        if (idDireccion != null) {
            return ClienteDAO.obtenerDireccion(idDireccion);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

    }

    @POST
    @Path("registrarCliente")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarCliente(String json) {
        Gson gson = new Gson();
        if (!json.isEmpty()) {

            DatosRegistroCliente datos = gson.fromJson(json, DatosRegistroCliente.class);
            Cliente cliente = datos.getCliente();
            Direccion direccion = datos.getDireccion();
            if (cliente == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (cliente.getApellidoPaterno() == null || cliente.getApellidoPaterno().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (cliente.getApellidoMaterno() == null || cliente.getApellidoMaterno().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (cliente.getFechaNacimiento() == null || cliente.getFechaNacimiento().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (cliente.getPassword() == null || cliente.getPassword().trim().isEmpty()) {
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

            return ClienteDAO.registrarCliente(datos);

        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

    }

    @PUT
    @Path("editarCliente")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarCliente(String json) {

        Gson gson = new Gson();
        if (!json.isEmpty()) {

            DatosRegistroCliente datos = gson.fromJson(json, DatosRegistroCliente.class);
            Cliente cliente = datos.getCliente();
            Direccion direccion = datos.getDireccion();
            if (cliente == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (cliente.getIdCliente() == null && cliente.getIdCliente() < 1) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (direccion == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (direccion.getIdDireccion() == null && direccion.getIdDireccion() < 1) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            return ClienteDAO.modificarCliente(datos);

        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
