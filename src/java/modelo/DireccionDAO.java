/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.pojo.Direccion;
import modelo.pojo.Mensaje;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Dell
 */
public class DireccionDAO {

    public static Direccion obtenerDireccionPorId(Integer id) {
        Direccion direccion = null;
        System.err.println(id);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                direccion = conexionBD.selectOne("direccion.obtenerDireccionPorId", id);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return direccion;
    }

    public static Mensaje ingresarDireccionCliente(Direccion direccion) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                int numeroFilasAfectadas = conexionBD.insert("direccion.registrarDireccionCliente", direccion);
                conexionBD.commit();
                if (numeroFilasAfectadas > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Dirección registrado con éxito");
                } else {
                    mensaje.setMensaje("No se pudo registrar la dirección, favor de intentarlo mas tarde");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexion con la base de datos");
        }

        return mensaje;
    }

    public static Mensaje modificarDireccionCliente(Direccion direccion) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int numeroFilasAfectadas = conexionBD.update("direccion.modificarDireccionCliente", direccion);
                conexionBD.commit();
                if (numeroFilasAfectadas > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Direccion editada con éxito");
                } else {
                    mensaje.setMensaje("No se pudo editar la dirección, favor de intentarlo mas tarde");
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

    public static Mensaje ingresarDireccionEmpresa() {
        //TODO

        return null;
    }

    public static Mensaje modificarDireccionEmpresa() {
        //TODO

        return null;
    }

    public static Mensaje ingresarDireccionSucursal(Direccion direccion) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int numeroFilasAfectadas = conexionBD.insert("direccion.registrarDireccionSucursal", direccion);
                conexionBD.commit();
                if (numeroFilasAfectadas > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Direccion de sucursal registrada con éxito");
                } else {
                    mensaje.setMensaje("Error no se pudo registrar la direccion de la sucursal");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexion con la base de datos, favor de intentarlo mas tarde");
        }

        return mensaje;
    }

    public static Mensaje modificarDireccionSucursal(Direccion direccion) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!= null){
            try {
                int numeroFilasAfectadas = conexionBD.update("direccion.modificarDireccionSucursal", direccion);
                conexionBD.commit();
                if(numeroFilasAfectadas > 0){
                    mensaje.setError(false);
                    mensaje.setMensaje("Direccion modificada con éxito");
                }else{
                    mensaje.setMensaje("Error al editar la dirección");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: "+ e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, favor de intentarlo mas tarde");
        }

        return mensaje;
    }
}
