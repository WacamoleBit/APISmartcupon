/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import modelo.pojo.DatosRegistroEmpresa;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.List;
import modelo.pojo.Direccion;
import modelo.pojo.FiltroBuscarEmpresa;
import modelo.pojo.Persona;

/**
 *
 * @author jegal
 */
public class EmpresaDAO {

    //BUSCAR EMPRESA POR NOMBRE, RFC O NOMBRE DEL REPRESENTANTE
    public static List<Empresa> obtenerEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                empresas = conexionBD.selectList("empresa.obtenerListaEmpresas");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return empresas;
    }

    public static DatosRegistroEmpresa obtenerDatosEmpresa(Integer idEmpresa) {
        DatosRegistroEmpresa datosEmpresa = new DatosRegistroEmpresa();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                Empresa empresa = conexionBD.selectOne("empresa.obtenerEmpresaPorId", idEmpresa);
                Persona persona = conexionBD.selectOne("empresa.obtenerRepresentantePorId", idEmpresa);
                Direccion direccion = conexionBD.selectOne("empresa.obtenerDireccionPorId", idEmpresa);

                datosEmpresa.setEmpresa(empresa);
                datosEmpresa.setPersona(persona);
                datosEmpresa.setDireccion(direccion);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return datosEmpresa;
    }

    public static Mensaje registrarEmpresa(DatosRegistroEmpresa datos) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession();
        if (conexionDB != null) {
            try {
                conexionDB.insert("empresa.registrarEmpresa", datos);
                conexionDB.commit();

                if (datos.getFilasAfectadas() > 0 && datos.getError().isEmpty()) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Empresa registrada con éxito.");
                } else {
                    mensaje.setMensaje("Error: No se pudo registrar la empresa, por favor intenta de nuevo.");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: " + e.getMessage());
                System.out.println(e.getMessage());
            } finally {
                conexionDB.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, intenta mas tarde.");
        }
        return mensaje;
    }

    public static Mensaje editarEmpresa(DatosRegistroEmpresa datos) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.update("empresa.editarEmpresa", datos);
                conexionBD.commit();
                if (datos.getFilasAfectadas() > 0 && datos.getError().isEmpty()) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Empresa modificada con éxito.");
                } else {
                    mensaje.setMensaje(datos.getError());
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error" + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, intenta mas tarde.");
        }
        return mensaje;
    }

    public static Mensaje eliminarEmpresa(DatosRegistroEmpresa empresa) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                    conexionBD.delete("empresa.eliminarEmpresa", empresa);
                    conexionBD.commit();
                    
                if (empresa.getFilasAfectadas()> 0 && empresa.getError().isEmpty()) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Empresa eliminada con éxito.");
                } else {
                    mensaje.setMensaje("Error: No se pudo eliminar la empresa " + empresa.getError());
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error" + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, intenta mas tarde.");
        }
        return mensaje;
    }
    
    public static List<Empresa> buscarPorFiltro (FiltroBuscarEmpresa filtro) {
        List<Empresa> empresas = null;
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null) {
            empresas = new ArrayList<>();
            
            empresas = conexionBD.selectList("empresa.buscarEmpresaPorFiltro", filtro);
        }
        
        return empresas;
    }
}
