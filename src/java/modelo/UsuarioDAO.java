/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import modelo.pojo.Mensaje;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Manuel Hernandez
 */
public class UsuarioDAO {
    public static Mensaje registrarUsuario(Usuario usuario) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.insert("usuario.registrarUsuario", usuario);
                conexionBD.commit();
                
                if(filasAfectadas > 0){    
                    mensaje.setError(false);
                    mensaje.setMensaje("El usuario se registró correctamente");
                }else{
                    mensaje.setMensaje("No se pudo registrar la información del usuario");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: La base de datos no pudo guardar la información del usuario");
            }finally{
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexion con la base de datos");
        }
        
        return mensaje;
    }
}
