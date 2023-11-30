/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author jegal
 */
public class PromocionDAO {
    
    public static Mensaje registrarPromocion(Promocion promocion){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null){
            try{
                int numeroFilasAfectadas  = conexionDB.insert("promocion.registrarPomocion", promocion);
                conexionDB.commit();
                if(numeroFilasAfectadas > 0){
                    mensaje.setError(false);
                    mensaje.setMensaje("Promoción registrada con éxito.");
                }else{
                    mensaje.setMensaje("Error: No se pudo registrar la empresa, por favor intenta de nuevo.");
                }
            }catch(Exception e){
                mensaje.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionDB.close();
            }
        }else{
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, intenta mas tarde.");
        }
        return mensaje;
    }
    
}
