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
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.AutenticacionDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import modelo.pojo.MensajeUsuario;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

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
   public Mensaje inicioSesionCliente(String json){
       Gson gson = new Gson();
       Cliente cliente = gson.fromJson(json, Cliente.class);
       if(cliente!= null && !cliente.getEmail().isEmpty() && !cliente.getPassword().isEmpty() ){
           return AutenticacionDAO.iniciarSesionCliente(cliente);
       }else{
           throw new WebApplicationException(Response.Status.BAD_REQUEST);
       }
   }
   
   
   @POST
   @Path("inicioSesionUsuario")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public MensajeUsuario verificarSesionUsuario(String json){
       Gson gson= new Gson();
       Usuario usuario = gson.fromJson(json, Usuario.class);
       if(usuario!=null){
           return AutenticacionDAO.iniciarSesionUsuario(usuario);
       }else{
           throw  new WebApplicationException(Response.Status.BAD_REQUEST);
       }
   }
}
