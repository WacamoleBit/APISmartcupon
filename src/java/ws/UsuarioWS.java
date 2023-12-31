/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.UsuarioDAO;
import modelo.pojo.DatosRegistroUsuario;
import modelo.pojo.FiltroBuscarUsuario;
import modelo.pojo.Mensaje;
import modelo.pojo.Usuario;

/**
 *
 * @author Manuel Hernandez
 */
@Path("usuarios")
public class UsuarioWS {

    @POST
    @Path("registrarUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarUsuario(String json) {
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(json, Usuario.class);

        if (usuario == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getNombre() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getApellidoMaterno() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getCurp() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getEmail() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getUsername() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getPassword() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getRol() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getRol() == 1 && usuario.getEmpresa() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
        DatosRegistroUsuario datos = new DatosRegistroUsuario();
        datos.setUsuario(usuario);
        

        return UsuarioDAO.registrarUsuario(datos);
    }

    @PUT
    @Path("editarUsuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje editarUsuario(@PathParam("idUsuario") Integer idUsuario,
            String json) {
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(json, Usuario.class);

        if (usuario == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (idUsuario == null || idUsuario <= 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getNombre() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getApellidoMaterno() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getCurp() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getEmail() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getUsername() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getPassword() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getRol() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (usuario.getRol() == 1 && usuario.getEmpresa() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        usuario.setIdUsuario(idUsuario);
        
        DatosRegistroUsuario datos = new DatosRegistroUsuario();
        datos.setUsuario(usuario);

        return UsuarioDAO.editarUsuario(datos);
    }

    @DELETE
    @Path("eliminarUsuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarUsuario(@PathParam("idUsuario") Integer idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return UsuarioDAO.eliminarUsuario(idUsuario);
    }

    @GET
    @Path("buscarPorFiltro")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> buscarUsuarioPorFiltro(
            @QueryParam("cadenaBusqueda") String cadenaBusqueda,
            @QueryParam("porNombre") Boolean porNombre,
            @QueryParam("porUsername") Boolean porUsername,
            @QueryParam("porRol") Boolean porRol) {

        if (cadenaBusqueda == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (porNombre == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (porUsername == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (porRol == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        FiltroBuscarUsuario cadenaFiltro = new FiltroBuscarUsuario();

        cadenaFiltro.setCadenaBusqueda(cadenaBusqueda);
        cadenaFiltro.setPorNombre(porNombre);
        cadenaFiltro.setPorUsername(porUsername);
        cadenaFiltro.setPorRol(porRol);

        return UsuarioDAO.buscarPorFiltro(cadenaFiltro);
    }

    @GET
    @Path("obtenerTodos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> obtenerTodos() {
        return UsuarioDAO.obtenerTodos();
    }

    @GET
    @Path("obtenerPorId/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario obtenerPorId(@PathParam("idUsuario") Integer idUsuario) {
        if (idUsuario == null || idUsuario < 1) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
        return UsuarioDAO.obtenerPorId(idUsuario);
    }
}