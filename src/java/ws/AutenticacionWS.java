/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

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
import modelo.pojo.Mensaje;
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
   @Path("verificarInicioSesionCliente")
   @Produces(MediaType.APPLICATION_JSON)
   public Mensaje verificarSesionCliente(@FormParam("email") String email,
                                 @FormParam("password") String password){
       Mensaje respuesta = null;
       if(!email.isEmpty() && !password.isEmpty()){
           respuesta = AutenticacionDAO.verificarSesionMovilCliente(email, password);
       }else{
           throw new WebApplicationException(Response.Status.BAD_REQUEST);
       }
       
       return respuesta;
   }
   
   
   @GET
   @Path("verificarInicioSesionUsuario")
   @Produces(MediaType.APPLICATION_JSON)
   public Mensaje verificarSesionUsuario(){
       Mensaje msj = new Mensaje();
       
       SqlSession conexionBD = MyBatisUtil.getSession();
       if(conexionBD!=null){
           msj.setMensaje("Si hay conexion");
       }else{
           msj.setMensaje("Estas pal perro");
       }
       
       return msj;
   }
    
}
