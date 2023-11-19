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
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try {
                int filasAfectadas = conexionBD.insert("cliente.registrarCliente", cliente);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    
                    msj.setError(false);
                    msj.setMensaje("Cliente registrado con exito");
                }else{
                    msj.setMensaje("No se pudo registrar la informacion del cliente");
                }
            } catch (Exception e) {
                e.printStackTrace();
                msj.setMensaje("Error: " + e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Por el momento no hay conexion con la base de datos");
        }
        
        return msj;
    }
    
    public static Mensaje actualizarCliente(Cliente cliente){

        Mensaje respuesta = new Mensaje();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            
            Cliente clienteExistente = conexionBD.selectOne("cliente.obtenerClientePorId", cliente.getId());

            if(cliente != null){
                try {
                    int numeroFilasAfectadas = conexionBD.update("cliente.actualizarInformacionCliente", cliente);
                    conexionBD.commit();
                    if(numeroFilasAfectadas > 0){
                        respuesta.setError(false);
                        respuesta.setMensaje("Cliente actualizado con exito");
                    }else{
                        respuesta.setMensaje("Error al actualizar el cliente");
                    }
                } catch (Exception e) {
                    respuesta.setMensaje("Error: " + e.getMessage());
                }finally{
                    conexionBD.close();
                }
            }else{
                respuesta.setMensaje("El cliente no esta registrado");
            }
        }
        return respuesta;
    }
    
}
