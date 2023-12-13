/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.pojo.DatosRegistroEmpresa;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author jegal
 */
public class EmpresaDAO {
    
    //BUSCAR EMPRESA POR NOMBRE, RFC O NOMBRE DEL REPRESENTANTE
    
    public static Mensaje registrarEmpresa(DatosRegistroEmpresa datos){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession();
        if (conexionDB != null){
            try{
                conexionDB.insert("empresa.registrarEmpresa", datos);
                conexionDB.commit();
                
                if(datos.getFilasAfectadas() > 0){
                    mensaje.setError(false);
                    mensaje.setMensaje("Empresa registrada con éxito.");
                }else{
                    mensaje.setMensaje("Error: No se pudo registrar la empresa, por favor intenta de nuevo.");
                }
            }catch(Exception e){
                mensaje.setMensaje("Error: " + e.getMessage());
            }finally{
                conexionDB.close();
            }
        }else{
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, intenta mas tarde.");
        }
        return  mensaje;
    }

    public static Mensaje editarEmpresa(Empresa empresa){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int numeroFilasAfectadas = conexionBD.update("empresa.editarEmpresa", empresa);
                conexionBD.commit();
                if(numeroFilasAfectadas > 0){
                mensaje.setError(false);
                mensaje.setMensaje("Empresa modificada con éxito.");
                }else{
                    mensaje.setMensaje("Error: No se pudo modificar la empresa, por favor intenta de nuevo.");
                }
            }catch(Exception e){
                mensaje.setMensaje("Error" + e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, intenta mas tarde.");
        }
        return mensaje;
    }
    
    public static Mensaje eliminarEmpresa(Empresa empresa){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int numeroFilasAfectadas = conexionBD.delete("empresa.emilinarEmpresa", empresa);
                if(numeroFilasAfectadas > 0){
                    mensaje.setError(false);
                    mensaje.setMensaje("Empresa eliminada con éxito.");
                }else{
                    mensaje.setMensaje("Error: No se pudo eliminar la empresa, por favor intenta de nuevo.");
                }
            }catch(Exception e){
                mensaje.setMensaje("Error" + e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, intenta mas tarde.");
        }
        return mensaje;
    }
}
