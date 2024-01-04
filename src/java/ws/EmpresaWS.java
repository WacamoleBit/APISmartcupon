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
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    public EmpresaWS() {
    }

    @GET
    @Path("obtenerEmpresas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> mostrarEmpresas() {

        return EmpresaDAO.obtenerEmpresas();
    }

    @GET
    @Path("obtenerInformacionEmpresa/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public DatosRegistroEmpresa datosEmpresa(@PathParam("idEmpresa") Integer idEmpresa) {

        if (idEmpresa != null && idEmpresa > 0) {
            return EmpresaDAO.obtenerDatosEmpresa(idEmpresa);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @POST
    @Path("registrarEmpresa")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarEmpresa(String json) {

        Gson gson = new Gson();
        DatosRegistroEmpresa datos = gson.fromJson(json, DatosRegistroEmpresa.class);
        Empresa empresa = datos.getEmpresa();
        Persona persona = datos.getPersona();
        Direccion direccion = datos.getDireccion();
        if (datos != null) {
            return EmpresaDAO.registrarEmpresa(datos);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @Path("editarEmpresa")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarEmpresa(String json) {

        Gson gson = new Gson();
        DatosRegistroEmpresa datos = gson.fromJson(json, DatosRegistroEmpresa.class);

        return EmpresaDAO.editarEmpresa(datos);
    }

    @DELETE
    @Path("eliminarEmpresa")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje eliminarEmpresa(String json) {
        if (!json.isEmpty()) {
            Gson gson = new Gson();
            DatosRegistroEmpresa empresa = gson.fromJson(json, DatosRegistroEmpresa.class);

            if (empresa.getEmpresa().getIdEmpresa() == null || empresa.getEmpresa().getIdEmpresa() < 0) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            return EmpresaDAO.eliminarEmpresa(empresa);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

    }
}
