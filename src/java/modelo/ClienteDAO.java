/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Dell
 */
public class ClienteDAO {
    
    public static Mensaje registrarCliente(Cliente cliente){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try {
                int numeroFilasAfectadas = conexionBD.insert("cliente.registrarCliente", cliente);
                conexionBD.commit();
                if(numeroFilasAfectadas > 0){
                    
                    mensaje.setError(false);
                    mensaje.setMensaje("Cliente registrado con exito");
                }else{
                    mensaje.setMensaje("No se pudo registrar la informacion del cliente");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: " + e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Por el momento no hay conexion con la base de datos");
        }
        
        return mensaje;
    }
    
    public static Mensaje modificarCliente(Cliente cliente){

        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            
            Cliente clienteExistente = conexionBD.selectOne("cliente.obtenerClientePorId", cliente);

            if(clienteExistente != null){
                try {
                    int numeroFilasAfectadas = conexionBD.update("cliente.modificarCliente", cliente);
                    conexionBD.commit();
                    if(numeroFilasAfectadas > 0){
                        mensaje.setError(false);
                        mensaje.setMensaje("Cliente modificado con exito");
                    }else{
                        mensaje.setMensaje("Error al actualizar el cliente");
                    }
                } catch (Exception e) {
                    mensaje.setMensaje("Error: " + e.getMessage());
                }finally{
                    conexionBD.close();
                }
            }else{
                mensaje.setMensaje("El cliente no esta registrado");
            }
        }else{
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos");
        }
        return mensaje;
    }
    
}
