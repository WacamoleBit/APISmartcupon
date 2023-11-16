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
public class AutenticacionDAO {
    
    public static Mensaje verificarSesionMovilCliente(String email, String password){
        Mensaje respuesta = new Mensaje();
        respuesta.setError(true);
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try {
                HashMap<String,String> parametros = new HashMap<>();
                parametros.put("email", email);
                parametros.put("password", password);
                
                Cliente cliente = conexionBD.selectOne("autenticacion.loginMovilCliente",parametros);
                
                if(cliente != null){
                    respuesta.setError(false);
                    respuesta.setMensaje("Bienvenid(@) "+ cliente.getNombre()+" al sistema de promociones");
                }else{
                    respuesta.setMensaje("Número de personal y/o contraseña incorrectos, favor de verificarlos");
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
}
