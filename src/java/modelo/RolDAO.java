/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.Rol;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Manuel Hernandez
 */
public class RolDAO {

    public static List<Rol> obtenerRoles() {
        List<Rol> roles = null;

        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            roles = new ArrayList<>();
            
            roles = conexionBD.selectList("rol.obtenerRoles");
        }

        return roles;
    }
}
