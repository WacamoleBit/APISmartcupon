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
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.EmpresaDAO;
import modelo.pojo.DatosRegistroEmpresa;
import modelo.pojo.Direccion;
import modelo.pojo.Mensaje;
import modelo.pojo.Empresa;
import modelo.pojo.Persona;

/**
 *
 * @author jegal
 */

@Path("empresas")
public class EmpresaWS {
    
    @Context
    private UriInfo context;
    
    public EmpresaWS(){
    }
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarEmpresa(String json){
        
        Gson gson = new Gson();
        DatosRegistroEmpresa datos = gson.fromJson(json, DatosRegistroEmpresa.class);
        Empresa empresa = datos.getEmpresa();
        Persona persona = datos.getPersona();
        Direccion direccion = datos.getDireccion();
        if(datos!=null ){
            return EmpresaDAO.registrarEmpresa(datos);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @PUT
    @Path("editar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarEmpresa(String json){
    
        Gson gson = new Gson();
        DatosRegistroEmpresa datos = gson.fromJson(json, DatosRegistroEmpresa.class);
        
        return EmpresaDAO.editarEmpresa(datos);
    }

}
