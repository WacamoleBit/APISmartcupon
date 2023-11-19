/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.pojo.Direccion;
import modelo.pojo.Mensaje;
import modelo.pojo.Sucursal;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Dell
 */
public class SucursalDAO {
    
    
    public static Sucursal obtenerSucursalPorDireccion(Direccion direccion){
        //TODO
        
        
        return null;
    }
    
    public static Mensaje registrarSucursal(Sucursal sucursal){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            
            int numeroFilasAfectadas = conexionBD.insert("sucursal.registrarSucursal", sucursal);
            conexionBD.commit();
            
            if(numeroFilasAfectadas > 0){
                mensaje.setError(false);
                mensaje.setMensaje("Sucursal registrada con éxito");
            }else{
                mensaje.setMensaje("No se pudo registrar la sucursal");
            }
            
        }else{
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos");
        }
        
        return null;
    }
    
    public static Mensaje modificarSucursal(){
        //TODO
        
        return null;
    }
}
