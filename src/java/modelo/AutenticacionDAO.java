/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import modelo.pojo.MensajeCliente;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Dell
 */
public class AutenticacionDAO {
    
    public static MensajeCliente iniciarSesionCliente(Cliente cliente){
        MensajeCliente respuesta = new MensajeCliente();
        respuesta.setError(true);
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try {
                
                Cliente sesionCliente = conexionBD.selectOne("autenticacion.iniciarSesionCliente", cliente);
                
                if(sesionCliente != null){
                    respuesta.setError(false);
                    respuesta.setMensaje("Bienvenid(@) "+ sesionCliente.getNombre()+" al catálogo de promociones");
                    respuesta.setCliente(sesionCliente);
                }else{
                    respuesta.setMensaje("Correo electrónico y/o contraseña incorrectos, favor de verificarlos");
                }
            } catch (Exception e) {
                respuesta.setMensaje("Error: " + e.getMessage());
            }finally{
                conexionBD.close();
            }
            
        }else{
            respuesta.setMensaje("Error: Por el momento no hay conexión con la base de datos");
        }
        
        return respuesta;
    }
    
    public static Mensaje iniciarSesionUsuario(Usuario usuario){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!=null){
            try {
                
                Usuario sesionUsuario = conexionBD.selectOne("iniciarSesionUsuario", usuario);
                
                if(sesionUsuario!=null){
                    mensaje.setError(false);
                    mensaje.setMensaje("Bienvenid(@) "+ sesionUsuario.getNombre()+" al sistema de promociones");
                }else{
                    mensaje.setMensaje("Usuario/Correo y/o contraseña incorrectos, favor de verificarlos");
                }
                
            } catch (Exception e) {
                mensaje.setMensaje("Error: " + e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos");
        }
        
        return mensaje;
    }
}
