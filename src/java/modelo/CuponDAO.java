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

public class CuponDAO {


    public static Mensaje canjearCupon(Promocion promocion) {
        Mensaje respuesta = new Mensaje();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                
                if (promocion.getEmpresa() == null || promocion.getEmpresa() < 0) {
                    int filasAfectadas = conexionBD.update("promociones.canjearCupon", promocion);
                    conexionBD.commit();
                    if (filasAfectadas > 0) {
                        respuesta.setError(false);
                        respuesta.setMensaje("Cupon canjeado con éxito");
                    } else {
                        respuesta.setMensaje("Favor de ingresar un cupon valido");
                    }
                } else {
                    int filasAfectadas = conexionBD.update("promociones.canjearCuponComercial", promocion);
                    conexionBD.commit();
                    if (filasAfectadas > 0) {
                        respuesta.setError(false);
                        respuesta.setMensaje("Cupon canjeado con éxito");
                    } else {
                        respuesta.setMensaje("Favor de ingresar un cupon válido");
                    }

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
}
