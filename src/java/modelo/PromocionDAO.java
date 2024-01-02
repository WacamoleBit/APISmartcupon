/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.Categoria;
import modelo.pojo.DatosRegistroPromocion;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
import modelo.pojo.TipoPromocion;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author jegal
 */
public class PromocionDAO {

    public static Mensaje registrarPromocion(DatosRegistroPromocion datos) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);

        SqlSession conexionDB = MyBatisUtil.getSession();
        if (conexionDB != null) {
            try {
                conexionDB.insert("promociones.registrarPromocion", datos);
                conexionDB.commit();

                if (datos.getFilasAfectadas() > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Promoción registrada con éxito.");
                } else {
                    mensaje.setMensaje("Error: " + datos.getError());
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: La base de datos no pudo guardar la información de la promocion");
            } finally {
                conexionDB.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, intenta mas tarde.");
        }
        return mensaje;
    }

    public static Mensaje editarPromocion(DatosRegistroPromocion datos) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                conexionBD.update("promociones.modificarPromocion", datos);
                conexionBD.commit();

                if (datos.getFilasAfectadas() > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Promoción modificada con éxito.");
                } else {
                    mensaje.setMensaje("Error: " + datos.getError());
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: La base de datos no pudo modificar la información de la promocion");
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexión con la base de datos, intenta mas tarde.");
        }

        return mensaje;
    }

    public static Mensaje eliminarPromocion(DatosRegistroPromocion datos) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                conexionBD.delete("promociones.eliminarPromocion", datos);
                conexionBD.commit();

                if (datos.getFilasAfectadas() > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("La promoción se eliminó correctamente");
                } else {
                    mensaje.setMensaje(datos.getError());
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: La base de datos no pudo eliminar la promoción");
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Error: Por el momento no hay conexion con la base de datos");
        }

        return mensaje;
    }
    
    public static Promocion obtenerPorId(Integer idPromocion) {
        Promocion promocion = null;

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            promocion = new Promocion();

            promocion = conexionBD.selectOne("promociones.obtenerPorId", idPromocion);
        }

        return promocion;
    }

    public static List<Promocion> obtenerPromociones() {
        List<Promocion> promociones = null;

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            promociones = new ArrayList<>();

            promociones = conexionBD.selectList("promociones.obtenerPromociones");
        }

        return promociones;
    }

    public static List<TipoPromocion> obtenerTiposPromocion() {
        List<TipoPromocion> tiposPromocion = null;

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            tiposPromocion = new ArrayList<>();

            tiposPromocion = conexionBD.selectList("promociones.obtenerTiposPromocion");
        }

        return tiposPromocion;
    }

    public static List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = null;

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            categorias = new ArrayList<>();

            categorias = conexionBD.selectList("promociones.obtenerCategorias");
        }

        return categorias;
    }
    
    public static List<Promocion> obtenerPromocionesPorCategoria(int idCategoria){
        List<Promocion> promocionesCategoria = null;

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            promocionesCategoria = new ArrayList<>();

            promocionesCategoria = conexionBD.selectList("promociones.obtenerPromocionesPorCategoria", idCategoria);
        }

        return promocionesCategoria;
    }
    
}
