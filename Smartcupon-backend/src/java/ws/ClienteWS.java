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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.ClienteDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;

/**
 * REST Web Service
 *
 * @author Dell
 */
@Path("cliente")
public class ClienteWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ClienteWS
     */
    public ClienteWS() {
    }

    @POST
    @Path("registrarCliente")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarClienteMovil(String json){
        Mensaje respuesta = null;
        
        Gson gson = new Gson();
        Cliente cliente = gson.fromJson(json, Cliente.class);
        if(cliente != null){
            return ClienteDAO.registrarCliente(cliente);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
    }
    
    @PUT
    @Path("actualizarCliente")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje actualizarClienteMovil(@FormParam("idCliente") Integer idCliente,
                                          @FormParam("nombre") String nombre,
                                          @FormParam("apellidoPaterno") String apellidoPaterno,
                                          @FormParam("apellidoMaterno") String apellidoMaterno,
                                          @FormParam("telefono") String telefono,
                                          @FormParam("calle") String calle,
                                          @FormParam("numero") Integer numero,
                                          @FormParam("fechaNacimiento") String fechaNacimiento,
                                          @FormParam("password") String password){
        Mensaje respuesta = null;
        
        if(idCliente != 0 && !nombre.isEmpty() && !apellidoPaterno.isEmpty() && !apellidoMaterno.isEmpty() && !telefono.isEmpty() &&
                !calle.isEmpty() && numero!=0 && !fechaNacimiento.isEmpty() && !password.isEmpty()){
            
            respuesta = ClienteDAO.actualizarCliente(idCliente, nombre, apellidoPaterno, apellidoMaterno, telefono, calle, numero, fechaNacimiento, password);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
           
        return respuesta;
    }
    
}
