/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;
import modelo.pojo.Cliente;
import modelo.pojo.DatosRegistroCliente;
import modelo.pojo.Mensaje;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Dell
 */
public class ClienteDAO {

    public static Mensaje registrarCliente(DatosRegistroCliente datos) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                //Usa procedimiento almacenado
                conexionBD.insert("cliente.registrarCliente", datos);
                conexionBD.commit();

                if (datos.getFilasAfectadas() > 0 && datos.getError().isEmpty()) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Cliente registrado con exito");
                } else {
                    mensaje.setMensaje(datos.getError());
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Por el momento no hay conexion con la base de datos");
        }

        return mensaje;
    }

    public static Mensaje modificarCliente(DatosRegistroCliente datos) {

        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {

            try {
                conexionBD.update("cliente.editarCliente", datos);
                conexionBD.commit();
                if (datos.getFilasAfectadas() > 0 && datos.getError().isEmpty()) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Cliente modificado con exito");
                } else {
                    mensaje.setMensaje(datos.getError());
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }

        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos");
        }
        return mensaje;
    }

}
