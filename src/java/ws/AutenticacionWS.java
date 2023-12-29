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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.AutenticacionDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import modelo.pojo.MensajeUsuario;
import modelo.pojo.MensajeCliente;
import modelo.pojo.Usuario;

/**
 * REST Web Service
 *
 * @author Dell
 */
@Path("autenticacion")
public class AutenticacionWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AutenticacionWS
     */
    public AutenticacionWS() {
    }

    @POST
    @Path("inicioSesionCliente")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public MensajeCliente inicioSesionCliente(String json) {
        Gson gson = new Gson();
        if (json != null) {
            Cliente cliente = gson.fromJson(json, Cliente.class);
            if (cliente == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (cliente.getPassword() == null || cliente.getPassword().trim().isEmpty()) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            return AutenticacionDAO.iniciarSesionCliente(cliente);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

    }

    @POST
    @Path("inicioSesionUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public MensajeUsuario verificarSesionUsuario(String json) {
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(json, Usuario.class);
        if (usuario != null) {
            if(usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if(usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            
            return AutenticacionDAO.iniciarSesionUsuario(usuario);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
