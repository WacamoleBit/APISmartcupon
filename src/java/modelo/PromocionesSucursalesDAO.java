/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import modelo.pojo.Mensaje;
import modelo.pojo.PromocionSucursal;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Dell
 */
public class PromocionesSucursalesDAO {

    public static Mensaje registrarPromocionesSucursales(PromocionSucursal promocionSucursal) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
               
                int filasAfectadas = conexionBD.insert("promociones.insertarPromocionSucursal", promocionSucursal);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Sucursal registrada con éxito");
                } else {
                    mensaje.setMensaje("Error al registrar la sucursal");
                }

            } catch (Exception e) {
                mensaje.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, favor de intentarlo mas tarde");
        }

        return mensaje;
    }

    public static Mensaje eliminarPromocionSucursal(PromocionSucursal promocionesSucursales) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                int filasAfectadas = conexionBD.delete("promociones.eliminarPromocionSucursal", promocionesSucursales);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Sucursal eliminada de la promoción");
                } else {
                    mensaje.setMensaje("Error al eliminar la sucursal de la promocion");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, favor e intentarlo mas tarde");
        }

        return mensaje;
    }

}
