/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.pojo.Cliente;
import modelo.pojo.DatosRegistroCliente;
import modelo.pojo.Direccion;
import modelo.pojo.MensajeUsuario;
import modelo.pojo.MensajeCliente;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Dell
 */
public class AutenticacionDAO {

    public static MensajeCliente iniciarSesionCliente(Cliente cliente) {
        MensajeCliente respuesta = new MensajeCliente();
        respuesta.setError(true);

        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                Cliente clienteSesion = conexionBD.selectOne("autenticacion.iniciarSesionCliente", cliente);
                 
                
                if (clienteSesion != null) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Bienvenid(@) " + clienteSesion.getNombre() + " al catálogo de promociones");
                    respuesta.setCliente(clienteSesion);
                    
                } else {
                    respuesta.setMensaje("Correo electrónico y/o contraseña incorrectos, favor de verificarlos");
                }
            } catch (Exception e) {
                respuesta.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }

        } else {
            respuesta.setMensaje("Error: Por el momento no hay conexión con la base de datos");
        }

        return respuesta;
    }

    public static MensajeUsuario iniciarSesionUsuario(Usuario usuario) {
        MensajeUsuario mensaje = new MensajeUsuario();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                Usuario sesionUsuario = conexionBD.selectOne("autenticacion.iniciarSesionUsuario", usuario);

                if (sesionUsuario != null) {
                    mensaje.setError(false);
                    mensaje.setUsuario(sesionUsuario);
                    mensaje.setContenido("Bienvenid(@) " + sesionUsuario.getNombre() + " al sistema de promociones");
                } else {
                    mensaje.setContenido("Usuario/Correo y/o contraseña incorrectos, favor de verificarlos");
                }

            } catch (Exception e) {
                mensaje.setContenido("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setContenido("Error: Por el momento no hay conexión con la base de datos");
        }

        return mensaje;
    }
}
