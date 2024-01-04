/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.DatosRegistroSucursal;
import modelo.pojo.Direccion;
import modelo.pojo.FiltroBuscarSucursal;
import modelo.pojo.Mensaje;
import modelo.pojo.Persona;
import modelo.pojo.PromocionSucursal;
import modelo.pojo.Sucursal;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * ha
 *
 * @author Dell
 */
public class SucursalDAO {

    public static List<Sucursal> obtenerSucursales() {
        List<Sucursal> sucursales = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                sucursales = conexionBD.selectList("sucursal.obtenerSucursales");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return sucursales;
    }

    public static List<Sucursal> obtenerSucursalesPorPromocion(Integer idPromocion) {
        List<Sucursal> sucursales = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                sucursales = conexionBD.selectList("sucursal.obtenerSucursalesPorIdPromocion", idPromocion);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return sucursales;
    }

    public static List<Sucursal> obtenerSucursalesSinPromocion(Integer idPromocion) {
        List<Sucursal> sucursales = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                sucursales = conexionBD.selectList("sucursal.obtenerSucursalesSinPromocion", idPromocion);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return sucursales;
    }

    public static List<Sucursal> obtenerSucursalesPorIdEmpresa(Integer idEmpresa) {
        List<Sucursal> sucursales = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {

                sucursales = conexionBD.selectList("sucursal.obtenerSucursalesPorIdEmpresa", idEmpresa);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return sucursales;
    }

    public static DatosRegistroSucursal obtenerSucursalPorId(Integer idSucursal) {
        DatosRegistroSucursal datosSucursal = new DatosRegistroSucursal();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                Sucursal sucursal = conexionBD.selectOne("sucursal.obtenerSucursalPorId", idSucursal);
                System.out.println(sucursal.getEncargado());
                Persona encargado = conexionBD.selectOne("sucursal.obtenerEncargadoPorId", sucursal.getEncargado());
                Direccion direccion = conexionBD.selectOne("sucursal.obtenerDireccionPorId", sucursal.getDireccion());

                datosSucursal.setSucursal(sucursal);
                datosSucursal.setPersona(encargado);
                datosSucursal.setDireccion(direccion);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return datosSucursal;
    }

    public static Mensaje registrarSucursal(DatosRegistroSucursal datos) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.insert("sucursal.registrarSucursal", datos);
                conexionBD.commit();

                if (datos.getFilasAfectadas() > 0 && datos.getError().isEmpty()) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Sucursal registrada con éxito.");
                } else {
                    mensaje.setMensaje("Error: " + datos.getError());
                }

            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos");
        }

        return mensaje;
    }

    public static Mensaje modificarSucursal(DatosRegistroSucursal datos) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.update("sucursal.editarSucursal", datos);
                conexionBD.commit();

                if (datos.getFilasAfectadas() > 0 && datos.getError().isEmpty()) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Información actualizada con éxito");
                } else {
                    mensaje.setMensaje("Error al actualizar la información");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, favor de intentarlo mas tarde");
        }

        return mensaje;
    }

    public static Mensaje eliminarSucursal(DatosRegistroSucursal datos) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                conexionBD.delete("sucursal.eliminarSucursal", datos);
                conexionBD.commit();
                if (datos.getFilasAfectadas() > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Sucursal eliminada con éxito");
                } else {
                    mensaje.setMensaje("Error al eliminar la sucursal");
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

    public static List<Sucursal> buscarPorFiltro(FiltroBuscarSucursal filtro) {
        List<Sucursal> sucursales = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                sucursales = conexionBD.selectList("sucursal.buscarPorFiltro", filtro);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }

        return sucursales;
    }
}
