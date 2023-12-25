/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.FiltroBuscarUsuario;
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
                int filasAfectadas = conexionBD.insert("usuario.editararUsuario", usuario);
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
    
    public static Mensaje editarUsuario(Usuario usuario) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("usuario.editarUsuario", usuario);
                conexionBD.commit();
                
                if(filasAfectadas > 0){    
                    mensaje.setError(false);
                    mensaje.setMensaje("La información del usuario se actualizó correctamente");
                }else{
                    mensaje.setMensaje("No se pudo actualizar la información del usuario");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: La base de datos no pudo actualizar la información del usuario");
            }finally{
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexion con la base de datos");
        }
        
        return mensaje;
    }
    
    public static Mensaje eliminarUsuario(int idUsuario) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.delete("usuario.eliminarUsuario", idUsuario);
                conexionBD.commit();
                
                if(filasAfectadas > 0){    
                    mensaje.setError(false);
                    mensaje.setMensaje("El usuario se eliminó correctamente");
                }else{
                    mensaje.setMensaje("No se pudo eliminar el usuario");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: La base de datos no pudo eliminar el usuario");
            }finally{
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexion con la base de datos");
        }
        
        return mensaje;
    }
    
    public static List<Usuario> buscarPorFiltro (FiltroBuscarUsuario cadenaFiltro) {
        List<Usuario> usuarios = null;
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null) {
            usuarios = new ArrayList<>();
            
            usuarios = conexionBD.selectList("usuario.buscarUsuarioPorFiltro", cadenaFiltro);
        }
        
        return usuarios;
    }
    
    public static List<Usuario> obtenerTodos () {
        List<Usuario> usuarios = null;
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null) {
            usuarios = new ArrayList<>();
            
            usuarios = conexionBD.selectList("usuario.obtenerTodos");
        }
        
        return usuarios;
    }
    
    public static Usuario obtenerPorId (int idUsuario) {
        Usuario usuario = null;
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null) {
            usuario = conexionBD.selectOne("usuario.obtenerPorId", idUsuario);
        }
        
        return usuario;
    }
}
