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
    
    
    public static Mensaje actualizarCliente(Integer idCliente,
                                    String nombre,
                                    String apellidoPaterno,
                                    String apellidoMaterno,
                                    String telefono,
                                    String calle,
                                    Integer numero,
                                    String fechaNacimiento,
                                    String password){

        Mensaje respuesta = new Mensaje();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("idCliente", idCliente);
            parametros.put("nombre", nombre);
            parametros.put("apellidoPaterno", apellidoPaterno);
            parametros.put("apellidoMaterno", apellidoMaterno);
            parametros.put("telefono", telefono);
            parametros.put("calle", calle);
            parametros.put("numero", numero);
            parametros.put("fechaNacimiento", fechaNacimiento);
            parametros.put("password", password);

            Cliente cliente = conexionBD.selectOne("cliente.obtenerClientePorId",idCliente);

            if(cliente != null){
                try {
                    int numeroFilasAfectadas = conexionBD.update("cliente.actualizarCliente", parametros);
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
