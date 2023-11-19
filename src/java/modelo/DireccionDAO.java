/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.pojo.Direccion;
import modelo.pojo.Mensaje;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
/**
 *
 * @author Dell
 */
public class DireccionDAO {
      
    public static Direccion obtenerDireccionPorId(Integer id) {
        Direccion direccion = null;
        System.err.println(id);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                
                direccion = conexionBD.selectOne("direccion.obtenerDireccionPorId", id);
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        
        return direccion;
    }

    
    public static Mensaje ingresarDireccionCliente(Direccion direccion) {
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                  
                    int filasAfectas = conexionBD.insert("direccion.registrarDireccionCliente", direccion);
                    conexionBD.commit();
                    if(filasAfectas > 0){
                        msj.setError(false);
                        msj.setMensaje("Dirección registrado con éxito");
                    }else{
                        msj.setMensaje("No se pudo registrar la dirección, favor de intentarlo mas tarde");
                    }
            } catch (Exception e) {
                    msj.setMensaje("Error: " + e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Por el momento no hay conexion con la base de datos");
        }

        return msj;
    }
    
    public static Mensaje modificarDireccionCliente(Direccion direccion){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!=null){
            try {
                int filasAfectadas = conexionBD.update("direccion.editarDireccionCliente", direccion);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    msj.setError(false);
                    msj.setMensaje("Direccion editada con éxito");
                }
                else{
                   msj.setMensaje("No se pudo editar la dirección, favor de intentarlo mas tarde");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: " + e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Por el momento no hay conexión con la base de datos");
        }
        
        return msj;
    }
    
    public static Mensaje ingresarDireccionEmpresa(){
        //TODO
        
        return null;
    }
    
    public static Mensaje modificarDireccionEmpresa(){
        //TODO
        
        return null;
    }
    
    public static Mensaje ingresarDireccionSucursal(){
        //TODO
        
        return null;
    }
    
    public static Mensaje modificarDireccionSucursal(){
        //TODO
        
        return null;
    }
}
