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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.EmpresaDAO;
import modelo.pojo.DatosRegistroEmpresa;
import modelo.pojo.Direccion;
import modelo.pojo.Mensaje;
import modelo.pojo.Empresa;
import modelo.pojo.FiltroBuscarEmpresa;
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

        if (!json.isEmpty()) {
            Gson gson = new Gson();
            DatosRegistroEmpresa datos = gson.fromJson(json, DatosRegistroEmpresa.class);
            Empresa empresa = datos.getEmpresa();
            Persona persona = datos.getPersona();
            Direccion direccion = datos.getDireccion();

            if (datos == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (empresa.getNombre().isEmpty() || empresa.getNombre() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (empresa.getNombreComercial().isEmpty() || empresa.getNombreComercial() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (empresa.getLogo() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (empresa.getEmail().isEmpty() || empresa.getEmail() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (empresa.getTelefono().isEmpty() || empresa.getTelefono() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (empresa.getTelefono().isEmpty() || empresa.getTelefono() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (direccion.getCalle().isEmpty() || direccion.getCalle() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (direccion.getNumero() < 0 || direccion.getNumero() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (direccion.getCodigoPostal().isEmpty() || direccion.getCodigoPostal() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (direccion.getCiudad() < 0 || direccion.getCiudad() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (persona.getNombre().isEmpty() || persona.getNombre() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (persona.getApellidoPaterno().isEmpty() || persona.getApellidoPaterno() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (persona.getApellidoMaterno().isEmpty() || persona.getApellidoMaterno() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
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

        if (!json.isEmpty()) {
            Gson gson = new Gson();
            DatosRegistroEmpresa datos = gson.fromJson(json, DatosRegistroEmpresa.class);
            Empresa empresa = datos.getEmpresa();
            Persona persona = datos.getPersona();
            Direccion direccion = datos.getDireccion();

            if (datos == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (datos.getEmpresa().getIdEmpresa() < 0 || datos.getEmpresa().getIdEmpresa() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            if (datos.getDireccion().getIdDireccion() < 0 || datos.getEmpresa().getIdEmpresa() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (datos.getPersona().getIdPersona() < 0 || datos.getPersona().getIdPersona() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if (datos.getDireccion().getCiudad() < 0 || datos.getDireccion().getCiudad() == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            return EmpresaDAO.editarEmpresa(datos);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

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

    @GET
    @Path("buscarPorFiltro")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> buscarUsuarioPorFiltro(
            @QueryParam("cadenaBusqueda") String cadenaBusqueda,
            @QueryParam("porNombre") Boolean porNombre,
            @QueryParam("porRFC") Boolean porRFC,
            @QueryParam("porRepresentante") Boolean porRepresentante) {

        if (cadenaBusqueda == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (porNombre == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (porRFC == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (porRepresentante == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        FiltroBuscarEmpresa filtro = new FiltroBuscarEmpresa();
        
        filtro.setCadena(cadenaBusqueda);
        filtro.setPorNombre(porNombre);
        filtro.setPorRFC(porRFC);
        filtro.setPorRepresentante(porRepresentante);

        return EmpresaDAO.buscarPorFiltro(filtro);
    }
}
